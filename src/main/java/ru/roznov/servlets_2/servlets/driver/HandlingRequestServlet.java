package ru.roznov.servlets_2.servlets.driver;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.cars.CarBase;
import ru.roznov.servlets_2.objects.cars.DriverIdByCarId;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.requests.RequestType;
import ru.roznov.servlets_2.objects.store.StorageBase;
import ru.roznov.servlets_2.objects.store.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/handlingRequest")
public class HandlingRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/handlingRequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandParameters commandParameters = new CommandParameters();
        int driverId = Integer.parseInt(req.getSession().getAttribute("id").toString());
        HttpSession session = req.getSession();
        if (session.getAttribute("storeId") != null) {
            int storeId = Integer.parseInt(req.getSession().getAttribute("storeId").toString());
            Store store = StorageBase.getStoreById(storeId);
            Car car = CarBase.getCarById(DriverIdByCarId.getCarIdByDriverId(driverId));
            if (store.isCarAtStore(car)) {
                commandParameters.addParameter("driverId", driverId);
                commandParameters.addParameter("product", ProductEnum.valueOf(req.getParameter("productName")));
                commandParameters.addParameter("count", Integer.parseInt(req.getParameter("count")));
                commandParameters.addParameter("requestType", RequestType.valueOf(req.getParameter("requestType")));
                CommandController.executeCommand(CommandName.MAKE_HANDLING_REQUEST, commandParameters);
            } else {
                req.setAttribute("error", "Car is not at the store, make entry request");
                req.getRequestDispatcher("/WEB-INF/view/handlingRequest.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Car is not at the store, make entry request");
            req.getRequestDispatcher("/WEB-INF/view/handlingRequest.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/WEB-INF/view/driver.jsp").forward(req, resp);

    }

}
