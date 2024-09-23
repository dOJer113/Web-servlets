package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.ADMIN) {
            CommandParameters commandParameters = new CommandParameters();
            String login = request.getParameter("login");
            commandParameters.addParameter("login", login);
            if (UsersSearcher.isExistsUser(login)) {
                if (!UsersSearcher.getRoleByLogin(login).equals(RoleEnum.ADMIN)) {
                    CommandController.executeCommand(CommandName.DELETE_USER_AND_ACTIVITY, commandParameters);
                } else {
                    System.err.println("Admin user can`t be deleted");
                }
            } else {
                System.err.println("No such user in db");
            }
            return "/WEB-INF/view/adm.jsp";
        }
        return request.getContextPath()+"/controller?command=LOGIN";
    }
}
