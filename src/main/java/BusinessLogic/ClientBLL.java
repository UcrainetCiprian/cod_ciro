package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;
import Model.Order_Table;
import BusinessLogic.OrderBLL;

import java.util.List;

public class ClientBLL {
    private final ClientDAO clientDAO = new ClientDAO();
    private final OrderBLL orderBLL = new OrderBLL();
    private final LogBLL logBLL = new LogBLL();


    /**
     * Insereaza un client nou în baza de date
     *
     * @param client obiectul Client care va fi adăugat
     * @throws IllegalArgumentException daca numele sau numarul de telefon sunt invalide
     */
    public void insertClient(Client client) {
        if (client.getNume() == null || client.getNume().isEmpty()) {
            throw new IllegalArgumentException("Numele clientului nu poate fi gol!");
        }
        if (client.getNrTel() == null || client.getNrTel().isEmpty()) {
            throw new IllegalArgumentException("Numărul de telefon este obligatoriu!");
        }

        clientDAO.insert(client);
    }
    public void updateClient(Client client) {
        clientDAO.update(client);
    }
    public void deleteClient(int id) {
        List<Order_Table> orders = orderBLL.getAllOrders().stream()
                .filter(o -> o.getClientID() == id)
                .toList();

        for (Order_Table order : orders) {
            logBLL.deleteLogsByOrderId(order.getIdOrder());
            orderBLL.delete(order.getIdOrder());
        }

        clientDAO.delete(id);
    }
    public Client getClient(int id) {
        return clientDAO.findById(id);
    }
    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }
}
