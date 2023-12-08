package com.ivan.wallet;

import com.ivan.wallet.console.WalletConsole;
import com.ivan.wallet.liquibase.LiquibaseManager;

/**
 * The AppRunner class is the entry point of the application.
 * It initializes the necessary components and starts the application.
 */
public class AppRunner {
    public static void main(String[] args) {
        LiquibaseManager liquibaseManager = LiquibaseManager.getInstance();
        liquibaseManager.runMigrations();

        WalletConsole walletConsole = WalletConsole.getINSTANCE();
        walletConsole.start();
    }
}