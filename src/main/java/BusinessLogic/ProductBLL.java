package BusinessLogic;

import DataAccess.ProductDAO;
import Model.Product;

import java.util.List;

public class ProductBLL {
    private final ProductDAO productDAO = new ProductDAO();

    /**
     * Inserează un produs nou în baza de date.
     *
     * @param product obiectul Product care va fi adăugat
     * @throws IllegalArgumentException dacă pretul sau stocul sunt invalide
     */
    public void insertProduct(Product product) {
        if (product.getPret() <= 0) {
            throw new IllegalArgumentException("Prețul trebuie să fie mai mare decât 0!");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stocul nu poate fi negativ!");
        }

        productDAO.insert(product);
    }
    public void updateProduct(Product product) {
        productDAO.update(product);
    }
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }
    public Product getProduct(int id) {
        return productDAO.findById(id);
    }
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
}
