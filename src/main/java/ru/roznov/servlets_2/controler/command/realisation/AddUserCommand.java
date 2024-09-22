package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.model.user.UsersSearcher;

import javax.servlet.http.HttpServletRequest;

public class AddUserCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String login = request.getParameter("login");
        CommandParameters commandParameters = new CommandParameters();
        commandParameters.addParameter("id", id);
        commandParameters.addParameter("login", login);
        commandParameters.addParameter("password", Integer.parseInt(request.getParameter("password")));
        commandParameters.addParameter("role", request.getParameter("role"));
        if (!UsersSearcher.isExistsUser(login) && !UsersSearcher.isExistsUser(id)) {
            CommandController.executeCommand(CommandName.ADD_USER_AND_ACTIVITY, commandParameters);
        } else {
            System.err.println("User already exists");
        }
        return "/WEB-INF/view/adm.jsp";
    }
}
