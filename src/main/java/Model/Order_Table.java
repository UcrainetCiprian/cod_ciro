package Model;

/**
 * Clasa Order_Table reprezinta o comanda realizata de un client
 * Contine ID-ul clientului, ID-ul produsului si cantitatea comandata
 */
public class Order_Table {
    /** ID-ul unic al comenzii */
    private int idOrder;;
    /** ID-ul clientului care a plasat comanda */
    private int clientID;
    /** ID-ul produsului comandat */
    private int productID;
    /** Cantitatea de produse comandata */
    private int cantitate;

    /** constructori, getter, setter, toString */
    public Order_Table(int idOrder, int clientID, int productID, int cantitate) {
        this.idOrder = idOrder;
        this.clientID = clientID;
        this.productID = productID;
        this.cantitate = cantitate;
    }

    public Order_Table() {

    }

    public int getIdOrder() {
        return idOrder;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getIdClient() {
        return clientID;
    }
    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }
    public void setClientID(int idClient) {
        this.clientID = idClient;
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + idOrder +
                ", clientID=" + clientID +
                ", productID=" + productID +
                ", cantitate=" + cantitate +
                '}';
    }


    public int getClientID() {
        return clientID;
    }
}
