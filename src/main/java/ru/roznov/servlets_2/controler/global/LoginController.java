package ru.roznov.servlets_2.controler.global;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
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
        if (role.equals(RoleEnum.ADMIN)) {
            req.getRequestDispatcher("/WEB-INF/view/adm.jsp").forward(req, res);
        } else if (role.equals(RoleEnum.MODERATOR)) {
            req.getRequestDispatcher("/WEB-INF/view/moder.jsp").forward(req, res);
        } else if (role.equals(RoleEnum.BLOCKED)) {
            req.getRequestDispatcher("/WEB-INF/view/pageBlockedClient.jsp").forward(req, res);
        } else if (role.equals(RoleEnum.DRIVER)) {
            req.getRequestDispatcher("/WEB-INF/view/driver.jsp").forward(req, res);
        } else if (role.equals(RoleEnum.STOREKEEPER)) {
            req.getRequestDispatcher("/WEB-INF/view/keeper.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }

    public static void reAuthorizeClient(CommandParameters parameters) {
        HttpServletRequest req = parameters.getParameter("request", HttpServletRequest.class);
        HttpSession session = req.getSession();
        CommandParameters activateParameters = new CommandParameters();
        activateParameters.addParameter("id", UsersSearcher.getIdByLogin(session.getAttribute("login").toString()));
        CommandController.executeCommand(CommandName.MAKE_CLIENT_ACTIVE, activateParameters);
        CommandController.executeCommand(CommandName.MOVE_TO_MENU, parameters);
    }


}
