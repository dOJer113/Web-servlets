package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;

import javax.servlet.http.HttpServletRequest;

public class RejectRequestCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        CommandParameters commandParameters = new CommandParameters();
        commandParameters.addParameter("requestId", Integer.parseInt(request.getParameter("requestId")));
        CommandController.executeCommand(CommandName.REJECT_REQUEST, commandParameters);
        return "/WEB-INF/view/storekeeperHandleRequests.jsp";
    }
}
