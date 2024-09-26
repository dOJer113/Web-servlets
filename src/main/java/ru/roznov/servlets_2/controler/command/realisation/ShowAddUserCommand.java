package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class ShowAddUserCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.ADMIN) {
            return new Page(RedirectEnum.FORWARD,"/WEB-INF/view/adduser.jsp");
        }
        return new Page(RedirectEnum.FORWARD,request.getContextPath()+"/controller?command=LOGIN");
    }
}
