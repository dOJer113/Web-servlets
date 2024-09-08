package ru.roznov.servlets_2.servlets.uviversal;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.ExceptionHandler;
import ru.roznov.servlets_2.model.user.UsersSearcher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final HttpSession session = req.getSession();
        try {
            if (!req.getSession().getAttribute("role").toString().equals("BLOCKED")) {
                final String login = session.getAttribute("login").toString();
                CommandParameters commandParameters = new CommandParameters();
                commandParameters.addParameter("id", UsersSearcher.getIdByLogin(login));
                CommandController.executeCommand(CommandName.MAKE_CLIENT_UNACTIVE, commandParameters);
            }
        } catch (NullPointerException e) {
            ExceptionHandler.handleException("Error making client not active ", e);
        }
        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");

        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
    }

}