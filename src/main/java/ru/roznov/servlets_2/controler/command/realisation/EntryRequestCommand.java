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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EntryRequestCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.DRIVER) {
            CommandParameters commandParameters = new CommandParameters();
            HttpSession session = request.getSession();
            int driverId = Integer.parseInt(session.getAttribute("id").toString());
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            StorageDAO storageDAO = (StorageDAO) request.getServletContext().getAttribute("StoreDAO");
            CarDAO carDAO = (CarDAO) request.getServletContext().getAttribute("CarDAO");
            Car car = CarManager.getCarById(CarManager.getCarIdByDriverId(driverId, carDAO), carDAO);
            if (!StoreManager.isCarAtAnyStore(car, storageDAO)) {
                session.setAttribute("storeId", storeId);
                commandParameters.addParameter("storeId", storeId);
                commandParameters.addParameter("driverId", driverId);
                commandParameters.addParameter("StoreDAO", storageDAO);
                commandParameters.addParameter("CarDAO", carDAO);
                CommandController.executeCommand(CommandName.MAKE_ENTRY_REQUEST, commandParameters);
                return new Page(RedirectEnum.SEND_REDIRECT, "?command=SHOW_SUCCESS");
            } else {
                request.setAttribute("errorMessage", "Error! Car is already at some store!.");
            }
        }

        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=SHOW_ENTRY_REQUEST");
    }
}
