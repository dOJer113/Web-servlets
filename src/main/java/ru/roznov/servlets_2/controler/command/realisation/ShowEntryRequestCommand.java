package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.model.CarManager;
import ru.roznov.servlets_2.model.StoreManager;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.CarDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.StorageDAO;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class ShowEntryRequestCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.DRIVER) {
            StorageDAO storageDAO = (StorageDAO) request.getServletContext().getAttribute("StoreDAO");
            CarDAO carDAO = (CarDAO) request.getServletContext().getAttribute("CarDAO");
            int driverId = Integer.parseInt(request.getSession().getAttribute("id").toString());
            int carId = CarManager.getCarIdByDriverId(driverId, carDAO);
            int storeId = StoreManager.getStoreIdByCarId(carId, storageDAO);
            if (storeId != 0) {
                request.getSession().setAttribute("storeId", storeId);
            }
            return new Page(RedirectEnum.FORWARD, "/WEB-INF/view/enterStoreId.jsp");
        }
        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=LOGIN");

    }
}
