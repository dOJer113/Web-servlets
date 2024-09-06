package ru.roznov.servlets_2.servlets.admin;

import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/searchUser")
public class SearchUserToChangeServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        Client client = new Client();
        try {
            if (UsersSearcher.isExistsUser(login)) {
                req.getSession().setAttribute("oldLogin",login);
                client = UsersSearcher.getClientByLogin(login);

            } else {
                System.err.println("No such user in db");
            }
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
        req.setAttribute("client", client);
        req.getRequestDispatcher("/WEB-INF/view/fundedClient.jsp").forward(req, resp);
    }
}
