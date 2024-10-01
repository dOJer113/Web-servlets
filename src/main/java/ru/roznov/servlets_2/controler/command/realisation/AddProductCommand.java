package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.model.StoreManager;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ProductDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.StorageDAO;
import ru.roznov.servlets_2.objects.clients.RoleEnum;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.products.ProductsBase;
import ru.roznov.servlets_2.objects.store.Store;

import javax.servlet.http.HttpServletRequest;

public class AddProductCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        ProductDAO productDAO = (ProductDAO) request.getServletContext().getAttribute("ProductDAO");
        if (role == RoleEnum.MODERATOR) {
            StorageDAO storageDAO = (StorageDAO) request.getServletContext().getAttribute("StoreDAO");
            CommandParameters commandParameters = new CommandParameters();
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            commandParameters.addParameter("storeId", storeId);
            ProductEnum name = ProductEnum.valueOf(request.getParameter("productName"));
            commandParameters.addParameter("productId", ProductsBase.getIdByProductName(name, productDAO));
            int count = Integer.parseInt(request.getParameter("count"));
            commandParameters.addParameter("productCount", count);
            commandParameters.addParameter("ProductDAO", productDAO);
            commandParameters.addParameter("StoreDAO", storageDAO);
            if (StoreManager.isStoreExists(storeId, storageDAO)) {
                Store store = StoreManager.getStoreById(storeId, storageDAO);
                if (count >= 0 || store.isStorageHaveProductAndCount(name, count)) {
                    CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_STORE, commandParameters);
                    store.loadProducts(name, count);
                }
            } else {
                System.err.println("No such store to add products");
            }
            return new Page(RedirectEnum.SEND_REDIRECT, "?command=SHOW_SUCCESS");
        }
        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=LOGIN");
    }
}
