package com.uws.secureprogramming.AgeInputValidation;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your age: ");
        String userAgeStr = scanner.nextLine(); // Reads the whole line as a string

        iAgeCheck ageCheck = new VulnerableAgeCheck(); // Create an instance of the VulnerableAgeCheck class
        String userAge = ageCheck.checkAge(userAgeStr); // Tries to convert the string to an integer

        if (userAge.equals(iAgeCheck.Adult)) {
            System.out.println("You are an adult.");
        } else {
            System.out.println("You are a minor.");
        }

        scanner.close(); // Good practice to close the scanner
    }
}
