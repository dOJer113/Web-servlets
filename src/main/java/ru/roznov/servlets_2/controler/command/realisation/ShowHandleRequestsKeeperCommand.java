package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.objects.clients.RoleEnum;
import ru.roznov.servlets_2.objects.requests.AbstractRequest;
import ru.roznov.servlets_2.objects.requests.RequestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowHandleRequestsKeeperCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        if (role == RoleEnum.STOREKEEPER) {
            int keeperId = Integer.parseInt(request.getSession().getAttribute("id").toString());
            List<AbstractRequest> requests = RequestController.getAllRequestsForKeeper(keeperId);
            request.setAttribute("requests", requests);
            return new Page(RedirectEnum.FORWARD,"/WEB-INF/view/storekeeperHandleRequests.jsp");
        }
        return new Page(RedirectEnum.FORWARD,request.getContextPath()+"/controller?command=LOGIN");
    }
}
