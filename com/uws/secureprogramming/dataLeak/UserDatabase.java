package com.uws.secureprogramming.dataLeak;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDatabase {

    private static final Logger logger =
            Logger.getLogger(UserDatabase.class.getName());

    ArrayList<User> database;

    public UserDatabase() {
        database = new ArrayList<>();
    }

    public void addUser(User user) {
        if (user != null && !database.contains(user)) {
            database.add(user);
        }
    }

    public boolean userExists(User user) {
        return database.contains(user);
    }


    public boolean AcceptUserLogin(String loginValue, String password) {
        if (loginValue == null || password == null) {
            logger.log(Level.WARNING, "AcceptUserLogin called with null argument(s)");
            return false;
        }
        for (User user : database) {
            boolean nameMatch  = loginValue.equals(user.getName());
            boolean emailMatch = loginValue.equals(user.getEmail());
            if ((nameMatch || emailMatch) && password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
