package ru.roznov.servlets_2.model.dao.DAOinterfeices;


import ru.roznov.servlets_2.model.dao.DBType;

public abstract class DAOFactory {
    public static DAOFactory getInstance(DBType dbType) {
        return dbType.getDAOFactory();
    }

    public abstract UsersDAO getUsersDAO();
    public abstract ClientActivityDAO getClientActivityDAO();

}
