package ru.roznov.servlets_2.servlets.moderator;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.block.ClientBlocker;
import ru.roznov.servlets_2.model.user.UsersSearcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/unBlockClient")
public class UnBlockClientServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        try {
            if (UsersSearcher.isExistsUser(login) || ClientBlocker.isClientBlocked(login)) {
                if (ClientBlocker.isClientBlocked(login)) {
                    CommandParameters commandParameters = new CommandParameters();
                    commandParameters.addParameter("client", UsersSearcher.getClientByLogin(login));
                    CommandController.executeCommand(CommandName.UNBLOCK_CLIENT, commandParameters);
                } else {
                    System.err.println("Client already not blocked");
                }
            } else {
                System.err.println("No such client in db");
            }
        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/view/moder.jsp").forward(req, resp);
    }
}
