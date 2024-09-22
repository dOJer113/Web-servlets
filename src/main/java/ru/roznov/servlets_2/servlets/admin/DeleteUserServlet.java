/*
package ru.roznov.servlets_2.servlets.admin;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/deleteUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CommandParameters commandParameters = new CommandParameters();
        String login = request.getParameter("login");
        commandParameters.addParameter("login", login);
        if (UsersSearcher.isExistsUser(login)) {
            if (!UsersSearcher.getRoleByLogin(login).equals(RoleEnum.ADMIN)) {
                CommandController.executeCommand(CommandName.DELETE_USER_AND_ACTIVITY, commandParameters);
            } else {
                System.err.println("Admin user can`t be deleted");
            }
        } else {
            System.err.println("No such user in db");
        }
        request.getRequestDispatcher("/WEB-INF/view/adm.jsp").forward(request, response);
    }
}
*/
