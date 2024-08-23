package ru.roznov.servlets_2.controler;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class AppListener implements ServletContextListener {

    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer();
        CommandController.init();
        CommandController.executeCommand(CommandName.GET_VALUES_FROM_ORACLE_DB, new CommandParameters());
        sce.getServletContext().setAttribute("appListener", this);
        System.out.println("Application started.");
    }

    public void scheduleTask(TimerTask task, long delay) {
        timer.schedule(task, delay);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (timer != null) {
            timer.cancel();
        }
        CommandController.executeCommand(CommandName.MAKE_ALL_UN_ACTIVE, new CommandParameters());
        System.out.println("Application stopped.");
    }

}
