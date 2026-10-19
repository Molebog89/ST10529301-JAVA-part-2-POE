/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentfinal;

import java.util.Scanner;

public class Login {

    // =========================================
    // PRIVATE FIELDS
    // =========================================
    private String firstName;
    private String surname;
    private String username;
    private String password;
    private String cellPhoneNumber;
    private boolean loginSuccessful;

    // =========================================
    // CONSTRUCTOR
    // =========================================
    public Login(
            String firstName,
            String surname,
            String username,
            String password,
            String cellPhoneNumber) {

        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.loginSuccessful = false;
    }

    // =========================================
    // GETTERS AND SETTERS
    // =========================================

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // =========================================
    // VALIDATION METHODS
    // (static so they can be used during registration,
    //  before a Login object even exists)
    // =========================================

    /**
     * Username must contain an underscore and be no more than
     * five characters long.
     */
    public static boolean checkUsername(String username) {

        if (username == null) {
            return false;
        }

        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Password must be at least eight characters long and contain
     * a capital letter, a number and a special character.
     */
    public static boolean checkPasswordComplexity(String password) {

        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        String specialChars = "~!@#$%^&*()_+{}|:\"<>?`[]\\;',./=";

        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            }

            if (Character.isDigit(ch)) {
                hasDigit = true;
            }

            if (specialChars.indexOf(ch) >= 0) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Accepts South African cellphone numbers in either local
     * (0821234567) or international (+27821234567) format.
     */
    public static boolean checkCellphoneNumber(String cellphoneNumber) {

        if (cellphoneNumber == null) {
            return false;
        }

        return cellphoneNumber.matches(
            "^(0[6-8][0-9]{8}|\\+27[6-8][0-9]{8})$"
        );
    }

    // =========================================
    // LOGIN
    // =========================================

    /**
     * Attempts to log the user in with the given credentials.
     * Stores the result so isLoginSuccessful() can be checked afterwards.
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {

        if (enteredUsername == null || enteredPassword == null) {
            loginSuccessful = false;
        } else {
            loginSuccessful =
                    enteredUsername.equals(username)
                    && enteredPassword.equals(password);
        }

        return loginSuccessful;
    }

    /**
     * Returns whether the most recent login attempt succeeded.
     * (The username parameter is accepted for API symmetry with
     * loginUser, but the result reflects the last loginUser call.)
     */
    public boolean isLoginSuccessful() {
    return loginSuccessful;
}

    // =========================================
    // INTERACTIVE CONSOLE LOGIN (used by Assignmentfinal)
    // =========================================
    public static void runLoginPrompt(Login user, Scanner input) {

        System.out.println();
        System.out.println("========== LOGIN ==========");

        int attempts = 0;

        while (attempts < 3) {

            System.out.println();
            System.out.println("Enter your username: ");
            String enteredUsername = input.nextLine();

            System.out.println("Enter your password: ");
            String enteredPassword = input.nextLine();

            if (user.loginUser(enteredUsername, enteredPassword)) {

    System.out.println();
    System.out.println("Login successful!");
    System.out.println();

    return;
                    
              

            } else {

                attempts++;

                System.out.println();
                System.out.println(
                    "Login failed. Incorrect username or password."
                );

                if (attempts < 3) {
                    System.out.println("Please try again.");
                }
            }
        }

        System.out.println();
        System.out.println("Too many unsuccessful login attempts.");
    }
}