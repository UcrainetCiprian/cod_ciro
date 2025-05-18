package Model;
/**
 * Clasa Client reprezinta un client din baza de date.
 * Contine informatii precum numele si numarul de telefon.
 */
public class Client {
    /** ID-ul unic al clientului */
    private int idClient;
    /** Numele clientului */
    private String nume;
    /** Numarul de telefon al clientului */
    private String nrTel;

    /** constructori, getter, toString */
    public Client(int idClient, String nume, String nrTel) {
        this.idClient = idClient;
        this.nume = nume;
        this.nrTel = nrTel;
    }

    public Client() {

    }

    public int getIdClient() {
        return idClient;
    }

    public String getNume() {
        return nume;
    }

    public String getNrTel() {
        return nrTel;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
    public void setNrTel(String nrTel) {
        this.nrTel = nrTel;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + idClient +
                ", nume='" + nume + '\'' +
                ", nrTel='" + nrTel + '\'' +
                '}';
    }

}
