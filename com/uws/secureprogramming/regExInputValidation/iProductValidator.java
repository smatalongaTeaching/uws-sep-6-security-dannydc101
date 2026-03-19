package com.uws.secureprogramming.regExInputValidation;

public interface iProductValidator {
    /**
     * Validates a product code against a regular expression.
     * Product Rules ("THREE uppercase letters - FOUR numbers" (e.g., "ABC-1234"))
     * @param productCode
     * @return true if the product code is valid, false otherwise
     */
    boolean isValidProductCode(String productCode);
}
