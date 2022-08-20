package com.company.services;

import com.company.models.Customer;
import com.company.models.Reservation;
import com.company.models.Room;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    Dao<Reservation, Integer> reservationDao;
    Dao<Customer, Integer> customerDao;
    CustomerService customerService;

    public ReservationServiceImpl(Dao<Reservation, Integer> reservationDao, Dao<Customer, Integer> customerDao) {
        this.reservationDao = reservationDao;
        this.customerDao = customerDao;
        customerService = new CustomerServiceImpl(this.customerDao);
    }

    @Override
    public boolean createReservation(Customer customer, Room room, LocalDate startTime, LocalDate endTime) throws SQLException {
        Reservation reservation = new Reservation(customer, room, startTime, endTime);
        return reservationDao.create(reservation) == 1;
    }

    @Override
    public List<Reservation> getReservations() throws SQLException {
        return reservationDao.queryForAll();
    }

    @Override
    public List<Reservation> getReservationByFirstName(String firstName) throws SQLException {
        List<Reservation> result = new ArrayList<>();
        List<Customer> customers = customerService.getCustomersByFirstName(firstName);
        List<Reservation> reservations = reservationDao.queryForAll();
        for (Reservation reservation : reservations) {
            if (customers.stream().anyMatch(customer -> customer.getFirstName().equals(reservation.getCustomer().getFirstName()))) {
                result.add(reservation);
            }
        }
        return result;
    }

    @Override
    public List<Reservation> getReservationByName(String firstName, String surname) throws SQLException {
        List<Reservation> result = new ArrayList<>();
        List<Customer> customers = customerService.getCustomersByName(firstName, surname);
        List<Reservation> reservations = reservationDao.queryForAll();
        for (Reservation reservation : reservations) {
            if (customers.stream().anyMatch(customer -> (customer.getFirstName().equals(reservation.getCustomer().getFirstName()))
                                                        && (customer.getSurname().equals(reservation.getCustomer().getSurname())))) {
                result.add(reservation);
            }
        }
        return result;
    }

    @Override
    public boolean removeReservationById(int id) throws SQLException {
        Reservation reservation = reservationDao.queryForEq("id", id).get(0);
        return reservationDao.delete(reservation) == 1;
    }
}
