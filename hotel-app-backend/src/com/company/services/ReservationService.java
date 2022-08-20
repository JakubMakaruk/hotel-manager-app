package com.company.services;

import com.company.models.Customer;
import com.company.models.Reservation;
import com.company.models.Room;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    boolean createReservation(Customer customer, Room room, LocalDate startTime, LocalDate endTime) throws SQLException;
    List<Reservation> getReservations() throws SQLException;
    List<Reservation> getReservationByFirstName(String firstName) throws SQLException;
    List<Reservation> getReservationByName(String firstName, String surname) throws SQLException;
    boolean removeReservationById(int id) throws SQLException;
}
