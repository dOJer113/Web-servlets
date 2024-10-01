package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.Client;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class ShowFundedUserCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        UsersDAO usersDAO = (UsersDAO) request.getServletContext().getAttribute("UsersDAO");
        if (role == RoleEnum.ADMIN) {
            String login = request.getParameter("login");
            Client client = new Client();
            try {
                if (UsersSearcher.isExistsUser(login, usersDAO)) {
                    request.getSession().setAttribute("oldLogin", login);
                    client = UsersSearcher.getClientByLogin(login, usersDAO);

                } else {
                    System.err.println("No such user in db");
                }
            } catch (Exception e) {
                System.err.println("Error" + e.getMessage());
            }
            if (client.getId() != 0) {
                request.setAttribute("client", client);
                return new Page(RedirectEnum.FORWARD, "/WEB-INF/view/fundedClient.jsp");
            }
        }
        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=LOGIN");
    }
}
