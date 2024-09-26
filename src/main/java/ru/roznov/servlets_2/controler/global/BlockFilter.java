package ru.roznov.servlets_2.controler.global;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BlockFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CommandParameters commandParameters = new CommandParameters();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String login = httpRequest.getSession().getAttribute("login").toString();
        commandParameters.addParameter("login", login);
        CommandController.executeCommand(CommandName.IS_CLIENT_BLOCKED, commandParameters);
        if (commandParameters.getParameter("block", Boolean.class)) {
            commandParameters.addParameter("role", RoleEnum.BLOCKED);
            commandParameters.addParameter("request", request);
            commandParameters.addParameter("response", response);
            CommandController.executeCommand(CommandName.MOVE_TO_MENU, commandParameters);
            httpRequest.getSession().setAttribute("role", RoleEnum.valueOf("BLOCKED"));
        }
        chain.doFilter(request, response);
    }
}
