package com.example.presentation;

import BusinessLogic.ProductBLL;
import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.LogBLL;
import Model.Client;
import Model.Log;
import Model.Order_Table;
import Model.Product;
import ReflectionUtils.ReflectionUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controllerul principal pentru interfata grafica.
 * Gestioneaza operatiile de CRUD pentru clienti si produse,
 * precum si plasarea comenzilor si logarea acestora.
 */
public class HelloController {
    @FXML
    private TextField clientIdField;

    @FXML
    private TextField clientNameField;

    @FXML
    private TextField clientPhoneField;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button viewByIdButton;

    @FXML
    private Button viewAllButton;

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, Integer> idColumn;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private Button addButtonProduct;

    @FXML
    private Button updateButtonProduct;

    @FXML
    private Button deleteButtonProduct;

    @FXML
    private Button viewByIdButtonProduct;

    @FXML
    private Button viewAllButtonProduct;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> idColumnProduct;

    @FXML
    private TableColumn<Product, String> nameColumnProduct;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private ComboBox<Product> productComboBox;

    @FXML
    private TextField productQuantityField;

    @FXML
    private Button placeOrderButton;

    @FXML
    private TableView<Log> logTable;

    @FXML
    private TableColumn<Log, Integer> idColumnLog;

    @FXML
    private TableColumn<Log, Integer> idColumnOrder;

    @FXML
    private TableColumn<Log, Integer> clientNameColumn;

    @FXML
    private TableColumn<Log, Integer> productMameColumn;

    @FXML
    private Button viewLogButton;

