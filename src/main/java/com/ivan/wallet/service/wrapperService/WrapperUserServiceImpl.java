package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.in.WalletConsole;

public interface WrapperUserServiceImpl {

    void wrapperLogOut(WalletConsole walletConsole);

    void wrapperExit();

    void wrapperIncorrect();

}