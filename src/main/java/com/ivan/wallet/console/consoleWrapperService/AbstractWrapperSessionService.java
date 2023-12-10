package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.console.WalletConsole;

/**
 * The AbstractWrapperSessionService class is an abstract class that provides common wrapper methods
 * for managing player sessions.
 */
public abstract class AbstractWrapperSessionService {

    /**
     * Log out the current player.
     *
     * @param walletConsole The WalletConsole instance.
     */
    public void wrapperLogOut(WalletConsole walletConsole) {
        System.out.println("Выхожу из аккаунта");

        walletConsole.setLoggedInUserName(null);
        walletConsole.setLogIn(false);
    }

    /**
     * Display an error message for an incorrect command selection.
     */
    public void wrapperIncorrect() {
        System.out.println("Некорректный выбор команды. Попробуйте еще раз.");
    }
}