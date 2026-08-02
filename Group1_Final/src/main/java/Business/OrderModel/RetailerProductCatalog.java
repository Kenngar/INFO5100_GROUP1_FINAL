/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.OrderModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author archil
 */
public class RetailerProductCatalog {

    private List<Product> productCatalog;

    public RetailerProductCatalog() {
        productCatalog = new ArrayList<Product>();
    }

    public List<Product> getProductcatalog() {
        return productCatalog;
    }

    public Product addProduct() {
        Product p = new Product();
        productCatalog.add(p);
        return p;
    }

    public void removeProduct(Product p) {
        productCatalog.remove(p);
    }
     public Product searchProductByName(String name) {
        if (name == null) {
            return null;
        }
        for (Product product : productCatalog) {
            if (product.getProdName() != null
                    && product.getProdName().equalsIgnoreCase(name.trim())) {
                return product;
            }
        }
        return null;
    }

    public Product searchProduct(int id) {
        for (Product product : productCatalog) {
            if (product.getModelNumber() == id) {
                return product;
            }
        }
        return null;
    }
}
