package com.company.services;

import com.company.models.Customer;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    Dao<Customer, Integer> customerDao;

    public CustomerServiceImpl(Dao<Customer, Integer> customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    public boolean createCustomer(String firstName, String surname, String email, String address, String city, String postCode, String telephone) throws SQLException {
        Customer customer = new Customer(firstName, surname, email, address, city, postCode, telephone);
        return customerDao.create(customer) == 1;
    }

    @Override
    public List<Customer> getCustomersByFirstName(String firstName) throws SQLException {
        return customerDao.queryForEq("firstName", firstName);
    }

    @Override
    public List<Customer> getCustomersByName(String firstName, String surname) throws SQLException {
        List<Customer> result = new ArrayList<>();
        List<Customer> customerFirstName = customerDao.queryForEq("firstName", firstName);
        for (Customer c : customerFirstName) {
            if (c.getFirstName().equals(firstName) && c.getSurname().equals(surname)) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public List<Customer> getCustomers() throws SQLException {
        return customerDao.queryForAll();
    }

    @Override
    public boolean removeCustomerById(int id) throws SQLException {
        Customer customer = customerDao.queryForEq("id", id).get(0);
        return customerDao.delete(customer) == 1;
    }
}
