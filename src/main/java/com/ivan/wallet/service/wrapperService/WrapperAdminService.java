package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.service.AdminWalletUserService;
import com.ivan.wallet.service.PlayerWalletService;

import java.util.Scanner;

public class WrapperAdminService implements WrapperUserServiceImpl {
    private static final WrapperAdminService INSTANCE = new WrapperAdminService();
    public static WrapperAdminService getINSTANCE() {
        return INSTANCE;
    }
    private WrapperAdminService() {
    }
    PlayerWalletService walletService = PlayerWalletService.getINSTANCE();
    AdminWalletUserService adminWalletUserService = AdminWalletUserService.getINSTANCE();

    Scanner scanner = new Scanner(System.in);
    char[] password;

    @Override
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

    public void wrapperAudit() {
        System.out.println("admin, аудит какого пользователя хотите посмотреть?");
        for (String users : walletService.getPlayers().keySet()) {
            System.out.println("---" + users + "---");
        }
        String username = scanner.nextLine();
        adminWalletUserService.audit(username);
    }

    @Override
    public void wrapperLogOut(WalletConsole logged){
        walletService.logOut(logged);
    }

    @Override
    public void wrapperExit() {
        walletService.exit();
    }

    @Override
    public void wrapperIncorrect() {
        walletService.incorrect();
    }
}