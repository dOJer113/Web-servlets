package ru.roznov.servlets_2.controler.global;

import ru.roznov.servlets_2.controler.command.FrontCommandNames;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Page page;
        String commandParam = request.getParameter("command");
        if (commandParam != null && !commandParam.isEmpty()) {
            FrontCommandNames commandName = FrontCommandNames.valueOf(commandParam);
            FrontControllerCommand command = commandName.getCommand();
            page = command.execute(request);
            if (page != null) {
                try {
                    if (page.getRedirectEnum() == RedirectEnum.FORWARD) {
                        request.getRequestDispatcher(page.getUrl()).forward(request, response);
                    } else {
                        response.sendRedirect("/controller" + page.getUrl());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }
    }
}
