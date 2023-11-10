package com.ivan.wallet.in.handlers;

/**
 * Класс AdminHandler предоставляет методы для отображения меню администратора.
 */
public class AdminHandler {

    /**
     * Метод для отображения меню администратора.
     */
    public void displayAdminMenu() {
        System.out.println("_______________________________");
        System.out.println("Дорогой Админ, выбери действие:");
        System.out.println("1. Аудит действий игрока");
        System.out.println("2. Выход из аккаунта");
        System.out.println("3. Выход из приложения");
        System.out.println("_______________________________");
    }
}