package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class EntryRequestCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.DRIVER) {
            CommandParameters commandParameters = new CommandParameters();
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            request.getSession().setAttribute("storeId", storeId);
            commandParameters.addParameter("storeId", storeId);
            commandParameters.addParameter("driverId", Integer.parseInt(request.getSession().getAttribute("id").toString()));
            CommandController.executeCommand(CommandName.MAKE_ENTRY_REQUEST, commandParameters);
            return "/WEB-INF/view/driver.jsp";
        }
        return request.getContextPath()+"/controller?command=LOGIN";
    }
}
