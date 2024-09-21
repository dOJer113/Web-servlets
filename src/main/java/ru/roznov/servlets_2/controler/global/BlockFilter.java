package ru.roznov.servlets_2.controler.global;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
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
        commandParameters.addParameter("login", request.getParameter("login"));
        CommandController.executeCommand(CommandName.IS_CLIENT_BLOCKED, commandParameters);
        final HttpServletRequest req = (HttpServletRequest) request;
        if (commandParameters.getParameter("block", Boolean.class)) {
            commandParameters.addParameter("role", RoleEnum.BLOCKED);
            commandParameters.addParameter("request", request);
            commandParameters.addParameter("response", response);
            CommandController.executeCommand(CommandName.MOVE_TO_MENU, commandParameters);
            req.getSession().setAttribute("role", RoleEnum.valueOf("BLOCKED"));
        } else {

        }
        chain.doFilter(request, response);
    }
}
