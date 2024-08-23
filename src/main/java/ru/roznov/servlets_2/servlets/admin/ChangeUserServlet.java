package ru.roznov.servlets_2.servlets.admin;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.exceptions.ExceptionHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/changing")
public class ChangeUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            CommandParameters commandParameters = new CommandParameters();
            commandParameters.addParameter("id", Integer.parseInt(req.getParameter("id")));
            commandParameters.addParameter("login", req.getParameter("login"));
            commandParameters.addParameter("password", Integer.parseInt(req.getParameter("password")));
            commandParameters.addParameter("role", req.getParameter("role"));
            CommandController.executeCommand(CommandName.UPDATE_USER, commandParameters);
            resp.sendRedirect(req.getContextPath() + "/clients");
        } catch (IOException e) {
            ExceptionHandler.handleException("Error updating user", e);
        }

    }
}
