package ru.roznov.servlets_2.controler.global;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ClientActivityDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

@WebListener
public class AppListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CommandController.init();
        sce.getServletContext().setAttribute("appListener", this);
        System.out.println("Application started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ClientActivityDAO activityDAO = (ClientActivityDAO) sce.getServletContext().getAttribute("ActivityDAO");
        CommandParameters commandParameters = new CommandParameters();
        commandParameters.addParameter("ActivityDAO", activityDAO);
        CommandController.executeCommand(CommandName.MAKE_ALL_UN_ACTIVE, commandParameters);
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
