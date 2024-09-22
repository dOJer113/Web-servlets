package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.objects.store.ProductsBase;

public class ProductManager {
    public static void getProductsFromDB(CommandParameters commandParameters) {
        ProductsBase.setProductsFromList(DAOFactory.getInstance(DBType.ORACLE).getProductDAO().getProducts());
    }
}
