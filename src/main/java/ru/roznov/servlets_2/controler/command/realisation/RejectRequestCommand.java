package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public class RejectRequestCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.STOREKEEPER) {
            CommandParameters commandParameters = new CommandParameters();
            commandParameters.addParameter("requestId", Integer.parseInt(request.getParameter("requestId")));
            CommandController.executeCommand(CommandName.REJECT_REQUEST, commandParameters);
            return "/WEB-INF/view/storekeeperHandleRequests.jsp";
        }
        return request.getContextPath()+"/controller?command=LOGIN";
    }
}
