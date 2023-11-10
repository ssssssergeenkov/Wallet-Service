package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.service.PlayerWalletService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperPlayerService { //wrapper директория отдаленно напоминает validator
    private static final WrapperPlayerService INSTANCE = new WrapperPlayerService();

    public static WrapperPlayerService getINSTANCE() {
        return INSTANCE;
    }

    PlayerWalletService playerWalletService = PlayerWalletService.getINSTANCE();
    PlayersDao playersDao = PlayersDao.getINSTANCE();

    Scanner scanner = new Scanner(System.in);
    String password;

    public void wrapperRegistration() {
        scanner = new Scanner(System.in);
        System.out.print("Введите имя игрока: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль игрока: ");
        password = scanner.nextLine();
        playerWalletService.registration(username, password);
    }

    public void wrapperAuthorization(WalletConsole walletConsole) {
        scanner = new Scanner(System.in);
        System.out.print("Введите имя игрока: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль игрока: ");
        password = scanner.nextLine();
        boolean authorized = playerWalletService.authorization(username, password);
        if (authorized) {
            walletConsole.setLogIn(true);
            walletConsole.setLoggedInUserName(username);
        } else {
            System.out.println("Игрок не авторизован.");
            walletConsole.setLogIn(false);
        }
    }

    public void wrapperCurrentPlayerBalance(WalletConsole player) {
        BigDecimal balance = playerWalletService.currentPlayerBalance(player.getLoggedInUserName());
        System.out.println("Текущий баланс игрока " + player.getLoggedInUserName() + ": " + balance);
    }

    public void wrapperLogOut(WalletConsole logged) {
        playerWalletService.logOut(logged);
    }

    public void wrapperExit() {
        playerWalletService.exit();
    }

    public void wrapperDeleteAccount(WalletConsole walletConsole) {
        System.out.println("Вы уверены?");
        String answer = scanner.nextLine();

        if ("да".equals(answer) || "da".equals(answer)) {
            playerWalletService.deleteAccount(walletConsole.getLoggedInUserName());
            System.out.println("Игрок " + walletConsole.getLoggedInUserName() + " удален");
            wrapperLogOut(walletConsole);
        } else {
            playerWalletService.deleteAccount(null);
            System.out.println("Удаление отменено");
        }
    }

    public void wrapperAdminDeleteAccount(WalletConsole walletConsole) {
        System.out.println("admin, аудит какого пользователя хотите удалить?");

        List<Player> players = playersDao.findAll();

        if (!players.isEmpty()) {
            for (Player player : players) {
                System.out.print(player.getName() + ", ");
            }
            System.out.println("\nБольше никого нет");
        } else {
            System.out.println("База данных пуста");
        }

        scanner = new Scanner(System.in);
        String player = scanner.nextLine();

        String answer = null;
        if ("admin".equals(player)) {
            System.out.println("admin не может удалить свой аккаунт");
        } else {
            System.out.println("Вы уверены?");
            answer = scanner.nextLine();
        }

        if ("да".equals(answer) || "da".equals(answer)) {
            playerWalletService.deleteAccount(player);
            System.out.println("Игрок " + player + " удален");
        } else {
            playerWalletService.deleteAccount(null);
            System.out.println("Удаление отменено");
        }
    }

    public void wrapperIncorrect() {
        playerWalletService.incorrect();
    }
}