package ru.roznov.servlets_2.model.dao.DAOinterfeices;

import ru.roznov.servlets_2.model.dao.DynamicResult;

import java.sql.SQLException;

public interface ClientActivityDAO {
    DynamicResult getClients() throws SQLException;

    void insertNewClient(int id) throws SQLException;

    void deleteClient(int id) throws SQLException;

    void updateClient(int id, int activity) throws SQLException;

    void makeAllUnActive() throws SQLException;

    DynamicResult getUsersWithActivity() throws SQLException;

}
