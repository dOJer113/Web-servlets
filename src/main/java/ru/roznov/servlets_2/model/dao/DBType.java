package ru.roznov.servlets_2.model.dao;



import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.oracledb.OracleDBDAOFactory;

import java.sql.SQLException;

public enum DBType {
	ORACLE {
		@Override
		public DAOFactory getDAOFactory() {
			DAOFactory oracleDBDAOFactory = null;
			try {
				oracleDBDAOFactory = OracleDBDAOFactory.getInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return oracleDBDAOFactory;
		}
	};

	public static DBType getTypeByName(String dbType) {
		try {
			return DBType.valueOf(dbType.toUpperCase());
		} catch (Exception e) {
			throw new DBTypeException();
		}
	}

	public abstract DAOFactory getDAOFactory();

}
