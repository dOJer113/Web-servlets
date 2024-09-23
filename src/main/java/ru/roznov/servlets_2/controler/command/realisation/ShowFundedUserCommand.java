package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.Client;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class ShowFundedUserCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.ADMIN) {
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
            if (client.getId() != 0) {
                request.setAttribute("client", client);
                return "/WEB-INF/view/fundedClient.jsp";
            }
        }
        return request.getContextPath() + "/controller?command=LOGIN";
    }
}
