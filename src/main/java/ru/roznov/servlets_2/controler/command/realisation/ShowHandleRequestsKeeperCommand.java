package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.objects.requests.AbstractRequest;
import ru.roznov.servlets_2.objects.requests.RequestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowHandleRequestsKeeperCommand implements FrontControllerCommand {
    @Override
    public String execute(HttpServletRequest request) {
        int keeperId = Integer.parseInt(request.getSession().getAttribute("id").toString());
        List<AbstractRequest> requests = RequestController.getAllRequestsForKeeper(keeperId);
        request.setAttribute("requests", requests);
        return "/WEB-INF/view/storekeeperHandleRequests.jsp";
    }
}
