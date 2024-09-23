package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.model.client.ClientActivitySearcher;
import ru.roznov.servlets_2.objects.clients.RoleEnum;
import ru.roznov.servlets_2.objects.clients.UserWithActivity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowClientsCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.ADMIN) {
            List<UserWithActivity> users = ClientActivitySearcher.getUserWithActivity();
            request.setAttribute("clients", users);
            return "/WEB-INF/view/viewClients.jsp";
        }
        return request.getContextPath()+"/controller?command=LOGIN";
    }
}
