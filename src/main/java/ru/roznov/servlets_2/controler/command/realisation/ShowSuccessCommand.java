package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;

import javax.servlet.http.HttpServletRequest;

public class ShowSuccessCommand implements FrontControllerCommand {

    @Override
    public Page execute(HttpServletRequest request) {
        return new Page(RedirectEnum.FORWARD,"/WEB-INF/view/success.jsp");
    }
}
