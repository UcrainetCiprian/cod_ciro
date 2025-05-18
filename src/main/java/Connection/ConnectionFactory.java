package Connection;

import java.sql.*;
import java.util.logging.Logger;

/**
 * ConnectionFactory se ocupa cu gestionarea conexiunii la baza de date MySQL.
 * Creeaza, returneaza si inchide conexiuni folosind Singleton, adica: are un constructor privat, o variabila statica
 * privata (singleInstance) si o metoda statica publica (getConnection())
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/management";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER); // încarcă driverul MySQL
        } catch (ClassNotFoundException e) {
            LOGGER.warning("MySQL JDBC Driver not found");
            e.printStackTrace();
        }
    }

    /**
     * Creeaza o conexiune la baza de date.
     * @return o instanta Connection sau null daca apare o eroare.
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASSWORD);
        } catch (SQLException e) {
            LOGGER.warning("Error while connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Returneaza o conexiune catre baza de date.
     * @return Connection activ
     */
    public static Connection getConnection() {return singleInstance.createConnection();}

    /**
     * Inchide conexiunea la baza de date.
     * @param connection conexiunea de inchis
     */
    public static void close(Connection connection){
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inchide un Statement SQL
     * @param statement obiectul Statement de inchis
     */
    public static void close(Statement statement){
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inchide un ResultSet SQL.\
     * @param resultSet obiectul ResultSet de inchis
     */
    public static void close(ResultSet resultSet){
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
