package com.uws.secureprogramming.regExInputValidation;

public class ProductCodeValidator implements iProductValidator {

    private static final String PRODUCT_CODE_REGEX = "^[A-Za-z0-9]{3}-[A-Za-z0-9]{4}$$";

    @Override
    public boolean isValidProductCode(String productCode) {
       return productCode.matches(PRODUCT_CODE_REGEX);
    }

}
