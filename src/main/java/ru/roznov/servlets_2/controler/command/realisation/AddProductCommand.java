package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.model.StoreManager;
import ru.roznov.servlets_2.objects.clients.RoleEnum;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.products.ProductsBase;
import ru.roznov.servlets_2.objects.store.Store;

import javax.servlet.http.HttpServletRequest;

public class AddProductCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.MODERATOR) {
            CommandParameters commandParameters = new CommandParameters();
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            commandParameters.addParameter("storeId", storeId);
            ProductEnum name = ProductEnum.valueOf(request.getParameter("productName"));
            commandParameters.addParameter("productId", ProductsBase.getIdByProductName(name));
            int count = Integer.parseInt(request.getParameter("count"));
            commandParameters.addParameter("productCount", count);
            if (StoreManager.isStoreExists(storeId)) {
                Store store = StoreManager.getStoreById(storeId);
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
