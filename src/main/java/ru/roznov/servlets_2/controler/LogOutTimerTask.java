package ru.roznov.servlets_2.controler;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.user.UsersSearcher;

import java.util.TimerTask;

public class LogOutTimerTask extends TimerTask {
    private String login;

    public LogOutTimerTask(String login) {
        this.login = login;
    }

    @Override
    public void run() {
        CommandParameters commandParameters = new CommandParameters();
        commandParameters.addParameter("id", UsersSearcher.getIdByLogin(this.login));
        CommandController.executeCommand(CommandName.MAKE_CLIENT_UNACTIVE, commandParameters);
    }
}
