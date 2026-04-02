package com.uws.secureprogramming.AgeInputValidation;

public class SecureAgeCheck implements iAgeCheck {

    @Override
    public String checkAge(String age) {
        try {
            int userAge = Integer.parseInt(age); //  convert the string to an int value

            // Check if the age is within a reasonable range
            if (userAge < 0 || userAge > 120) {
                System.out.println("Invalid input: Age must be between 0 and 120.");
                return iAgeCheck.Minor;
            }

            if (userAge >= 18) {
                return iAgeCheck.Adult;
            } else {
                return iAgeCheck.Minor;
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter a valid integer for age.");
            return iAgeCheck.Minor;
        }
    }

}
