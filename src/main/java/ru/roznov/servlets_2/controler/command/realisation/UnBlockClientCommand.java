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

public class UnBlockClientCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        UsersDAO usersDAO = (UsersDAO) request.getServletContext().getAttribute("UsersDAO");
        if (role == RoleEnum.MODERATOR) {
            String login = request.getParameter("login");
            try {
                CommandParameters blockParameters = new CommandParameters();
                blockParameters.addParameter("login", login);
                CommandController.executeCommand(CommandName.IS_CLIENT_BLOCKED, blockParameters);
                boolean block = blockParameters.getParameter("block", Boolean.class);
                if (UsersSearcher.isExistsUser(login, usersDAO) || block) {
                    if (block) {
                        CommandParameters commandParameters = new CommandParameters();
                        commandParameters.addParameter("client", UsersSearcher.getClientByLogin(login, usersDAO));
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
            return new Page(RedirectEnum.SEND_REDIRECT, "?command=SHOW_SUCCESS");
        }
        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=LOGIN");
    }
}
