package Model;

/**
 * Clasa Log reprezintă intregistrarea unei comenzi in istoricul bazei de date.
 * Contine informatii precum ID-ul log-ului si al comenzii, si numele clientului si al produsului
 */
public class Log {
    /** ID-ul logu-ului */
    private int idLog;
    /** ID-ul comenzii */
    private int orderID;
    /** Numele clientului */
    private String clientName;
    /** Numele produsului */
    private String productName;

    /** Constructori, toString */
    public Log(int idLog, int orderID, String clientName, String productName) {
        this.idLog = idLog;
        this.orderID = orderID;
        this.clientName = clientName;
        this.productName = productName;
    }

    public Log(){

    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    @Override
    public String toString() {
        return "Log{" +
                "id=" + idLog +
                ", orderID=" + orderID +
                '}';
    }


}
