package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.CarDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ClientActivityDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.StorageDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        UsersDAO usersDAO = (UsersDAO) request.getServletContext().getAttribute("UsersDAO");
        StorageDAO storageDAO = (StorageDAO) request.getServletContext().getAttribute("StoreDAO");
        ClientActivityDAO activityDAO = (ClientActivityDAO) request.getServletContext().getAttribute("ActivityDAO");
        CarDAO carDAO = (CarDAO) request.getServletContext().getAttribute("CarDAO");
        if (role == RoleEnum.ADMIN) {
            CommandParameters commandParameters = new CommandParameters();
            String login = request.getParameter("login");
            commandParameters.addParameter("login", login);
            commandParameters.addParameter("UsersDAO", usersDAO);
            if (UsersSearcher.isExistsUser(login, usersDAO)) {
                if (!UsersSearcher.getRoleByLogin(login, usersDAO).equals(RoleEnum.ADMIN)) {
                    commandParameters.addParameter("ActivityDAO", activityDAO);
                    commandParameters.addParameter("StoreDAO", storageDAO);
                    commandParameters.addParameter("CarDAO",carDAO);
                    CommandController.executeCommand(CommandName.DELETE_USER_AND_ACTIVITY, commandParameters);
                } else {
                    System.err.println("Admin user can`t be deleted");
                }
            } else {
                System.err.println("No such user in db");
            }
            return new Page(RedirectEnum.SEND_REDIRECT, "?command=SHOW_SUCCESS");
        }
        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=LOGIN");
    }
}
