package ru.roznov.servlets_2.servlets;


import ru.roznov.servlets_2.model.UsersSearcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hgjohvhhi")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UsersSearcher.getValuesFromOracleDB();
        getServletContext().getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req,resp);
    }
}
