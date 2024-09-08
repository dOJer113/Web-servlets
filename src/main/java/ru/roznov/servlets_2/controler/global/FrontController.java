package ru.roznov.servlets_2.controler.global;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.Client;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/")
public class FrontController extends HttpServlet {
    public static final int LOG_OUT_TIMER = 2_999_999;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
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
        } else if (role.equals(RoleEnum.DRIVER)) {
            req.getRequestDispatcher("/WEB-INF/view/driver.jsp").forward(req, res);
        } else if (role.equals(RoleEnum.STOREKEEPER)) {
            req.getRequestDispatcher("/WEB-INF/view/keeper.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }

    public static void authorizeClient(CommandParameters commandParameters) {
        CommandController.executeCommand(CommandName.START_LISTENER_TIMER, commandParameters);
        HttpServletRequest req = commandParameters.getParameter("request", HttpServletRequest.class);
        String login = req.getParameter("login");
        HttpSession session = req.getSession();
        Client client = UsersSearcher.getClientByLogin(login);
        final RoleEnum role = client.getRole();
        session.setAttribute("password", commandParameters.getParameter("password", String.class));
        session.setAttribute("login", login);
        session.setAttribute("role", role);
        session.setAttribute("id", client.getId());
        commandParameters.addParameter("role", role);
        CommandParameters activateParameters = new CommandParameters();
        activateParameters.addParameter("id", client.getId());
        CommandController.executeCommand(CommandName.MAKE_CLIENT_ACTIVE, activateParameters);
        CommandController.executeCommand(CommandName.MOVE_TO_MENU, commandParameters);
    }

    public static void reAuthorizeClient(CommandParameters parameters) {
        HttpServletRequest req = parameters.getParameter("request", HttpServletRequest.class);
        HttpSession session = req.getSession();
        CommandParameters activateParameters = new CommandParameters();
        activateParameters.addParameter("id", UsersSearcher.getIdByLogin(session.getAttribute("login").toString()));
        CommandController.executeCommand(CommandName.MAKE_CLIENT_ACTIVE, activateParameters);
        CommandController.executeCommand(CommandName.START_LISTENER_TIMER, parameters);
        CommandController.executeCommand(CommandName.MOVE_TO_MENU, parameters);
    }

    public static void startListenerTimer(CommandParameters commandParameters) {
        HttpServletRequest req = commandParameters.getParameter("request", HttpServletRequest.class);
        String login = req.getParameter("login");
        AppListener appListener = (AppListener) req.getServletContext().getAttribute("appListener");
        appListener.scheduleTask(new LogOutTimerTask(login), FrontController.LOG_OUT_TIMER);
    }
}
