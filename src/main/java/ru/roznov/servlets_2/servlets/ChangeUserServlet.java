package ru.roznov.servlets_2.servlets;

import ru.roznov.servlets_2.model.UserManager;
import ru.roznov.servlets_2.model.dao.ExceptionHandler;
import ru.roznov.servlets_2.objects.RoleEnum;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/changing")
public class ChangeUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        int id = Integer.parseInt(req.getParameter("id"));
        int password = Integer.parseInt(req.getParameter("password"));
        RoleEnum role = RoleEnum.valueOf(req.getParameter("role"));
        try {
            UserManager.updateUser(id, login, password, role);
            resp.sendRedirect(req.getContextPath() + "/clients");
        } catch (SQLException | IOException e) {
            ExceptionHandler.handleException("Error updating user", e);
        }

    }
}
