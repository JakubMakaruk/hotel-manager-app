package com.company;

import com.company.models.User;

public final class UserSingleton {
    private User user;
    private final static UserSingleton instance = new UserSingleton();

    private UserSingleton() {}

    public static UserSingleton getInstance() {
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return this.user;
    }
}
