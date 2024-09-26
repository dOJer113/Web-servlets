package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.model.client.ClientActivityManager;
import ru.roznov.servlets_2.objects.clients.RoleEnum;
import ru.roznov.servlets_2.objects.clients.UserWithActivity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowClientsCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.ADMIN) {
            List<UserWithActivity> users = ClientActivityManager.getUserWithActivity();
            request.setAttribute("clients", users);
            return new Page(RedirectEnum.FORWARD,"/WEB-INF/view/viewClients.jsp");
        }
        return new Page(RedirectEnum.FORWARD,request.getContextPath() + "/controller?command=LOGIN");
    }
}
