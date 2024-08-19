package ru.roznov.servlets_2.servlets;

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
        final String login = session.getAttribute("login").toString();

        try {
            ClientActivityManager.makeClientUnActive(UsersSearcher.getIdByLogin(login));
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error making client not active", e);
        }
        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");

        getServletContext().getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);

    }

}