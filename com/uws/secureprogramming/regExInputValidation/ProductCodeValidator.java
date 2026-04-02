package com.uws.secureprogramming.regExInputValidation;

public class ProductCodeValidator implements iProductValidator {

    // Pattern breakdown:
    // ^        - start of string anchor
    // [A-Z]{3} - exactly THREE uppercase letters (A-Z only, no lowercase, no digits)
    // -        - literal hyphen dash
    // [0-9]{4} - exactly FOUR digits (0-9 only, no letters)
    // $        - end of string anchor (single $ is correct; $$ was a typo)
    private static final String PRODUCT_CODE_REGEX = "^[A-Z]{3}-[0-9]{4}$";

    @Override
    public boolean isValidProductCode(String productCode) {
        // Guard against null input — calling .matches() on null throws NullPointerException
        if (productCode == null) {
            return false;
        }
        return productCode.matches(PRODUCT_CODE_REGEX);
    }

}
