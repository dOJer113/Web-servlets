package ru.roznov.servlets_2.model.dao.DAOinterfeices;

import ru.roznov.servlets_2.objects.clients.UserWithActivity;

import java.sql.SQLException;
import java.util.List;

public interface ClientActivityDAO {
    void insertNewClient(int id) throws SQLException;

    void deleteClient(int id);

    void updateClient(int id, int activity);

    void makeAllUnActive();

    List<UserWithActivity> getUsersWithActivity();

}
