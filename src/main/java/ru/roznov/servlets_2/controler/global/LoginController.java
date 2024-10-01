package ru.roznov.servlets_2.controler.global;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ClientActivityDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController {


    public static void moveToMenu(CommandParameters commandParameters)
            throws ServletException, IOException {
        RoleEnum role = commandParameters.getParameter("role", RoleEnum.class);
        HttpServletResponse res = commandParameters.getParameter("response", HttpServletResponse.class);
        HttpServletRequest req = commandParameters.getParameter("request", HttpServletRequest.class);
        req.getRequestDispatcher(role.getPage().getUrl()).forward(req, res);
    }

    public static void reAuthorizeClient(CommandParameters parameters) {
        HttpServletRequest req = parameters.getParameter("request", HttpServletRequest.class);
        HttpSession session = req.getSession();
        ClientActivityDAO activityDAO = (ClientActivityDAO) req.getServletContext().getAttribute("ActivityDAO");
        UsersDAO usersDAO = (UsersDAO) req.getServletContext().getAttribute("UsersDAO");
        CommandParameters activateParameters = new CommandParameters();
        int id = UsersSearcher.getIdByLogin(session.getAttribute("login").toString(), usersDAO);
        if (id != 0) {
            activateParameters.addParameter("id", id);
            activateParameters.addParameter("ActivityDAO", activityDAO);
            CommandController.executeCommand(CommandName.MAKE_CLIENT_ACTIVE, activateParameters);
            CommandController.executeCommand(CommandName.MOVE_TO_MENU, parameters);
        }
    }


}
