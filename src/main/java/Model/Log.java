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
    @Override
    public String toString() {
        return "Log{" +
                "id=" + idLog +
                ", orderID=" + orderID +
                '}';
    }


}
