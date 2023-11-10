package com.ivan.wallet;

import com.ivan.wallet.in.WalletConsole;

/**
 * Класс AppRunner представляет точку входа в приложение.
 * Запускает экземпляр класса WalletConsole и вызывает метод start() для начала работы приложения.
 */
public class AppRunner {
    public static void main(String[] args) {
        WalletConsole walletConsole = WalletConsole.getINSTANCE();
        walletConsole.start();
    }
}