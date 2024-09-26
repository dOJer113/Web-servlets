package ru.roznov.servlets_2.objects.products;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
public class ProductsBase {

    private ProductsBase() {
    }

    public static int getIdByProductName(ProductEnum productName) {
        return DAOFactory.getInstance(DBType.ORACLE).getProductDAO().getProducts().stream().filter(product -> product.getName() == productName).findFirst().orElse(new Product()).getId();
    }

    public static ProductEnum getProductNameById(int id) {
        return DAOFactory.getInstance(DBType.ORACLE).getProductDAO().getProducts().stream().filter(product -> product.getId() == id).findFirst().orElse(new Product()).getName();
    }


}
