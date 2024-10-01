package ru.roznov.servlets_2.objects.products;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.ProductDAO;
public class ProductsBase {

    private ProductsBase() {
    }

    public static int getIdByProductName(ProductEnum productName, ProductDAO productDAO) {
        return productDAO.getProducts().stream().filter(product -> product.getName() == productName).findFirst().orElse(new Product()).getId();
    }

    public static ProductEnum getProductNameById(int id, ProductDAO productDAO) {
        return productDAO.getProducts().stream().filter(product -> product.getId() == id).findFirst().orElse(new Product()).getName();
    }


}
