package com.ivan.wallet.in.handlers;

import java.util.Scanner;

/**
 * Класс AppRunnerHandler предоставляет методы для работы с меню приложения.
 */
public class AppRunnerHandler {
    Scanner scanner = new Scanner(System.in);

    /**
     * Считывает выбор пользователя.
     *
     * @return выбор пользователя
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
     * Отображает меню приложения.
     */
    public void displayAppRunnerMenu() {
        System.out.println("_______________________________");
        System.out.println("Выберите действие:");
        System.out.println("1. Регистрация");
        System.out.println("2. Авторизация");
        System.out.println("3. Выход");
        System.out.println("_______________________________");
    }
}