    /**
     * Initializeaza ComboBox-urile si le populeaza cu clienti si produse sortate.
     * Se apeleaza automat la incarcarea ferestrei si dupa fiecare modificare adusa celor 2 liste
     */
    @FXML
    private void initialize() {
        List<Client> clients = clientBLL.getAllClients().stream()
                .sorted((c1, c2) -> c1.getNume().compareToIgnoreCase(c2.getNume()))
                .toList();

        clientComboBox.setItems(FXCollections.observableArrayList(clients));

        List<Product> products = productBLL.getAllProducts().stream()
                .sorted((p1, p2) -> p1.getNume().compareToIgnoreCase(p2.getNume()))
                .toList();

        productComboBox.setItems(FXCollections.observableArrayList(products));

        clientComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                setText(empty || client == null ? null : client.getNume());
            }
        });
        clientComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                setText(empty || client == null ? null : client.getNume());
            }
        });

        productComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                setText(empty || product == null ? null : product.getNume());
            }
        });
        productComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                setText(empty || product == null ? null : product.getNume());
            }
        });

    }

    private final ClientBLL clientBLL = new ClientBLL();
    private final ProductBLL productBLL = new ProductBLL();
    private final OrderBLL orderBLL = new OrderBLL();
    private final LogBLL logBLL = new LogBLL();

    /**
     * Afiseaza toti clientii in tabelul principal.
     * Sorteaza clientii alfabetic dupa nume.
     */
    @FXML
    private void viewAllClients() {
        List<Client> clients = clientBLL.getAllClients().stream()
                .sorted(Comparator.comparing(Client::getNume))
                .collect(Collectors.toList());
        ReflectionUtils.loadTable(clientTable, clients, Client.class);

    }

    @FXML
    private void viewAllButton() {
        viewAllClients();
    }

    /**
     * Cauta si afiseaza in tabel doar clientul cu id-ul scris in TextField
     * Sfiseaza mesaje de confirmare sau eroare
     */
    @FXML
    private void findByIdClient() {
        try {
            int idClient = Integer.parseInt(clientIdField.getText());

            Client c = clientBLL.getClient(idClient);

            viewAllClients();

            if (c != null) {
                clientTable.getItems().clear();
                clientTable.getItems().add(c);

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succes");
                success.setHeaderText(null);
                success.setContentText("Client găsit cu succes!");
                success.showAndWait();
            } else {
                Alert notFound = new Alert(Alert.AlertType.WARNING);
                notFound.setTitle("Client inexistent");
                notFound.setHeaderText(null);
                notFound.setContentText("Nu există un client cu ID-ul introdus.");
                notFound.showAndWait();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare la cautare client");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Adauga un client nou folosind datele introduse in campurile de text
     * Valideaza inputul si afiseaza mesaje de confirmare sau eroare
     */
    @FXML
    private void addButtonActionClient() {
        try {
            String clientName = clientNameField.getText();
            clientName = clientName.trim();
            String clientPhone = clientPhoneField.getText();
            clientPhone = clientPhone.trim();

            Client c = new Client(0, clientName, clientPhone);

            clientBLL.insertClient(c);

            viewAllClients();
            initialize();

            clientNameField.clear();
            clientPhoneField.clear();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Succes");
            success.setContentText("Client adăugat cu succes!");
            success.showAndWait();
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare la adăugare client");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Modifica un client deja existent
     * Valideaza inputul si afiseaza mesaje de confirmare sau eroare
     */
    @FXML
    private void updateButtonActionClient() {
        try {
            int idClient = Integer.parseInt(clientIdField.getText());
            String clientName = clientNameField.getText();
            clientName = clientName.trim();
            String clientPhone = clientPhoneField.getText();
            clientPhone = clientPhone.trim();

            Client c = new Client(idClient, clientName, clientPhone);

            clientBLL.updateClient(c);

            viewAllClients();
            initialize();

            clientIdField.clear();
            clientNameField.clear();
            clientPhoneField.clear();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Succes");
            success.setContentText("Client modificat cu succes!");
            success.setHeaderText(null);
            success.showAndWait();
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare la modifcare client");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Sterge un client din baza de date
     * Valideaza inputul si afiseaza mesaje de confirmare sau eroare
     */
    @FXML
    private void deleteButtonActionClient() {
        try {
            int idClient = Integer.parseInt(clientIdField.getText());

            clientBLL.deleteClient(idClient);

            viewAllClients();
            initialize();

            clientIdField.clear();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Succes");
            success.setContentText("Client sters cu succes!");
            success.setHeaderText(null);
            success.showAndWait();

        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare la stergere client");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    /**
     * Afiseaza toate produsele in tabelul principal
     */
    @FXML
    private void viewAllProducts() {
        List<Product> products = productBLL.getAllProducts();
        ReflectionUtils.loadTable(productTable, products, Product.class);
    }

    /**
     * Adauga un produs in baza de date
     * Afiseaza mesaje de confirmare sau eroare
     */
    @FXML
    private void addButtonActionProduct() {
        try {
            String productName = productNameField.getText();
            productName = productName.trim();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            Product p = new Product(0, productName, price, stock);

            productBLL.insertProduct(p);

            viewAllProducts();
            initialize();

            productNameField.clear();
            priceField.clear();
            stockField.clear();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Succes");
            success.setContentText("Produs adăugat cu succes!");
            success.showAndWait();
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare la adăugare produs");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Modifica un produs din baza de date
     * Afiseaza mesaje de confirmare sau eroare
     */
    @FXML
    private void updateButtonActionProduct() {
        try {
            int idProduct = Integer.parseInt(productIdField.getText());
            String productName = productNameField.getText();
            productName = productName.trim();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            Product p = new Product(idProduct, productName, price, stock);

            productBLL.updateProduct(p);

            viewAllProducts();
            initialize();

            productIdField.clear();
            productNameField.clear();
            priceField.clear();
            stockField.clear();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Succes");
            success.setContentText("Produs modificat cu succes!");
            success.setHeaderText(null);
            success.showAndWait();
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare la modifcare produs");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Sterge un produs din baza de date
     * Afiseaza mesaje de confirmare sau eroare
     */
    @FXML
    private void deleteButtonActionProduct() {
        try {
            int idProduct = Integer.parseInt(productIdField.getText());

            productBLL.deleteProduct(idProduct);

            viewAllProducts();
            initialize();

            productIdField.clear();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Succes");
            success.setContentText("Produs sters cu succes!");
            success.setHeaderText(null);
            success.showAndWait();

        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare la stergere produs");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Cauta un produs in baza de date
     * Afiseaza mesaje de confirmare sau eroare
     */
    @FXML
    private void findByIdProduct() {
        try {
            int idProduct = Integer.parseInt(productIdField.getText());

            Product p = productBLL.getProduct(idProduct);

            viewAllClients();

            if (p != null) {
                productTable.getItems().clear();
                productTable.getItems().add(p);

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succes");
                success.setHeaderText(null);
                success.setContentText("Produs găsit cu succes!");
                success.showAndWait();
            } else {
                Alert notFound = new Alert(Alert.AlertType.WARNING);
                notFound.setTitle("Produs inexistent");
                notFound.setHeaderText(null);
                notFound.setContentText("Nu există un produs cu ID-ul introdus.");
                notFound.showAndWait();
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare la cautare produs");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Afiseaza toate comenzile in tabelul principal.
     */
    @FXML
    private void viewAllLogs(){
        List<Log> logs = logBLL.getAllLogs();
        ReflectionUtils.loadTable(logTable, logs, Log.class);
    }

    /**
     * Plaseaza o comanda noua pe baza selectiei din ComboBox-uri
     * Verifica daca exista suficient stoc, actualizeaza baza de date și adauga log-ul comenzii
     * Afiseaza alerte pentru succes sau erori
     */
    @FXML
    private void placeOrderButton(){
        Product p = new Product();
        p = productComboBox.getSelectionModel().getSelectedItem();
        Client c = new Client();
        c = clientComboBox.getSelectionModel().getSelectedItem();
        int quantity = Integer.parseInt(productQuantityField.getText());

        if(p.getStock() < quantity){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("EROARE STOC");
            alert.setContentText(String.format("Nu exista destule produse de tipul selectat in stoc.\n" +
                                                "Numarul produselor in stoc: %d", p.getStock()));
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else {
            try {
                p.setStock(p.getStock() - quantity);
                productBLL.updateProduct(p);

                Order_Table o = new Order_Table(0, c.getIdClient(), p.getIdProduct(), quantity);
                orderBLL.insertOrder(o);

                Log l = new Log(0,o.getIdOrder(),c.getNume(),p.getNume());
                logBLL.insertLog(l);

                initialize();

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succes");
                success.setHeaderText(null);
                success.setContentText("Comanda a fost plasată cu succes!");
                success.showAndWait();

                productComboBox.getSelectionModel().clearSelection();
                clientComboBox.getSelectionModel().clearSelection();
                productQuantityField.clear();

                viewAllProducts();
                viewAllLogs();

            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare la plasare comanda");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}