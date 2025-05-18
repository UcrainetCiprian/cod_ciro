package BusinessLogic;

import DataAccess.OrderDAO;
import Model.Order_Table;

import java.util.List;

public class OrderBLL {
    private final OrderDAO orderDAO = new OrderDAO();

    /**
     * Inserează o comanda noua în baza de date.
     *
     * @param o obiectul Order care va fi adăugat
     * @throws IllegalArgumentException dacă numele cantitatea e invalida sau goala
     */
    public void insertOrder(Order_Table o) {
        if (o.getCantitate() <= 0) {
            throw new IllegalArgumentException("Trebuie sa fie o cantitate!");
        }

        int generatedId = orderDAO.insert(o);
        o.setIdOrder(generatedId);
    }
    public void delete(int idOrder) {
        orderDAO.delete(idOrder);
    }
    public Order_Table getOrder(int id) {
        return orderDAO.findById(id);
    }
    public List<Order_Table> getAllOrders() {
        return orderDAO.findAll();
    }
}
