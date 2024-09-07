package ru.roznov.servlets_2.servlets.keeper;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.objects.requests.RequestType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandParameters commandParameters = new CommandParameters();
        commandParameters.addParameter("requestId", Integer.parseInt(req.getParameter("requestId")));
        RequestType requestType = RequestType.valueOf(req.getParameter("requestType"));
        if (requestType.equals(RequestType.ENTRY)) {
            CommandController.executeCommand(CommandName.CONFIRM_ENTRY_REQUEST, commandParameters);
        } else {
            commandParameters.addParameter("keeperId", req.getSession().getAttribute("id"));
            CommandController.executeCommand(CommandName.CONFIRM_HANDLING_REQUEST, commandParameters);
        }
        req.getRequestDispatcher("/handleRequests").forward(req, resp);
    }
}
