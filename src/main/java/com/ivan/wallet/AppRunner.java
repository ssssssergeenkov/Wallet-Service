package com.ivan.wallet;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.liquibase.LiquibaseDemo;

/**
 * The AppRunner class is the entry point of the application.
 * It initializes the necessary components and starts the application.
 */
public class AppRunner {
    public static void main(String[] args) {
        LiquibaseDemo liquibaseDemo = LiquibaseDemo.getInstance();
        liquibaseDemo.runMigrations();

        WalletConsole walletConsole = WalletConsole.getINSTANCE();
        walletConsole.start();
    }
}