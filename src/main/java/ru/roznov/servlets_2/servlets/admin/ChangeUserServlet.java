package ru.roznov.servlets_2.servlets.admin;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update")
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
            req.getRequestDispatcher("/WEB-INF/view/adm.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            ExceptionHandler.handleException("Error updating user", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/searchUser.jsp").forward(req, resp);
    }
}
