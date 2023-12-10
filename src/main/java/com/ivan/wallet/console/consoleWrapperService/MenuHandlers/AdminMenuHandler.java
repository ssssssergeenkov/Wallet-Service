package com.ivan.wallet.console.consoleWrapperService.MenuHandlers;

/**
 * The AdminMenuHandler class is responsible for displaying the admin menu.
 */
public class AdminMenuHandler {

    /**
     * Display the admin menu.
     */
    public void displayAdminMenu() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("          Админ, выбери действие:         ");
        System.out.println("  1. Аудит действий игрока                ");
        System.out.println("  2. Выход из аккаунта                    ");
        System.out.println("  3. Выход из приложения                  ");
        System.out.println("╚════════════════════════════════════════╝");
    }
}