package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements FrontControllerCommand {

    @Override
    public Page execute(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        session.invalidate();
        return new Page(RedirectEnum.FORWARD,  "/WEB-INF/view/login.jsp");
    }
}
