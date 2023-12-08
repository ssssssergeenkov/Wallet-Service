package com.ivan.wallet.service;

import com.ivan.wallet.console.WalletConsole;
import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.impl.AuditsDaoImpl;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.service.impl.AdminWalletServiceImpl;
import com.ivan.wallet.service.impl.PlayerWalletServiceImpl;

import static com.ivan.wallet.domain.types.ActionType.AUTHORIZATION_ACTION;
import static com.ivan.wallet.domain.types.ActionType.LOG_OUT_ACTION;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;

/**
 * Abstract class representing the user's wallet service
 */
public abstract class AbstractUserWalletService {
    AuditsDao<String, Audits> auditsDaoImpl = AuditsDaoImpl.getINSTANCE();

    AdminWalletServiceImpl adminWalletServiceImpl = AdminWalletServiceImpl.getINSTANCE();
    PlayerWalletServiceImpl playerWalletService = PlayerWalletServiceImpl.getINSTANCE();

    /**
     * Initializes the user's wallet service.
     */
    protected void initialize() {
        if (adminWalletServiceImpl == null && playerWalletService == null) {
            adminWalletServiceImpl = AdminWalletServiceImpl.getINSTANCE();
        }
    }

    /**
     * Log out the current player.
     *
     * @param walletConsole The WalletConsole instance.
     */
    public void logOut(WalletConsole walletConsole) {
        initialize();

        System.out.println("Выхожу из аккаунта");

        Audits audits = Audits.builder()
                .playerName(walletConsole.getLoggedInUserName())
                .actionType(LOG_OUT_ACTION)
                .identifierType(SUCCESS)
                .build();
        auditsDaoImpl.save(audits);

        walletConsole.setLoggedInUserName(null);
        walletConsole.setLogIn(false);
    }

    /**
     * Exit the application.
     *
     * @param walletConsole The WalletConsole instance.
     */
    public void exit(WalletConsole walletConsole) {
        initialize();

        Audits audits = Audits.builder()
                .playerName(walletConsole.getLoggedInUserName())
                .actionType(AUTHORIZATION_ACTION)
                .identifierType(SUCCESS)
                .build();
        auditsDaoImpl.save(audits);

        System.out.println("Выхожу из приложения");
        System.exit(0);
    }
}