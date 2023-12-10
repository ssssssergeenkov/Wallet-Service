package com.ivan.wallet.service;

import com.ivan.wallet.console.WalletConsole;

/**
 * Abstract class representing the user's wallet service
 */
public abstract class AbstractSessionService {

    /**
     * Log out the current player.
     *
     * @param walletConsole The WalletConsole instance.
     */
    public void logOut(WalletConsole walletConsole) {
        walletConsole.setLoggedInUserName(null);
        walletConsole.setLogIn(false);
    }
}