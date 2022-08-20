package com.company.services;

import com.company.models.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    Dao<User, Integer> userDao;

    public UserServiceImpl(Dao<User, Integer> userDao) {
        this.userDao = userDao;
    }

    @Override
    public User signIn(String username, String password) throws SQLException {
        List<User> users = userDao.queryForEq("username", username);
        if (users.size() > 0 && users.get(0).getPassword().equals(password)) {
            System.out.println("Logged!");
            return users.get(0);
        } else {
            System.out.println("Wrong username or password!");
            return null;
        }
    }

    @Override
    public boolean signUp(String firstname, String lastname, String username, String password) throws SQLException {
        User user = new User(firstname, lastname, username, password);
        userDao.create(user);
        System.out.println("Successfully created user!");
        return true;
    }

    @Override
    public boolean checkIfUserExists(String username) throws SQLException {
        List<User> users = userDao.queryForEq("username", username);
        return users.size() > 0;
    }

    @Override
    public boolean changePassword(String username, String password) throws SQLException {
        List<User> users = userDao.queryForEq("username", username);
        if (users.size() > 0) {
            User user = users.get(0);
            System.out.println(user.toString());
            user.setPassword(password);
            userDao.update(user);
            System.out.println(user.toString());
            return true;
        }
        return false;
    }
}
