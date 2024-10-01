package ru.roznov.servlets_2.controler.command.realisation;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.controler.command.FrontControllerCommand;
import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.CarDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ProductDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.StorageDAO;
import ru.roznov.servlets_2.objects.clients.RoleEnum;
import ru.roznov.servlets_2.objects.requests.AbstractRequest;
import ru.roznov.servlets_2.objects.requests.RequestController;
import ru.roznov.servlets_2.objects.requests.RequestType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ConfirmRequestCommand implements FrontControllerCommand {
    @Override
    public Page execute(HttpServletRequest request) {
        RoleEnum role = RoleEnum.valueOf(request.getSession().getAttribute("role").toString());
        CarDAO carDAO = (CarDAO) request.getServletContext().getAttribute("CarDAO");
        StorageDAO storageDAO = (StorageDAO) request.getServletContext().getAttribute("StoreDAO");
        if (role == RoleEnum.STOREKEEPER) {
            CommandParameters commandParameters = new CommandParameters();
            ProductDAO productDAO = (ProductDAO) request.getServletContext().getAttribute("ProductDAO");
            commandParameters.addParameter("ProductDAO", productDAO);
            commandParameters.addParameter("requestId", Integer.parseInt(request.getParameter("requestId")));
            RequestType requestType = RequestType.valueOf(request.getParameter("requestType"));
            commandParameters.addParameter("CarDAO", carDAO);
            commandParameters.addParameter("StoreDAO", storageDAO);
            if (requestType.equals(RequestType.ENTRY)) {
                CommandController.executeCommand(CommandName.CONFIRM_ENTRY_REQUEST, commandParameters);
            } else {
                commandParameters.addParameter("keeperId", request.getSession().getAttribute("id"));
                CommandController.executeCommand(CommandName.CONFIRM_HANDLING_REQUEST, commandParameters);
            }
            int keeperId = Integer.parseInt(request.getSession().getAttribute("id").toString());
            List<AbstractRequest> requests = RequestController.getAllRequestsForKeeper(keeperId, storageDAO);
            request.setAttribute("requests", requests);
            return new Page(RedirectEnum.FORWARD, "/WEB-INF/view/storekeeperHandleRequests.jsp");
        }
        return new Page(RedirectEnum.FORWARD, request.getContextPath() + "/controller?command=LOGIN");
    }
}
