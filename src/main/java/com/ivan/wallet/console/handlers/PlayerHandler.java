package com.ivan.wallet.console.handlers;

/**
 * The PlayerHandler class is responsible for displaying the player menu.
 */
public class PlayerHandler {

    /**
     * Display the player menu.
     */
    public void displayPlayerMenu() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("  1. Получение текущего баланса игрока          ");
        System.out.println("  2. Дебетовая транзакция                       ");
        System.out.println("  3. Кредитная транзакция                       ");
        System.out.println("  4. Просмотр истории транзакций                ");
        System.out.println("  5. Выход из аккаунта игрока                   ");
        System.out.println("  6. Выход из приложения                        ");
        System.out.println("  7. Удалить аккаунт                            ");
        System.out.println("╚══════════════════════════════════════════════╝");
    }
}