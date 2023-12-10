package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.console.WalletConsole;

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

    public void wrapperIncorrect() {
        System.out.println("Некорректный выбор команды. Попробуйте еще раз.");
    }
}