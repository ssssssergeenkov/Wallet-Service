package com.ivan.wallet.in.handlers;

import java.util.Scanner;

public class AppRunnerHandler {
    Scanner scanner = new Scanner(System.in);

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

    public void displayAppRunnerMenu() {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("          Выберите действие:         ");
        System.out.println("  1. Регистрация                     ");
        System.out.println("  2. Авторизация                     ");
        System.out.println("  3. Выход                           ");
        System.out.println("╚═══════════════════════════════════╝");
    }
}