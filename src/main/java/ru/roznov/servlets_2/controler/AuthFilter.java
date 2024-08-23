package ru.roznov.servlets_2.controler;

import jakarta.servlet.annotation.WebFilter;
import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.block.ClientBlocker;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.RoleEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) {

        final HttpServletRequest req = (HttpServletRequest) request;
        CommandParameters moveParameters = new CommandParameters();
        moveParameters.addParameter("response", response);
        moveParameters.addParameter("request", req);
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final HttpSession session = req.getSession();

        if (ClientBlocker.isClientBlocked(login)) {
            moveParameters.addParameter("role", RoleEnum.BLOCKED);
            CommandController.executeCommand(CommandName.MOVE_TO_MENU, moveParameters);
            req.getSession().setAttribute("role", RoleEnum.valueOf("BLOCKED"));
        } else if (AuthFilter.isUserAuthed(request)) {
            final RoleEnum role = (RoleEnum) session.getAttribute("role");
            moveParameters.addParameter("role", role);
            CommandController.executeCommand(CommandName.MOVE_TO_MENU, moveParameters);
        } else if (nonNull(login) && UsersSearcher.isExistsUser(login)) {
            CommandParameters authorizationParameters = new CommandParameters();
            authorizationParameters.addParameter("response", response);
            authorizationParameters.addParameter("request", req);
            authorizationParameters.addParameter("login", login);
            authorizationParameters.addParameter("password", password);
            CommandController.executeCommand(CommandName.AUTHORIZE_CLIENT, authorizationParameters);
        } else {
            moveParameters.addParameter("role", RoleEnum.UNKNOWN);
            CommandController.executeCommand(CommandName.MOVE_TO_MENU, moveParameters);
        }
    }

    private static boolean isUserAuthed(ServletRequest request) {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpSession session = req.getSession();
        return nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"));
    }


}