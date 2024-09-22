package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.Client;

import javax.servlet.http.HttpServletRequest;

public class ShowFundedUserCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        Client client = new Client();
        try {
            if (UsersSearcher.isExistsUser(login)) {
                request.getSession().setAttribute("oldLogin", login);
                client = UsersSearcher.getClientByLogin(login);

            } else {
                System.err.println("No such user in db");
            }
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
        request.setAttribute("client", client);
        return "/WEB-INF/view/fundedClient.jsp";
    }
}
