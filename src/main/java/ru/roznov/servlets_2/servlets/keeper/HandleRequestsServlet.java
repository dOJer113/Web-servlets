/*
package ru.roznov.servlets_2.servlets.keeper;

import ru.roznov.servlets_2.objects.requests.AbstractRequest;
import ru.roznov.servlets_2.objects.requests.RequestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/handleRequests")
public class HandleRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int keeperId = Integer.parseInt(req.getSession().getAttribute("id").toString());
        List<AbstractRequest> requests = RequestController.getAllRequestsForKeeper(keeperId);
        req.setAttribute("requests", requests);
        req.getRequestDispatcher("/WEB-INF/view/storekeeperHandleRequests.jsp").forward(req, resp);
    }

*/
/*    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }*//*

}
*/
