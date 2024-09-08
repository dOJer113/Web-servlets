package ru.roznov.servlets_2.servlets.driver;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/entryRequest")
public class EntryRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/enterStoreId.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandParameters commandParameters = new CommandParameters();
        int storeId = Integer.parseInt(req.getParameter("storeId"));
        req.getSession().setAttribute("storeId", storeId);
        commandParameters.addParameter("storeId", storeId);
        commandParameters.addParameter("driverId", Integer.parseInt(req.getSession().getAttribute("id").toString()));
        CommandController.executeCommand(CommandName.MAKE_ENTRY_REQUEST, commandParameters);
        req.getRequestDispatcher("/WEB-INF/view/driver.jsp").forward(req, resp);
    }
}
