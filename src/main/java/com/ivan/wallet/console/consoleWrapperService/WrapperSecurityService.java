package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.console.WalletConsole;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.service.SecurityService;
import com.ivan.wallet.service.impl.SecurityServiceImpl;

import java.util.Scanner;

/**
 * The WrapperSecurityService class provides wrapper methods for interacting with the SecurityService.
 * It handles user input and performs player registration and authorization.
 */
public class WrapperSecurityService {
    private static final WrapperSecurityService INSTANCE = new WrapperSecurityService();

    public static WrapperSecurityService getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Get the singleton instance of WrapperSecurityService.
     */
    SecurityService securityServiceImpl = SecurityServiceImpl.getINSTANCE();

    Scanner scanner = new Scanner(System.in);
    String password;

    /**
     * Wrapper method for player registration.
     * Prompts the user to enter a username and password, and calls the registration method of the SecurityService.
     */
    public void wrapperRegistration() {
        scanner = new Scanner(System.in);
        System.out.print("Введите имя игрока: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль игрока: ");
        password = scanner.nextLine();
        securityServiceImpl.registration(username, password);
    }

    /**
     * Wrapper method for player authorization.
     * Prompts the user to enter a username and password, and calls the authorization method of the SecurityService.
     * If the player is authorized, updates the login status and logged-in username in the WalletConsole.
     *
     * @param walletConsole The instance of WalletConsole.
     */
    public void wrapperAuthorization(WalletConsole walletConsole) {
        scanner = new Scanner(System.in);
        System.out.print("Введите имя игрока: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль игрока: ");
        password = scanner.nextLine();
        Player authorized = securityServiceImpl.authorization(username, password);
        if (authorized != null) {
            walletConsole.setLogIn(true);
            walletConsole.setLoggedInUserName(username);
        } else {
            System.out.println("Игрок не авторизован.");
            walletConsole.setLogIn(false);
        }
    }
}