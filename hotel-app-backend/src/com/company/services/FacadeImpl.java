package com.company.services;

import com.company.DatabaseManager;
import com.company.models.Customer;
import com.company.models.Reservation;
import com.company.models.Room;
import com.company.models.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FacadeImpl extends UnicastRemoteObject implements Facade {
    static DatabaseManager databaseManager;

    UserService userService;
    RoomService roomService;
    CustomerService customerService;
    ReservationService reservationService;

    public FacadeImpl(DatabaseManager databaseManager) throws SQLException, RemoteException {
        super();
        this.databaseManager = databaseManager;
        userService = new UserServiceImpl(databaseManager.getUserDao());
        roomService = new RoomServiceImpl(databaseManager.getRoomDao());
        customerService = new CustomerServiceImpl(databaseManager.getCustomerDao());
        reservationService = new ReservationServiceImpl(databaseManager.getReservationDao(), databaseManager.getCustomerDao());
    }

    @Override
    public User signIn(String username, String password) throws SQLException, RemoteException {
        return userService.signIn(username, password);
    }

    @Override
    public boolean signUp(String firstname, String lastname, String username, String password) throws SQLException, RemoteException {
        return userService.signUp(firstname, lastname, username, password);
    }

    @Override
    public boolean checkIfUserExists(String username) throws SQLException, RemoteException {
        return userService.checkIfUserExists(username);
    }

    @Override
    public boolean changePassword(String username, String password) throws SQLException, RemoteException {
        return userService.changePassword(username, password);
    }

    @Override
    public boolean createRoom(String roomNumber, String roomName, String numberOfBeds) throws SQLException, RemoteException {
        return roomService.createRoom(roomNumber, roomName, numberOfBeds);
    }

    @Override
    public List<Room> getAllRooms() throws SQLException, RemoteException {
        return roomService.getAllRooms();
    }

    @Override
    public Room getRoomByRoomNumber(Integer roomNumber) throws SQLException, RemoteException {
        return roomService.getRoomByRoomNumber(roomNumber);
    }

    @Override
    public List<Room> getAvailableRooms() throws SQLException, RemoteException {
        return roomService.getAvailableRooms();
    }

    @Override
    public boolean updateRoom(Room room) throws SQLException, RemoteException {
        return roomService.updateRoom(room);
    }


    @Override
    public boolean createCustomer(String firstName, String surname, String email, String address, String city, String postCode, String telephone) throws SQLException, RemoteException {
        return customerService.createCustomer(firstName, surname, email, address, city, postCode, telephone);
    }

    @Override
    public List<Customer> getCustomersByFirstName(String firstName) throws SQLException, RemoteException {
        return customerService.getCustomersByFirstName(firstName);
    }

    @Override
    public List<Customer> getCustomersByName(String firstName, String surname) throws SQLException, RemoteException {
        return customerService.getCustomersByName(firstName, surname);
    }

    @Override
    public List<Customer> getCustomers() throws SQLException, RemoteException {
        return customerService.getCustomers();
    }

    @Override
    public boolean removeCustomerById(int id) throws SQLException, RemoteException {
        return customerService.removeCustomerById(id);
    }


    @Override
    public boolean createReservation(Customer customer, Room room, LocalDate startTime, LocalDate endTime) throws SQLException, RemoteException {
        return reservationService.createReservation(customer, room, startTime, endTime);
    }

    @Override
    public List<Reservation> getReservations() throws SQLException, RemoteException {
        return reservationService.getReservations();
    }

    @Override
    public List<Reservation> getReservationByFirstName(String firstName) throws SQLException, RemoteException {
        return reservationService.getReservationByFirstName(firstName);
    }

    @Override
    public List<Reservation> getReservationByName(String firstName, String surname) throws SQLException, RemoteException {
        return reservationService.getReservationByName(firstName, surname);
    }

    @Override
    public boolean removeReservationById(int id) throws SQLException, RemoteException {
        return reservationService.removeReservationById(id);
    }
}
