package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.model.CarManager;
import ru.roznov.servlets_2.model.StoreManager;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.CarDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.StorageDAO;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.clients.RoleEnum;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.requests.RequestType;
import ru.roznov.servlets_2.objects.store.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HandlingRequestCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        StorageDAO storeDAO = (StorageDAO) request.getServletContext().getAttribute("StoreDAO");
        CarDAO carDAO = (CarDAO) request.getServletContext().getAttribute("CarDAO");
        if (role == RoleEnum.DRIVER) {
            CommandParameters commandParameters = new CommandParameters();
            int driverId = Integer.parseInt(request.getSession().getAttribute("id").toString());
            HttpSession session = request.getSession();
            if (session.getAttribute("storeId") != null) {
                int storeId = Integer.parseInt(request.getSession().getAttribute("storeId").toString());
                Store store = StoreManager.getStoreById(storeId, storeDAO);
                Car car = CarManager.getCarById(CarManager.getCarIdByDriverId(driverId, carDAO), carDAO);
                if (store.isCarAtStore(car)) {
                    commandParameters.addParameter("driverId", driverId);
                    commandParameters.addParameter("product", ProductEnum.valueOf(request.getParameter("productName")));
                    commandParameters.addParameter("count", Integer.parseInt(request.getParameter("count")));
                    commandParameters.addParameter("requestType", RequestType.valueOf(request.getParameter("requestType")));
                    commandParameters.addParameter("storeId", storeId);
                    CommandController.executeCommand(CommandName.MAKE_HANDLING_REQUEST, commandParameters);
                } else {
                    request.setAttribute("error", "Car is not at the store, make entry request");
                    return new Page(RedirectEnum.FORWARD, "/WEB-INF/view/handlingRequest.jsp");
                }
            } else {
                request.setAttribute("error", "Car is not at the store, make entry request");
                return new Page(RedirectEnum.FORWARD, "/WEB-INF/view/handlingRequest.jsp");
            }
            return new Page(RedirectEnum.SEND_REDIRECT, "?command=SHOW_SUCCESS");
        }
        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=LOGIN");
    }
}
