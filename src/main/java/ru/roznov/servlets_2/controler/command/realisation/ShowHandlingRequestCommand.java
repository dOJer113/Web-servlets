package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class ShowHandlingRequestCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.DRIVER) {
            return "/WEB-INF/view/handlingRequest.jsp";
        }
        return request.getContextPath()+"/controller?command=LOGIN";
    }
}
