package com.uws.secureprogramming.dataLeak;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DatabaseConnectionTest {

    private UserDatabase userDatabase;
    private DatabaseConnection dbConnection;

    @BeforeEach
    void setUp() {
        userDatabase = new UserDatabase();
        // Regular users (Disney characters)
        userDatabase.addUser(new User("mickey", "mm@disney.com",   "mouse123"));
        userDatabase.addUser(new User("donald", "dd@disney.com",   "duck456"));
        userDatabase.addUser(new User("goofy",  "goof@disney.com", "good123"));
        userDatabase.addUser(new User("minnie", "mm01@disney.com", "minnie321"));

        dbConnection = new DatabaseConnection(userDatabase);
    }


    @Test
    void testConnectWithNullUsername() {

        assertDoesNotThrow(() ->
                dbConnection.connectToDatabase(null, "password"));
    }

    @Test
    void testConnectWithNullPassword() {

        assertDoesNotThrow(() ->
                dbConnection.connectToDatabase("mickey", null));
    }

    @Test
    void testUserNotFoundOrIncorrectPassword() {

        assertDoesNotThrow(() -> {
            dbConnection.connectToDatabase("pluto",  "dog123");
            dbConnection.connectToDatabase("donald", "wrongpass");
        });
    }

    @Test
    void testSuccessfulConnection_byUsername() {

        assertDoesNotThrow(() -> {
            dbConnection.connectToDatabase("goofy",  "good123");
            dbConnection.connectToDatabase("minnie", "minnie321");
        });
    }

    @Test
    void testSuccessfulConnection_byEmail() {

        assertDoesNotThrow(() -> {
            dbConnection.connectToDatabase("mm@disney.com",   "mouse123");
            dbConnection.connectToDatabase("dd@disney.com",   "duck456");
        });
    }

    @Test
    void testAdminConnectionWithIncorrectPassword() {

        assertDoesNotThrow(() ->
                dbConnection.connectToDatabase("admin", "wrongpass"));
    }

    @Test
    void testAdminConnectionWithCorrectPassword() {

        assertDoesNotThrow(() ->
                dbConnection.connectToDatabase("admin", "secr3tP@ss"));
    }

    /** Attach a mock Handler to the named logger and return it. */
    private Handler attachMockHandler(String loggerName) {
        Handler mockHandler = mock(Handler.class);
        // Allow all levels so the mock receives every record
        when(mockHandler.isLoggable(any())).thenReturn(true);

        Logger targetLogger = Logger.getLogger(loggerName);
        targetLogger.addHandler(mockHandler);
        targetLogger.setLevel(Level.ALL);
        return mockHandler;
    }

    /** Remove the mock handler after the assertion to avoid polluting other tests. */
    private void detachHandler(String loggerName, Handler handler) {
        Logger.getLogger(loggerName).removeHandler(handler);
    }

    @Test
    void testLogger_nullUsername_logsSevere() {
        String loggerName = DatabaseConnection.class.getName();
        Handler mockHandler = attachMockHandler(loggerName);

        try {
            dbConnection.connectToDatabase(null, "password");

            ArgumentCaptor<LogRecord> captor = ArgumentCaptor.forClass(LogRecord.class);
            verify(mockHandler, atLeastOnce()).publish(captor.capture());

            LogRecord record = captor.getValue();
            assertEquals(Level.SEVERE, record.getLevel(),
                    "A null username must be logged at SEVERE");
            assertEquals("Database connection error", record.getMessage(),
                    "Log message must be the generic admin message, not user-facing detail");
        } finally {
            detachHandler(loggerName, mockHandler);
        }
    }

    @Test
    void testLogger_nullPassword_logsSevere() {
        String loggerName = DatabaseConnection.class.getName();
        Handler mockHandler = attachMockHandler(loggerName);

        try {
            dbConnection.connectToDatabase("mickey", null);

            ArgumentCaptor<LogRecord> captor = ArgumentCaptor.forClass(LogRecord.class);
            verify(mockHandler, atLeastOnce()).publish(captor.capture());

            assertEquals(Level.SEVERE, captor.getValue().getLevel());
        } finally {
            detachHandler(loggerName, mockHandler);
        }
    }

    @Test
    void testLogger_wrongPassword_logsSevere() {
        String loggerName = DatabaseConnection.class.getName();
        Handler mockHandler = attachMockHandler(loggerName);

        try {
            dbConnection.connectToDatabase("donald", "wrongpass");

            ArgumentCaptor<LogRecord> captor = ArgumentCaptor.forClass(LogRecord.class);
            verify(mockHandler, atLeastOnce()).publish(captor.capture());

            assertEquals(Level.SEVERE, captor.getValue().getLevel());
        } finally {
            detachHandler(loggerName, mockHandler);
        }
    }

    @Test
    void testLogger_unknownUser_logsSevere() {
        String loggerName = DatabaseConnection.class.getName();
        Handler mockHandler = attachMockHandler(loggerName);

        try {
            dbConnection.connectToDatabase("pluto", "dog123");

            ArgumentCaptor<LogRecord> captor = ArgumentCaptor.forClass(LogRecord.class);
            verify(mockHandler, atLeastOnce()).publish(captor.capture());

            assertEquals(Level.SEVERE, captor.getValue().getLevel());
        } finally {
            detachHandler(loggerName, mockHandler);
        }
    }

    @Test
    void testLogger_adminWrongPassword_logsSevere() {
        String loggerName = DatabaseConnection.class.getName();
        Handler mockHandler = attachMockHandler(loggerName);

        try {
            dbConnection.connectToDatabase("admin", "wrongpass");

            ArgumentCaptor<LogRecord> captor = ArgumentCaptor.forClass(LogRecord.class);
            verify(mockHandler, atLeastOnce()).publish(captor.capture());

            assertEquals(Level.SEVERE, captor.getValue().getLevel());
        } finally {
            detachHandler(loggerName, mockHandler);
        }
    }

    @Test
    void testLogger_successfulLogin_doesNotLog() {
        // A clean successful login must NOT trigger any SEVERE log record
        String loggerName = DatabaseConnection.class.getName();
        Handler mockHandler = attachMockHandler(loggerName);

        try {
            dbConnection.connectToDatabase("goofy", "good123");

            // No SEVERE records should have been published
            ArgumentCaptor<LogRecord> captor = ArgumentCaptor.forClass(LogRecord.class);
            verify(mockHandler, never()).publish(
                    argThat(r -> r.getLevel().equals(Level.SEVERE)));
        } finally {
            detachHandler(loggerName, mockHandler);
        }
    }

    @Test
    void testLogger_adminSuccessfulLogin_doesNotLog() {
        String loggerName = DatabaseConnection.class.getName();
        Handler mockHandler = attachMockHandler(loggerName);

        try {
            dbConnection.connectToDatabase("admin", "secr3tP@ss");

            verify(mockHandler, never()).publish(
                    argThat(r -> r.getLevel().equals(Level.SEVERE)));
        } finally {
            detachHandler(loggerName, mockHandler);
        }
    }
}
