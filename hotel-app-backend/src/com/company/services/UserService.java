package com.company.services;

import com.company.models.User;

import java.rmi.Remote;
import java.sql.SQLException;

public interface UserService extends Remote {
    User signIn(String username, String password) throws SQLException;
    boolean signUp(String firstname, String lastname, String username, String password) throws SQLException;
    boolean checkIfUserExists(String username) throws SQLException;
    boolean changePassword(String username, String password) throws SQLException;
}
