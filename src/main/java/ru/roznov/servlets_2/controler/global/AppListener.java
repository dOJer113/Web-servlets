package ru.roznov.servlets_2.controler.global;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class AppListener implements ServletContextListener {

    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer();
        CommandController.init();
        CommandParameters commandParameters = new CommandParameters();
        CommandController.executeCommand(CommandName.GET_USERS_FROM_ORACLE_DB, commandParameters);
        CommandController.executeCommand(CommandName.GET_PRODUCTS, commandParameters);
        CommandController.executeCommand(CommandName.GET_STORAGES_STOREKEEPERS, commandParameters);
        CommandController.executeCommand(CommandName.GET_CARS, commandParameters);
        CommandController.executeCommand(CommandName.GET_STORAGES, commandParameters);
        CommandController.executeCommand(CommandName.GET_CARS_DRIVERS, commandParameters);
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
        try {
            while (DriverManager.getDrivers().hasMoreElements()) {
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Iterator<CommandName> iterator = CommandController.getCommandLog().iterator();
        int i = 1;
        System.out.println("Used command log:");
        while (iterator.hasNext()) {
            System.out.println(i + ") " + iterator.next());
            i++;
        }
        System.out.println("Application stopped.");
    }

}
