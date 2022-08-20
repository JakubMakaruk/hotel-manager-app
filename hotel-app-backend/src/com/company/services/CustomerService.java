package com.company.services;

import com.company.models.Customer;
import com.j256.ormlite.field.DatabaseField;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
    boolean createCustomer(String firstName, String surname, String email, String address, String city, String postCode, String telephone) throws SQLException;
    List<Customer> getCustomersByFirstName(String firstName) throws SQLException;
    List<Customer> getCustomersByName(String firstName, String surname) throws SQLException;
    List<Customer> getCustomers() throws SQLException;
    boolean removeCustomerById(int id) throws SQLException;
}
