package ru.roznov.servlets_2.controler.command;

import javax.servlet.http.HttpServletRequest;

public interface FrontControllerCommand {
    String execute(HttpServletRequest request);
}
