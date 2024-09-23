package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.Client;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class BlockClientCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.MODERATOR) {
            String login = request.getParameter("login");
            try {
                if (UsersSearcher.isExistsUser(login)) {
                    CommandParameters blockParameters = new CommandParameters();
                    blockParameters.addParameter("login", login);
                    CommandController.executeCommand(CommandName.IS_CLIENT_BLOCKED, blockParameters);
                    if (!blockParameters.getParameter("block", Boolean.class)) {
                        CommandParameters commandParameters = new CommandParameters();
                        Client client = UsersSearcher.getClientByLogin(login);
                        if (client.getId() != 0) {
                            commandParameters.addParameter("client", UsersSearcher.getClientByLogin(login));
                            CommandController.executeCommand(CommandName.BLOCK_CLIENT, commandParameters);
                        }
                    } else {
                        System.err.println("Client already blocked");
                    }
                } else {
                    System.err.println("No such client in db");
                }
            } catch (Exception e) {
                System.err.println("Error" + e.getMessage());
            }
            return "/WEB-INF/view/moder.jsp";
        }
        return request.getContextPath() + "/controller?command=LOGIN";
    }
}
