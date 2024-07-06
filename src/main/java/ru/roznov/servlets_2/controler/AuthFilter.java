package ru.roznov.servlets_2.controler;

import jakarta.servlet.annotation.WebFilter;
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
    public void init(FilterConfig filterConfig) throws ServletException {
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
        //Logged user.
        if (nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"))) {

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

    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final RoleEnum role)
            throws ServletException, IOException {


        if (role.equals(RoleEnum.ADMIN)) {
            //req.getRequestDispatcher("/addUser").forward(req, res);
            req.getRequestDispatcher("/WEB-INF/view/adduser.jsp").forward(req, res);

        } else {
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }


    @Override
    public void destroy() {
    }

}