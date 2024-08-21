package ru.roznov.servlets_2.servlets;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.client.ClientActivityManager;
import ru.roznov.servlets_2.model.exceptions.ExceptionHandler;
import ru.roznov.servlets_2.model.user.UsersSearcher;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/delogin")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        final HttpSession session = req.getSession();
        try {
            if (!req.getSession().getAttribute("role").toString().equals("BLOCKED")) {
                final String login = session.getAttribute("login").toString();
                CommandParameters commandParameters = new CommandParameters();
                commandParameters.addParameter("id",UsersSearcher.getIdByLogin(login));
                CommandController.executeCommand(CommandName.MAKE_CLIENT_UNACTIVE,commandParameters);
            }
        } catch (SQLException | NullPointerException e) {
            ExceptionHandler.handleException("Error making client not active ", e);
        }
        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");

        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
    }

}