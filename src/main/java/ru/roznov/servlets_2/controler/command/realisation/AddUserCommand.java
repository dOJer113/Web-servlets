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

public class AddUserCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        UsersDAO usersDAO = (UsersDAO) request.getServletContext().getAttribute("UsersDAO");
        StorageDAO storageDAO = (StorageDAO) request.getServletContext().getAttribute("StoreDAO");
        CarDAO carDAO = (CarDAO) request.getServletContext().getAttribute("CarDAO");
        if (role == RoleEnum.ADMIN) {
            int id = Integer.parseInt(request.getParameter("id"));
            String login = request.getParameter("login");
            CommandParameters commandParameters = new CommandParameters();
            commandParameters.addParameter("id", id);
            commandParameters.addParameter("login", login);
            commandParameters.addParameter("password", Integer.parseInt(request.getParameter("password")));
            commandParameters.addParameter("role", request.getParameter("role"));
            if (!UsersSearcher.isExistsUser(login, usersDAO) && !UsersSearcher.isExistsUser(id, usersDAO)) {
                commandParameters.addParameter("UsersDAO", usersDAO);
                ClientActivityDAO activityDAO = (ClientActivityDAO) request.getServletContext().getAttribute("ActivityDAO");
                commandParameters.addParameter("ActivityDAO", activityDAO);
                commandParameters.addParameter("StoreDAO", storageDAO);
                commandParameters.addParameter("CarDAO", carDAO);
                CommandController.executeCommand(CommandName.ADD_USER_AND_ACTIVITY, commandParameters);
            } else {
                System.err.println("User already exists");
            }

            return new Page(RedirectEnum.SEND_REDIRECT, "?command=SHOW_SUCCESS");
        }
        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=LOGIN");
    }
}
