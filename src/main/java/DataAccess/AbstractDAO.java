package DataAccess;

import Connection.ConnectionFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {
    private final Class<T> type;
    private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());


    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String getTableName() {
        if (type.getSimpleName().equals("Order_Table"))
            return "order_table";
        return type.getSimpleName().toLowerCase();
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(getTableName());
        sb.append(" WHERE " + field + " = ?");
        return sb.toString();
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(type.getDeclaredFields()[0].getName());
        List<T> results = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            results = createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        if (!results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + getTableName();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            list = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return list;
    }

    private List<T> createObjects(ResultSet resultSet){
        List<T> list = new ArrayList<T>();

        try{
            while(resultSet.next()){
                T instance = type.getDeclaredConstructor().newInstance();
                for(Field field : type.getDeclaredFields()){
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | IntrospectionException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(getTableName());
        sb.append(" (");
        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            sb.append(fields[i].getName());
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(") VALUES (");
        for (int i = 1; i < fields.length; i++) {
            sb.append("?");
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public int insert(T t){
        Connection connection = null;
        PreparedStatement statement = null;

        Field[] fields = type.getDeclaredFields();
        String query = createInsertQuery();

        ResultSet resultSet;
        int generatedId = -1;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(t);
                statement.setObject(i, value);
            }
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);

                fields[0].setAccessible(true);
                fields[0].set(t, generatedId);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return generatedId;
    }

    private String createUpdateQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(getTableName());
        sb.append(" SET ");
        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            sb.append(fields[i].getName()).append(" = ?");
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(" WHERE " + field + " = ?");
        return sb.toString();
    }

    public void update(T t){
        Connection connection = null;
        PreparedStatement statement = null;

        Field[] fields = type.getDeclaredFields();
        String query = createUpdateQuery(fields[0].getName());

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(t);
                statement.setObject(i, value);
            }
            fields[0].setAccessible(true);
            Object idValue = fields[0].get(t);
            statement.setObject(fields.length, idValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(getTableName());
        sb.append(" WHERE " + field + " = ?");
        return sb.toString();
    }

    public void delete(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        Field[] fields = type.getDeclaredFields();
        String idFieldName = fields[0].getName();
        String query = createDeleteQuery(fields[0].getName());

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete" + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void deleteByOrderId(int idOrder) {
        String query = "DELETE FROM log WHERE orderID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idOrder);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
