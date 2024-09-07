package ru.roznov.servlets_2.servlets.keeper;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reject")
public class RejectServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandParameters commandParameters = new CommandParameters();
        commandParameters.addParameter("requestId", Integer.parseInt(req.getParameter("requestId")));
        CommandController.executeCommand(CommandName.REJECT_REQUEST, commandParameters);
        req.getRequestDispatcher("/handleRequests").forward(req, resp);
    }
}
