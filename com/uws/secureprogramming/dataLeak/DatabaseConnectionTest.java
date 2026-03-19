package com.uws.secureprogramming.dataLeak;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {

    private UserDatabase userDatabase;
    private DatabaseConnection dbConnection;

    @BeforeEach
    void setUp() {
        userDatabase = new UserDatabase();
        // Add example users (Disney characters)
        userDatabase.addUser(new User("mickey", "mm@disney.com",	 "mouse123"));
        userDatabase.addUser(new User("donald", "dd@disney.com", "duck456"));
        userDatabase.addUser(new User("goofy", "goof@disney.com", "good123"));
        userDatabase.addUser(new User("minnie", "mm01@disney.com", "minnie321"));
        dbConnection = new DatabaseConnection(userDatabase);
    }

    
    @Test
    void testConnectWithNullUsername() {
        try {
            dbConnection.connectToDatabase(null, "password");
        } catch (Exception e) {
            
            e.printStackTrace();
            fail();
        }
        
    }

    @Test
    void testConnectWithNullPassword() {
        try {
            dbConnection.connectToDatabase("mickey", null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testUserNotFoundOrIncorrectPassword() {
        try {
            dbConnection.connectToDatabase("pluto", "dog123");
            dbConnection.connectToDatabase("donald", "wrongpass");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testSuccessfulConnection() {
        try {
            dbConnection.connectToDatabase("goofy", "goof789");
            dbConnection.connectToDatabase("minnie", "minnie321");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testAdminConnectionWithIncorrectPassword() {
        try {
            dbConnection.connectToDatabase("admin", "wrongpass");
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }

    @Test
    void testAdminConnectionWithCorrectPassword() {
        try {
            dbConnection.connectToDatabase("admin", "secr3tP@ss");
            System.out.println("Admin connected successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

}