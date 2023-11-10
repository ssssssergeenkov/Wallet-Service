package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.service.AuditWalletUserService;
import com.ivan.wallet.service.PlayerWalletService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Класс WrapperPlayerService предоставляет обертки для вызова методов класса PlayerWalletService.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperPlayerService {
    private static final WrapperPlayerService INSTANCE = new WrapperPlayerService();

    public static WrapperPlayerService getINSTANCE() {
        return INSTANCE;
    }

    PlayerWalletService walletService = PlayerWalletService.getINSTANCE();

    Scanner scanner = new Scanner(System.in);
    String password;

    /**
     * Обертка для метода registration класса PlayerWalletService.
     */
    public void wrapperRegistration() {
        scanner = new Scanner(System.in);
        System.out.println("Введите имя игрока: ");
        String username = scanner.nextLine();
        System.out.println("Введите пароль игрока: ");
        password = scanner.nextLine();
        walletService.registration(username, password);
    }

    /**
     * Обертка для метода authorization класса PlayerWalletService.
     *
     * @param walletConsole - экземпляр класса WalletConsole
     */
    public void wrapperAuthorization(WalletConsole walletConsole) {
        scanner = new Scanner(System.in);
        System.out.println("Введите имя игрока: ");
        String username = scanner.nextLine();
        System.out.println("Введите пароль игрока: ");
        password = scanner.nextLine();
        String authorized = walletService.authorization(username, password);
        if (authorized != null) {
            walletConsole.setLogIn(true);
            walletConsole.setLoggedInUserName(username);
        } else {
            System.out.println("Игрок не авторизован.");
            walletConsole.setLogIn(false);
        }
    }

    /**
     * Обертка для метода currentPlayerBalance класса PlayerWalletService.
     *
     * @param player - экземпляр класса WalletConsole
     */
    public void wrapperCurrentPlayerBalance(WalletConsole player) {
        BigDecimal balance = walletService.currentPlayerBalance(player.getLoggedInUserName());
        System.out.println("Текущий баланс игрока: " + balance);
    }

    /**
     * Обертка для метода logOut класса PlayerWalletService.
     *
     * @param logged - экземпляр класса WalletConsole
     */
    public void wrapperLogOut(WalletConsole logged) {
        walletService.logOut(logged);
    }

    /**
     * Обертка для метода exit класса PlayerWalletService.
     */
    public void wrapperExit() {
        walletService.exit();
    }

    /**
     * Обертка для метода incorrect класса PlayerWalletService.
     */
    public void wrapperIncorrect() {
        walletService.incorrect();
    }
}