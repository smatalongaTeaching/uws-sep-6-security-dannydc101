package com.uws.secureprogramming.AgeInputValidation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class iAgeCheckTest {

    private final iAgeCheck ageCheck = new SecureAgeCheck();

    @Test
    void testAdultAge() {
        assertEquals(iAgeCheck.Adult, ageCheck.checkAge("18"));
        assertEquals(iAgeCheck.Adult, ageCheck.checkAge("25"));
        assertEquals(iAgeCheck.Adult, ageCheck.checkAge("100"));
    }

    @Test
    void testMinorAge() {
        assertEquals(iAgeCheck.Minor, ageCheck.checkAge("0"));
        assertEquals(iAgeCheck.Minor, ageCheck.checkAge("10"));
        assertEquals(iAgeCheck.Minor, ageCheck.checkAge("15"));
    }

    @Test
    void testInvalidInput() {
        // Non-numeric input: caught by NumberFormatException -> returns Minor
        assertEquals(iAgeCheck.Minor, ageCheck.checkAge("abc"));
        assertEquals(iAgeCheck.Minor, ageCheck.checkAge(""));
        // Negative number: out of range (0-120) -> returns Minor
        assertEquals(iAgeCheck.Minor, ageCheck.checkAge("-1"));
    }

    @Test
    void testBoundaryValues() {
        assertEquals(iAgeCheck.Minor, ageCheck.checkAge("17"));
        assertEquals(iAgeCheck.Adult, ageCheck.checkAge("18"));
    }

    @Test
    void testOutOfRangeValues() {
        // Above maximum reasonable age -> returns Minor
        assertEquals(iAgeCheck.Minor, ageCheck.checkAge("121"));
        assertEquals(iAgeCheck.Minor, ageCheck.checkAge("999"));
    }
}
