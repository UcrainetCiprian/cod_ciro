package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;

import java.util.List;

public class ClientBLL {
    private final ClientDAO clientDAO = new ClientDAO();

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
    public void deleteClient(int idClient) {
        clientDAO.delete(idClient);
    }
    public Client getClient(int id) {
        return clientDAO.findById(id);
    }
    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }
}
