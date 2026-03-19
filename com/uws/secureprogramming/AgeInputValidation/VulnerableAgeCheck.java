package com.uws.secureprogramming.AgeInputValidation;

public class VulnerableAgeCheck implements iAgeCheck {

    @Override
    public String checkAge(String age) {
        int userAge = Integer.parseInt(age); // Tries to convert the string to an integer

        if (userAge >= 18) {
            return iAgeCheck.Adult;
        } else {
            return iAgeCheck.Minor;
        }

    }

}
