package com.uws.secureprogramming.regExInputValidation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class iProductValidatorTest {

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


    // ADDITIONAL TESTS


    @Test
    void testInvalidProductCode_WhitespacePadding() {
        assertFalse(validator.isValidProductCode(" ABC-1234"));
        assertFalse(validator.isValidProductCode("ABC-1234 "));
        assertFalse(validator.isValidProductCode(" ABC-1234 "));
    }

    @Test
    void testInvalidProductCode_EmbeddedWhitespace() {
        assertFalse(validator.isValidProductCode("AB C-1234"));
        assertFalse(validator.isValidProductCode("ABC -1234"));
        assertFalse(validator.isValidProductCode("ABC- 1234"));
        assertFalse(validator.isValidProductCode("ABC-12 34"));
    }

    @Test
    void testInvalidProductCode_NewlineCharacters() {
        assertFalse(validator.isValidProductCode("ABC-1234\n"));
        assertFalse(validator.isValidProductCode("\nABC-1234"));
        assertFalse(validator.isValidProductCode("ABC\n-1234"));
        assertFalse(validator.isValidProductCode("ABC-1234\t"));
    }

    @Test
    void testInvalidProductCode_UnicodeLetters() {
        assertFalse(validator.isValidProductCode("\u00c4BC-1234")); // Ä
        assertFalse(validator.isValidProductCode("ABC-123\u00d8")); // Ø
    }

    @Test
    void testInvalidProductCode_WrongDashType() {
        assertFalse(validator.isValidProductCode("ABC\u20131234")); // en-dash
        assertFalse(validator.isValidProductCode("ABC\u20141234")); // em-dash
    }

    @Test
    void testInvalidProductCode_MultipleDashes() {
        assertFalse(validator.isValidProductCode("ABC--1234"));
        assertFalse(validator.isValidProductCode("-ABC-1234"));
        assertFalse(validator.isValidProductCode("ABC-1234-"));
    }

    @Test
    void testInvalidProductCode_ReversedFormat() {
        assertFalse(validator.isValidProductCode("1234-ABC"));
    }

    @Test
    void testInvalidProductCode_AllDigitsOrAllLetters() {
        assertFalse(validator.isValidProductCode("123-1234"));
        assertFalse(validator.isValidProductCode("ABC-WXYZ"));
    }

    @Test
    void testInvalidProductCode_OffByOneLength() {
        assertFalse(validator.isValidProductCode("AB-1234"));
        assertFalse(validator.isValidProductCode("ABCD-1234"));
        assertFalse(validator.isValidProductCode("ABC-123"));
        assertFalse(validator.isValidProductCode("ABC-12345"));
    }

    @Test
    void testInvalidProductCode_InjectionStyleInputs() {
        assertFalse(validator.isValidProductCode("'; DROP TABLE products; --"));
        assertFalse(validator.isValidProductCode("<script>alert(1)</script>"));
        assertFalse(validator.isValidProductCode("../../../etc/passwd"));
    }

    @Test
    void testInvalidProductCode_VeryLongInput() {
        String longInput = "A".repeat(10_000) + "-" + "1".repeat(10_000);
        assertFalse(validator.isValidProductCode(longInput));
    }
}
