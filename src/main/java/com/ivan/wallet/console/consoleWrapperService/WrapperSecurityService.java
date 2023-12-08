package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.console.WalletConsole;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.service.SecurityService;
import com.ivan.wallet.service.impl.SecurityServiceImpl;

import java.util.Scanner;

public class WrapperSecurityService {
    private static final WrapperSecurityService INSTANCE = new WrapperSecurityService();

    public static WrapperSecurityService getINSTANCE() {
        return INSTANCE;
    }

    SecurityService securityServiceImpl = SecurityServiceImpl.getINSTANCE();

    Scanner scanner = new Scanner(System.in);
    String password;

    public void wrapperRegistration() {
        scanner = new Scanner(System.in);
        System.out.print("Введите имя игрока: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль игрока: ");
        password = scanner.nextLine();
        securityServiceImpl.registration(username, password);
    }

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