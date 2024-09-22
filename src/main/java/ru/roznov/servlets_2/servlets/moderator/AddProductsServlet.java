/*
package ru.roznov.servlets_2.servlets.moderator;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.store.ProductsBase;
import ru.roznov.servlets_2.objects.store.StorageBase;
import ru.roznov.servlets_2.objects.store.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addProduct")
public class AddProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandParameters commandParameters = new CommandParameters();
        int storeId = Integer.parseInt(req.getParameter("storeId"));
        commandParameters.addParameter("storeId", storeId);
        ProductEnum name = ProductEnum.valueOf(req.getParameter("productName"));
        commandParameters.addParameter("productId", ProductsBase.getIdByProductName(name));
        int count = Integer.parseInt(req.getParameter("count"));
        commandParameters.addParameter("productCount", count);
        if (StorageBase.isStoreExists(storeId)) {
            Store store = StorageBase.getStoreById(storeId);
            if (count >= 0 ||store.isStorageHaveProductAndCount(name, count)) {
                CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_STORE, commandParameters);
                store.loadProducts(name,count);
            }
        } else {
            System.err.println("No such store to add products");
        }
        req.getRequestDispatcher("/WEB-INF/view/moder.jsp").forward(req, resp);
    }
}
*/
