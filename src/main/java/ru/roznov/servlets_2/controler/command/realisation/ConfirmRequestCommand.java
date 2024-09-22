package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.objects.requests.RequestType;

import javax.servlet.http.HttpServletRequest;

public class ConfirmRequestCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        CommandParameters commandParameters = new CommandParameters();
        commandParameters.addParameter("requestId", Integer.parseInt(request.getParameter("requestId")));
        RequestType requestType = RequestType.valueOf(request.getParameter("requestType"));
        if (requestType.equals(RequestType.ENTRY)) {
            CommandController.executeCommand(CommandName.CONFIRM_ENTRY_REQUEST, commandParameters);
        } else {
            commandParameters.addParameter("keeperId", request.getSession().getAttribute("id"));
            CommandController.executeCommand(CommandName.CONFIRM_HANDLING_REQUEST, commandParameters);
        }
        return "/WEB-INF/view/storekeeperHandleRequests.jsp";
    }
}