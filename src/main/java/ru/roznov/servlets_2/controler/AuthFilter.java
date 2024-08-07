package ru.roznov.servlets_2.controler;

import jakarta.servlet.annotation.WebFilter;
import ru.roznov.servlets_2.model.ClientBlocker;
import ru.roznov.servlets_2.model.UsersSearcher;
import ru.roznov.servlets_2.objects.RoleEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)

            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");


        final HttpSession session = req.getSession();
        UsersSearcher.getValuesFromOracleDB();
        if (ClientBlocker.isClientBlocked(login)) {
            moveToMenu(req, res, RoleEnum.BLOCKED);
        } else if (AuthFilter.isUserAuthed(request)) {

            final RoleEnum role = (RoleEnum) session.getAttribute("role");

            moveToMenu(req, res, role);

        } else if (nonNull(login) && UsersSearcher.isExistsUser(login)) {

            final RoleEnum role = RoleEnum.valueOf(UsersSearcher.getRoleByLogin(login).toString());
            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);

            moveToMenu(req, res, role);

        } else {

            moveToMenu(req, res, RoleEnum.UNKNOWN);
        }
    }

    private static boolean isUserAuthed(ServletRequest request) {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpSession session = req.getSession();
        return nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"));
    }

    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final RoleEnum role)
            throws ServletException, IOException {


        if (role.equals(RoleEnum.ADMIN)) {
            req.getRequestDispatcher("/WEB-INF/view/adm.jsp").forward(req, res);
        } else if (role.equals(RoleEnum.MODERATOR)) {
            req.getRequestDispatcher("/WEB-INF/view/moder.jsp").forward(req, res);
        } else if (role.equals(RoleEnum.BLOCKED)) {
            req.getRequestDispatcher("/WEB-INF/view/block.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }


    @Override
    public void destroy() {
    }

}