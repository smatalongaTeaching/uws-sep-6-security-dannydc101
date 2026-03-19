package com.uws.secureprogramming.regExInputValidation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class iProductValidatorTest {

    // Minimal implementation for testing purposes
    private final iProductValidator validator = new ProductCodeValidator();

    @Test
    void testValidProductCode() {
        assertTrue(validator.isValidProductCode("ABC-1234"));
        assertTrue(validator.isValidProductCode("XYZ-0000"));
    }

    @Test
    void testInvalidProductCodes_LowercaseLetters() {
        assertFalse(validator.isValidProductCode("abc-1234"));
        assertFalse(validator.isValidProductCode("abd-1234"));
    }

    @Test
    void testInvalidProductCodes_MixedCharacters() {
        assertFalse(validator.isValidProductCode("123-Abc"));
        assertFalse(validator.isValidProductCode("ab1-1234"));
    }

    @Test
    void testInvalidProductCode_MissingDash() {
        assertFalse(validator.isValidProductCode("ABC1234"));
    }

    @Test
    void testInvalidProductCode_WrongLetterCount() {
        assertFalse(validator.isValidProductCode("AB-1234"));
        assertFalse(validator.isValidProductCode("ABCD-1234"));
    }

    @Test
    void testInvalidProductCode_WrongNumberCount() {
        assertFalse(validator.isValidProductCode("AB-123"));
        assertFalse(validator.isValidProductCode("ABC-12345"));
    }

    @Test
    void testInvalidProductCode_NullOrEmpty() {
        assertFalse(validator.isValidProductCode(null));
        assertFalse(validator.isValidProductCode(""));
    }

    @Test
    void testInvalidProductCode_SpecialCharacters() {
        assertFalse(validator.isValidProductCode("A*C-1234"));
        assertFalse(validator.isValidProductCode("ABC-12#4"));
    }
}