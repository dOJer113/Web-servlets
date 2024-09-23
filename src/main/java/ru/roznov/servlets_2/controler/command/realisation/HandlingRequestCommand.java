package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.cars.CarBase;
import ru.roznov.servlets_2.objects.cars.DriverIdByCarId;
import ru.roznov.servlets_2.objects.clients.RoleEnum;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.requests.RequestType;
import ru.roznov.servlets_2.objects.store.StorageBase;
import ru.roznov.servlets_2.objects.store.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HandlingRequestCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.DRIVER) {
            CommandParameters commandParameters = new CommandParameters();
            int driverId = Integer.parseInt(request.getSession().getAttribute("id").toString());
            HttpSession session = request.getSession();
            if (session.getAttribute("storeId") != null) {
                int storeId = Integer.parseInt(request.getSession().getAttribute("storeId").toString());
                Store store = StorageBase.getStoreById(storeId);
                Car car = CarBase.getCarById(DriverIdByCarId.getCarIdByDriverId(driverId));
                if (store.isCarAtStore(car)) {
                    commandParameters.addParameter("driverId", driverId);
                    commandParameters.addParameter("product", ProductEnum.valueOf(request.getParameter("productName")));
                    commandParameters.addParameter("count", Integer.parseInt(request.getParameter("count")));
                    commandParameters.addParameter("requestType", RequestType.valueOf(request.getParameter("requestType")));
                    commandParameters.addParameter("storeId", storeId);
                    CommandController.executeCommand(CommandName.MAKE_HANDLING_REQUEST, commandParameters);
                } else {
                    request.setAttribute("error", "Car is not at the store, make entry request");
                    return "/WEB-INF/view/handlingRequest.jsp";
                }
            } else {
                request.setAttribute("error", "Car is not at the store, make entry request");
                return "/WEB-INF/view/handlingRequest.jsp";
            }
            return "/WEB-INF/view/driver.jsp";
        }
        return request.getContextPath()+"/controller?command=LOGIN";
    }
}
