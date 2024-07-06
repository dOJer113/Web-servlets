package ru.roznov.servlets_2.tests;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //переопределение метода для get запросов
        //из объекта request берётся вся необходимая информация о запросе
        //в объект response записывается ответ и заголовки
        HttpSession session = request.getSession(); //добавление работы с сессией
        Integer visitCounter = (Integer) session.getAttribute("visitCounter");
        if (visitCounter == null) {
            visitCounter = 1;
        } else {
            visitCounter++;
        }
        session.setAttribute("visitCounter", visitCounter);
        String username = request.getParameter("username");
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        if (username == null) {
            printWriter.write("Hello, Anonymous" + "<br>");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
        printWriter.write("Page was visited " + visitCounter + " times.");
        printWriter.close();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //переопределение метода для get запросов
        //из объекта request берётся вся необходимая информация о запросе
        //в объект response записывается ответ и заголовки
        HttpSession session = request.getSession(); //добавление работы с сессией
        Integer visitCounter = (Integer) session.getAttribute("visitCounter");
        if (visitCounter == null) {
            visitCounter = 1;
        } else {
            visitCounter++;
        }
        session.setAttribute("visitCounter", visitCounter);
        String username = request.getParameter("username");
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        if (username == null) {
            printWriter.write("Hello, Anonymous" + "<br>");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
        printWriter.write("Page was visited " + visitCounter + " times.");
        printWriter.close();
    }
}