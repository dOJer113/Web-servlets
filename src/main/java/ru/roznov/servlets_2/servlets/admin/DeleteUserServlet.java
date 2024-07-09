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

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        try {
            if (UsersSearcher.isExistsUser(login)) {
                if (!UsersSearcher.getRoleByLogin(login).equals("ADMIN")) {
                    UserManager.deleteUser(login);
                } else {
                    System.err.println("Admin user can`t be deleted");
                }
            } else {
                System.err.println("No such user in db");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/clients");
    }
}
