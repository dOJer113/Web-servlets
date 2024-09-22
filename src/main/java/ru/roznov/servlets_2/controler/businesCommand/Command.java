package ru.roznov.servlets_2.controler.businesCommand;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public interface Command {
    void execute(CommandParameters commandParameters) throws SQLException, ServletException, IOException;
}
