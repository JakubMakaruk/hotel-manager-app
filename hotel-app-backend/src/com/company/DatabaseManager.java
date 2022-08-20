package com.company;

import com.company.models.Customer;
import com.company.models.Reservation;
import com.company.models.Room;
import com.company.models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_PATH = "D:/DANE/Projekty/hotel-app-java/hotel-app-backend/hotel.db";
    private static final String JDBC_DATABASE_PATH = "jdbc:sqlite:/" + DATABASE_PATH;
    private ConnectionSource connectionSource;

    private Dao<User, Integer> userDao;
    private Dao<Room, Integer> roomDao;
    private Dao<Customer, Integer> customerDao;
    private Dao<Reservation, Integer> reservationDao;


    public DatabaseManager() {
        try {
            File file = new File(DATABASE_PATH);

            if (!file.exists()) {
                throw new Exception("Database not exists");
            }

            connectionSource = new JdbcConnectionSource(JDBC_DATABASE_PATH);
            System.out.println("Successfully connected to database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() throws SQLException {
//        TableUtils.createTable(connectionSource, Room.class);
//        TableUtils.createTable(connectionSource, Room.class);
//        TableUtils.createTable(connectionSource, Customer.class);
//        TableUtils.createTable(connectionSource, Reservation.class);
//        TableUtils.createTable(connectionSource, Room.class);
//        TableUtils.createTable(connectionSource, User.class);
//        User user = new User("Jan", "Kowalski", "jkowalski", "jkowalski");
//        userDao.create(user);
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        userDao = DaoManager.createDao(connectionSource, User.class);
        return userDao;
    }
    public Dao<Room, Integer> getRoomDao() throws SQLException {
        roomDao = DaoManager.createDao(connectionSource, Room.class);
        return roomDao;
    }
    public Dao<Customer, Integer> getCustomerDao() throws SQLException {
        customerDao = DaoManager.createDao(connectionSource, Customer.class);
        return customerDao;
    }
    public Dao<Reservation, Integer> getReservationDao() throws SQLException {
        reservationDao = DaoManager.createDao(connectionSource, Reservation.class);
        return reservationDao;
    }
}
