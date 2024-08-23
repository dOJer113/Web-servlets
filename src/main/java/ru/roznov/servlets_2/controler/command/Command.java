package ru.roznov.servlets_2.controler.command;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public interface Command {
    void execute(CommandParameters commandParameters) throws SQLException, ServletException, IOException;
}
