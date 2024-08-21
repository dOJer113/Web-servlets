package ru.roznov.servlets_2.controler.command;

import java.sql.SQLException;

public interface Command {
    void execute(CommandParameters commandParameters) throws SQLException;
}
