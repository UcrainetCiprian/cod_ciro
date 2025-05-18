package Model;

/**
 * Clasa Product reprezinta un produs vandut de companie.
 * Contine informatii despre nume, pret si stoc disponibil.
 */
public class Product {
    /** ID-ul unic al produsului */
    private int idProduct;;
    /** Numele produsului */
    private String  nume;
    /** Prețul produsului */
    private double pret;
    /** Stocul disponibil pentru produs */
    private int stock;

    /** constructori, getter, setter, toString */
    public Product(int idProduct, String nume, double pret, int stock) {
        this.idProduct = idProduct;
        this.nume = nume;
        this.pret = pret;
        this.stock = stock;
    }

    public Product() {

    }

    public int getIdProduct() {
        return idProduct;
    }

    public String  getNume() {
        return nume;
    }

    public double getPret() {
        return pret;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + idProduct +
                ", nume='" + nume + '\'' +
                ", pret=" + pret +
                ", stock=" + stock +
                '}';
    }

}
