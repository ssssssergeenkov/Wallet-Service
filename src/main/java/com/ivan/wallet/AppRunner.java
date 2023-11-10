package com.ivan.wallet;

import com.ivan.wallet.in.WalletConsole;

public class AppRunner {
    public static void main(String[] args) {
        WalletConsole walletConsole = WalletConsole.getINSTANCE();
        walletConsole.start();
    }
}
