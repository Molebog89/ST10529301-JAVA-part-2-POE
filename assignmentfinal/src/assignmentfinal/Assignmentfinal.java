/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentfinal;

import java.util.Scanner;

public class Assignmentfinal {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Variables
        String firstName;
        String surname;
        String username;
        String password;
        String cellphoneNumber;

        System.out.println("========== REGISTER ==========");

        // =========================
        // GET FIRST NAME
        // =========================
        System.out.println("Enter your first name: ");
        firstName = input.nextLine();

        // =========================
        // GET SURNAME
        // =========================
        System.out.println("Enter your surname: ");
        surname = input.nextLine();

        // =========================
        // USERNAME VALIDATION
        // =========================
        while (true) {

            System.out.println("Enter your username: ");
            username = input.nextLine();

            if (Login.checkUsername(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println(
                    "Username is not correctly formatted."
                );
                System.out.println(
                    "Username must contain an underscore (_) "
                    + "and be no more than 5 characters long."
                );
                System.out.println();
            }
        }

        // =========================
        // PASSWORD VALIDATION
        // =========================
        while (true) {

            System.out.println("Enter your password: ");
            password = input.nextLine();

            if (Login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println(
                    "Password is not correctly formatted."
                );
                System.out.println("Password must:");
                System.out.println("- Be at least 8 characters long");
                System.out.println("- Contain at least one capital letter");
                System.out.println("- Contain at least one number");
                System.out.println("- Contain at least one special character");
                System.out.println();
            }
        }

        // =========================
        // CELLPHONE VALIDATION
        // =========================
        while (true) {

            System.out.print("Enter your cellphone number: ");
            cellphoneNumber = input.nextLine().trim();

            if (Login.checkCellphoneNumber(cellphoneNumber)) {
                System.out.println("Cellphone number successfully captured.");
                break;
            } else {
                System.out.println(
                    "Error: Please enter a valid cellphone number."
                );
                System.out.println(
                    "Example: 0821234567 or +27821234567"
                );
            }
        }

        // =========================
        // CREATE LOGIN OBJECT (this is the "registered" user)
        // =========================
        Login user = new Login(
            firstName,
            surname,
            username,
            password,
            cellphoneNumber
        );

        // =========================
        // REGISTRATION SUCCESS
        // =========================
        System.out.println();
        System.out.println("Registration successful!");
        System.out.println(
            "Welcome " + user.getFirstName() + " " + user.getSurname()
        );

        // =========================
        // LOGIN
        // =========================
        Login.runLoginPrompt(user, input);

    // OPEN QUICKCHAT ONLY AFTER SUCCESSFUL LOGIN
           if (user.isLoginSuccessful()) {
            System.out.println("\nLogin process completed.");
            Messages.startQuickChat(input);
        } else {
            System.out.println(
                "\nQuickChat cannot open because login failed."
            );
        }

        input.close();
    }
}