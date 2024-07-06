package ru.roznov.servlets_2.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentLogin = (String) session.getAttribute("login");
        String loginToDelete = request.getParameter("login");
/*
        if (!currentLogin.equals(loginToDelete)) {
            // Логика удаления пользователя из базы данных
            // UsersSearcher.deleteUser(loginToDelete);
        }*/

        response.sendRedirect("viewUsers.jsp");
    }
}
