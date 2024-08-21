package ru.roznov.servlets_2.servlets.admin;


import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.client.ClientActivityManager;
import ru.roznov.servlets_2.model.client.ClientActivitySearcher;
import ru.roznov.servlets_2.model.exceptions.ExceptionHandler;
import ru.roznov.servlets_2.model.user.UserManager;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.UserWithActivity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clients")
public class ViewClientsServlet extends HttpServlet {
    @Override
    public void init() {
        try {
            CommandController.executeCommand(CommandName.GET_VALUES_FROM_ORACLE_DB, new CommandParameters());
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error getting values from ORACLE DB ", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserWithActivity> users = ClientActivitySearcher.getUserWithActivity();
        req.setAttribute("clients", users);
        req.getRequestDispatcher("/WEB-INF/view/viewClients.jsp").forward(req, resp);
    }
}
