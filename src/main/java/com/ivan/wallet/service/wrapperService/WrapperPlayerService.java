package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.service.AdminWalletUserService;
import com.ivan.wallet.service.PlayerWalletService;

import java.math.BigDecimal;
import java.util.Scanner;

public class WrapperPlayerService {
    private static final WrapperPlayerService INSTANCE = new WrapperPlayerService();
    public static WrapperPlayerService getINSTANCE() {
        return INSTANCE;
    }
    private WrapperPlayerService() {
    }
    PlayerWalletService walletService = PlayerWalletService.getINSTANCE();
    AdminWalletUserService adminWalletUserService = AdminWalletUserService.getINSTANCE();

    Scanner scanner = new Scanner(System.in);
    char[] password;

    public void wrapperRegistration() {
        scanner = new Scanner(System.in);
        System.out.println("Введите имя игрока: ");
        String username = scanner.nextLine();
        System.out.println("Введите пароль игрока: ");
        password = scanner.nextLine().toCharArray();
        walletService.registration(username, password);
    }

    public void wrapperAuthorization(WalletConsole walletConsole) {
        scanner = new Scanner(System.in);
        System.out.println("Введите имя игрока: ");
        String username = scanner.nextLine();
        System.out.println("Введите пароль игрока: ");
        password = scanner.nextLine().toCharArray();
        String authorized = walletService.authorization(username, password);
        if (authorized != null) {
            walletConsole.setLogIn(true);
            walletConsole.setLoggedInUserName(username);
        } else {
            System.out.println("Игрок не авторизован.");
            walletConsole.setLogIn(false);
        }
    }

    public void wrapperCurrentPlayerBalance(WalletConsole player) {
        BigDecimal balance = walletService.currentPlayerBalance(player.getLoggedInUserName());
        System.out.println("Текущий баланс игрока: " + balance);
    }



    public void wrapperAudit() {
        System.out.println("admin, аудит какого пользователя хотите посмотреть?");
        for (String users : walletService.getPlayers().keySet()) {
            System.out.println("---" + users + "---");
        }
        String username = scanner.nextLine();
        adminWalletUserService.audit(username);
    }
    public void wrapperLogOut(WalletConsole logged){
        walletService.logOut(logged);
    }
    public void wrapperExit() {
    walletService.exit();
    }
    public void wrapperIncorrect() {
        walletService.incorrect();
    }
}