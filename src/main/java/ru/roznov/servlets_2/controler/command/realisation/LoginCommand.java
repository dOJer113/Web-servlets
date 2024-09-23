package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.Client;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

public class LoginCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        if (login == null) {
            HttpSession session = request.getSession();
            if (nonNull(session)) {
                login = request.getSession().getAttribute("login").toString();
            }
        }
        HttpSession session = request.getSession();
        Client client = UsersSearcher.getClientByLogin(login);
        if (client.getId() != 0) {
            final RoleEnum role = client.getRole();
            session.setAttribute("password", request.getParameter("password"));
            session.setAttribute("login", login);
            session.setAttribute("role", role);
            session.setAttribute("id", client.getId());
            CommandParameters activateParameters = new CommandParameters();
            activateParameters.addParameter("id", client.getId());
            CommandController.executeCommand(CommandName.MAKE_CLIENT_ACTIVE, activateParameters);
            if (role.equals(RoleEnum.ADMIN)) {
                return "/WEB-INF/view/adm.jsp";
            } else if (role.equals(RoleEnum.MODERATOR)) {
                return "/WEB-INF/view/moder.jsp";
            } else if (role.equals(RoleEnum.BLOCKED)) {
                return "/WEB-INF/view/pageBlockedClient.jsp";
            } else if (role.equals(RoleEnum.DRIVER)) {
                return "/WEB-INF/view/driver.jsp";
            } else if (role.equals(RoleEnum.STOREKEEPER)) {
                return "/WEB-INF/view/keeper.jsp";
            }
        }
        return "/WEB-INF/view/login.jsp";

    }
}