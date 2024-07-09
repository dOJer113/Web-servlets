package ru.roznov.servlets_2.servlets.admin;

import ru.roznov.servlets_2.model.UserManager;
import ru.roznov.servlets_2.model.UsersSearcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String login = request.getParameter("login");
        int password = Integer.parseInt(request.getParameter("password"));
        String role = request.getParameter("role");
        try {
            if (!UsersSearcher.isExistsUser(login) && !UsersSearcher.isExistsUser(id)) {
                UserManager.addUser(id, login, password, role);
            } else {
                System.err.println("User already exists");
            }
        } catch (SQLException e) {
            System.err.println("Error adding user " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/clients");
    }
}
