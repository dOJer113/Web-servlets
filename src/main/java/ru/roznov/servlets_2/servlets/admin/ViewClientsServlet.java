package ru.roznov.servlets_2.servlets.admin;


import ru.roznov.servlets_2.model.client.ClientActivityManager;
import ru.roznov.servlets_2.model.client.ClientActivitySearcher;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.UserWithActivity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/clients")
public class ViewClientsServlet extends HttpServlet {
    @Override
    public void init() {
        UsersSearcher.getValuesFromOracleDB();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //List<ClientWithActivity> clients = UsersSearcher.getClientsListByDynamicResult();
        List<UserWithActivity> users = ClientActivitySearcher.getUserWithActivity();
        req.setAttribute("clients", users);
        req.getRequestDispatcher("/WEB-INF/view/viewClients.jsp").forward(req, resp);
    }
}
