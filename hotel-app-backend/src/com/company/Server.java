package com.company;

import com.company.services.Facade;
import com.company.services.FacadeImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws SQLException, RemoteException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.create();
        Facade facadeService = new FacadeImpl(databaseManager);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("HotelService", facadeService);
    }
}
