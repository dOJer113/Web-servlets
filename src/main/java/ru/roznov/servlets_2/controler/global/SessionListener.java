package ru.roznov.servlets_2.controler.global;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        CommandParameters commandParameters = new CommandParameters();
        commandParameters.addParameter("id", Integer.parseInt(se.getSession().getAttribute("id").toString()));
        CommandController.executeCommand(CommandName.MAKE_CLIENT_UNACTIVE, commandParameters);
    }
}
