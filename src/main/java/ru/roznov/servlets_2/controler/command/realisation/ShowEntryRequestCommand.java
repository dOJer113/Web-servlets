package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;

import javax.servlet.http.HttpServletRequest;

public class ShowEntryRequestCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/view/enterStoreId.jsp";
    }
}
