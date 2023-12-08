package com.ivan.wallet.console;

import com.ivan.wallet.console.consoleWrapperService.WrapperAdminService;
import com.ivan.wallet.console.consoleWrapperService.WrapperPlayerService;
import com.ivan.wallet.console.consoleWrapperService.WrapperSecurityService;
import com.ivan.wallet.console.consoleWrapperService.WrapperTransactionService;
import com.ivan.wallet.console.handlers.AdminHandler;
import com.ivan.wallet.console.handlers.AppRunnerHandler;
import com.ivan.wallet.console.handlers.PlayerHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletConsole {
    private static final WalletConsole INSTANCE = new WalletConsole();

    /**
     * Get the singleton instance of WalletConsole.
     *
     * @return The instance of WalletConsole.
     */
    public static WalletConsole getINSTANCE() {
        return INSTANCE;
    }

    WrapperAdminService wrapperAdminService = WrapperAdminService.getINSTANCE();
    WrapperPlayerService wrapperPlayerService = WrapperPlayerService.getINSTANCE();
    WrapperSecurityService wrapperSecurityService = WrapperSecurityService.getINSTANCE();
    WrapperTransactionService wrapperTransactionService = WrapperTransactionService.getINSTANCE();

    @Getter
    @Setter
    String loggedInUserName = null;

    @Getter
    @Setter
    boolean logIn = false;

    /**
     * The start method is responsible for running the main application loop.
     * It displays menus based on the user's login status and handles user input.
     */
    public void start() {
        AdminHandler adminHandler = new AdminHandler();
        AppRunnerHandler appRunnerHandler = new AppRunnerHandler();
        PlayerHandler playerHandler = new PlayerHandler();

        while (true) {
            if (!logIn || loggedInUserName == null) {
                appRunnerHandler.displayAppRunnerMenu();
                int choice = appRunnerHandler.readChoice();

                switch (choice) {
                    case 1 -> wrapperSecurityService.wrapperRegistration();
                    case 2 -> wrapperSecurityService.wrapperAuthorization(INSTANCE);
                    case 3 -> wrapperPlayerService.wrapperExit(INSTANCE);
                    default -> wrapperPlayerService.wrapperIncorrect();
                }
            }
            if ("admin".equals(loggedInUserName)) {
                adminHandler.displayAdminMenu();
                int choice = appRunnerHandler.readChoice();

                switch (choice) {
                    case 1 -> wrapperAdminService.wrapperAudit();
                    case 2 -> wrapperAdminService.wrapperLogOut(INSTANCE);
                    case 3 -> wrapperAdminService.wrapperExit(INSTANCE);
                    default -> wrapperAdminService.wrapperIncorrect();
                }
            } else if (loggedInUserName != null) {
                playerHandler.displayPlayerMenu();
                int choice = appRunnerHandler.readChoice();

                switch (choice) {
                    case 1 -> wrapperPlayerService.wrapperCurrentPlayerBalance(INSTANCE);
                    case 2 -> wrapperTransactionService.wrapperDebit(INSTANCE);
                    case 3 -> wrapperTransactionService.wrapperCredit(INSTANCE);
                    case 4 -> wrapperTransactionService.wrapperTransactionHistory(INSTANCE);
                    case 5 -> wrapperPlayerService.wrapperLogOut(INSTANCE);
                    case 6 -> wrapperPlayerService.wrapperExit(INSTANCE);
                    case 7 -> wrapperPlayerService.wrapperDeleteAccount(INSTANCE);
                    default -> wrapperPlayerService.wrapperIncorrect();
                }
            }
        }
    }
}