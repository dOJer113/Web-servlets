package ru.roznov.servlets_2.servlets.admin;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.UserAndActivityManager;
import ru.roznov.servlets_2.model.user.UserManager;
import ru.roznov.servlets_2.model.user.UsersSearcher;

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
        CommandParameters commandParameters = new CommandParameters();
        String login = request.getParameter("login");
        commandParameters.addParameter("login", login);
        try {
            if (UsersSearcher.isExistsUser(login)) {
                if (!UsersSearcher.getRoleByLogin(login).equals("ADMIN")) {
                    CommandController.executeCommand(CommandName.DELETE_USER_AND_ACTIVITY,commandParameters);
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
