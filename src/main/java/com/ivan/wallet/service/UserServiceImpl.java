package com.ivan.wallet.service;

import com.ivan.wallet.in.WalletConsole;

public interface UserServiceImpl {

    void logOut(WalletConsole walletConsole);

    void exit();

    void incorrect();

    String authorization(String username, char[] password);
}