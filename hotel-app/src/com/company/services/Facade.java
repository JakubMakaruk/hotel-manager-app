package com.company.services;

import com.company.models.Customer;
import com.company.models.Reservation;
import com.company.models.Room;
import com.company.models.User;

import java.io.NotSerializableException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface Facade extends Remote {
    User signIn(String username, String password) throws SQLException, RemoteException, NotSerializableException;
    boolean signUp(String firstname, String lastname, String username, String password) throws SQLException, RemoteException;
    boolean checkIfUserExists(String username) throws SQLException, RemoteException;
    boolean changePassword(String username, String password) throws SQLException, RemoteException;

    boolean createRoom(String roomNumber, String roomName, String numberOfBeds) throws SQLException;
    List<Room> getAllRooms() throws SQLException, RemoteException;
    Room getRoomByRoomNumber(Integer roomNumber) throws SQLException, RemoteException;
    List<Room> getAvailableRooms() throws SQLException, RemoteException;
    boolean updateRoom(Room room) throws SQLException, RemoteException;

    boolean createCustomer(String firstName, String surname, String email, String address, String city, String postCode, String telephone) throws SQLException, RemoteException;
    List<Customer> getCustomersByName(String firstName, String surname) throws SQLException, RemoteException;
    List<Customer> getCustomersByFirstName(String firstName) throws SQLException, RemoteException;
    List<Customer> getCustomers() throws SQLException, RemoteException;
    boolean removeCustomerById(int id) throws SQLException, RemoteException;

    boolean createReservation(Customer customer, Room room, LocalDate startTime, LocalDate endTime) throws SQLException, RemoteException;
    List<Reservation> getReservations() throws SQLException, RemoteException;
    List<Reservation> getReservationByFirstName(String firstName) throws SQLException, RemoteException;
    List<Reservation> getReservationByName(String firstName, String surname) throws SQLException, RemoteException;
    boolean removeReservationById(int id) throws SQLException, RemoteException;
}
