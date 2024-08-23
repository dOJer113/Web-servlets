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

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String login = request.getParameter("login");
        CommandParameters commandParameters = new CommandParameters();
        commandParameters.addParameter("id", id);
        commandParameters.addParameter("login", login);
        commandParameters.addParameter("password", Integer.parseInt(request.getParameter("password")));
        commandParameters.addParameter("role", request.getParameter("role"));
        if (!UsersSearcher.isExistsUser(login) && !UsersSearcher.isExistsUser(id)) {
            CommandController.executeCommand(CommandName.ADD_USER_AND_ACTIVITY, commandParameters);
        } else {
            System.err.println("User already exists");
        }
        response.sendRedirect(request.getContextPath() + "/clients");
    }
}
