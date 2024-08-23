package ru.roznov.servlets_2.controler;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.RoleEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class FrontController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

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
        } else {
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }
    public static void authorizeClient(CommandParameters commandParameters) {
        String login = commandParameters.getParameter("login", String.class);
        HttpServletRequest req = commandParameters.getParameter("request", HttpServletRequest.class);
        final RoleEnum role = RoleEnum.valueOf(UsersSearcher.getRoleByLogin(login).toString());
        req.getSession().setAttribute("password", commandParameters.getParameter("password", String.class));
        commandParameters.addParameter("role", role);
        req.getSession().setAttribute("login", login);
        req.getSession().setAttribute("role", role);
        CommandParameters activateParameters = new CommandParameters();
        activateParameters.addParameter("id", UsersSearcher.getIdByLogin(login));
        CommandController.executeCommand(CommandName.MAKE_CLIENT_ACTIVE, activateParameters);
        CommandController.executeCommand(CommandName.MOVE_TO_MENU, commandParameters);
    }
}
