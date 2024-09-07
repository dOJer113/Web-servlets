package ru.roznov.servlets_2.servlets.driver;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.requests.RequestType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        commandParameters.addParameter("driverId", Integer.parseInt(req.getSession().getAttribute("id").toString()));
        commandParameters.addParameter("product", ProductEnum.valueOf(req.getParameter("productName")));
        commandParameters.addParameter("count", Integer.parseInt(req.getParameter("count")));
        commandParameters.addParameter("requestType", RequestType.valueOf(req.getParameter("requestType")));
        CommandController.executeCommand(CommandName.MAKE_HANDLING_REQUEST, commandParameters);
        req.getRequestDispatcher("/WEB-INF/view/driver.jsp").forward(req, resp);
    }

}
