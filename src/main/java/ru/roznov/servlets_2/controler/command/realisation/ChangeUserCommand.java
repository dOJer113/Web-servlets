package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class ChangeUserCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        UsersDAO usersDAO = (UsersDAO) request.getServletContext().getAttribute("UsersDAO");
        if (role == RoleEnum.ADMIN) {
            CommandParameters commandParameters = new CommandParameters();

            CommandParameters commandParametersForOldLogin = new CommandParameters();
            String oldLogin = request.getSession().getAttribute("oldLogin").toString();
            commandParametersForOldLogin.addParameter("login", oldLogin);
            CommandController.executeCommand(CommandName.IS_CLIENT_BLOCKED, commandParametersForOldLogin);
            boolean block = commandParametersForOldLogin.getParameter("block", Boolean.class);
            if (block) {
                commandParametersForOldLogin.addParameter("client", UsersSearcher.getClientByLogin(oldLogin, usersDAO));
                CommandController.executeCommand(CommandName.UNBLOCK_CLIENT, commandParametersForOldLogin);

            }
            commandParameters.addParameter("id", Integer.parseInt(request.getParameter("id")));
            commandParameters.addParameter("login", request.getParameter("login"));
            commandParameters.addParameter("password", Integer.parseInt(request.getParameter("password")));
            commandParameters.addParameter("role", request.getParameter("role"));
            commandParameters.addParameter("UsersDAO", usersDAO);
            CommandController.executeCommand(CommandName.UPDATE_USER, commandParameters);
            if (block) {
                commandParameters.addParameter("client", UsersSearcher.getClientByLogin(request.getParameter("login"), usersDAO));
                CommandController.executeCommand(CommandName.BLOCK_CLIENT, commandParameters);
            }
            return new Page(RedirectEnum.SEND_REDIRECT, "?command=SHOW_SUCCESS");
        }
        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=LOGIN");
    }
}
