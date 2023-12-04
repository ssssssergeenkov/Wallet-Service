package com.ivan.wallet.in.handlers;

import java.util.Scanner;

/**
 * The AppRunnerHandler class is responsible for handling user input and displaying the application runner menu.
 */
public class AppRunnerHandler {
    Scanner scanner = new Scanner(System.in);

    /**
     * Read the user's choice from the input.
     *
     * @return The user's choice as an integer.
     */
    public int readChoice() {
        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
        }
        return choice;
    }

    /**
     * Display the application runner menu.
     */
    public void displayAppRunnerMenu() {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("          Выберите действие:         ");
        System.out.println("  1. Регистрация                     ");
        System.out.println("  2. Авторизация                     ");
        System.out.println("  3. Выход                           ");
        System.out.println("╚═══════════════════════════════════╝");
    }
}