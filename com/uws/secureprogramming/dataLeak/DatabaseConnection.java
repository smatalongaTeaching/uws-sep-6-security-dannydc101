package com.uws.secureprogramming.dataLeak;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseConnection {


    private static final Logger logger =
            Logger.getLogger(DatabaseConnection.class.getName());


    private static final String GENERIC_AUTH_ERROR =
            "Authentication failed. Please check your credentials and try again.";

    private final UserDatabase userDatabase;

    public DatabaseConnection(UserDatabase users) {
        userDatabase = users;

        userDatabase.addUser(new User("admin", "admin@disney.com", "secr3tP@ss"));
    }


    public void connectToDatabase(String username, String password) {
        try {

            if (username == null || password == null) {

                throw new IllegalArgumentException(
                        "connectToDatabase called with null credential(s): "
                        + "username=" + username + ", password=[REDACTED]");
            }


            if ("admin".equals(username)) {
                if (!"secr3tP@ss".equals(password)) {
                    throw new IllegalArgumentException(
                            "Admin login attempt with incorrect password from username=admin");
                }
                System.out.println("Connected successfully to database as " + username);
                return;
            }


            if (!this.userDatabase.AcceptUserLogin(username, password)) {
                throw new IllegalArgumentException(
                        "Login failed for username=" + username + " (not found or wrong password)");
            }

            System.out.println("Connected successfully to database as " + username);

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Database connection error", e);


            System.out.println(GENERIC_AUTH_ERROR);


        }
    }
}
