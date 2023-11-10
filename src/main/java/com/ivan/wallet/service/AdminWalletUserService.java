package com.ivan.wallet.service;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.model.Action;
import com.ivan.wallet.model.Player;

import java.util.Arrays;
import java.util.List;

import static com.ivan.wallet.model.types.ActionType.AUTHORIZATION_ACTION;
import static com.ivan.wallet.model.types.IdentifierType.SUCCESS;

public class AdminWalletUserService implements UserServiceImpl {
    private static final AdminWalletUserService INSTANCE = new AdminWalletUserService();
    PlayerWalletService walletService = PlayerWalletService.getINSTANCE();

    public static AdminWalletUserService getINSTANCE() {
        return INSTANCE;
    }

    private AdminWalletUserService() {

    }

    @Override
    public String authorization(String username, char[] password) {
        Player player = walletService.getPlayers().get(username);
        if (walletService.getPlayers().containsKey(username)) {
            Action action = new Action(AUTHORIZATION_ACTION, SUCCESS);
            player.getActionsHistory().add(action);
            char[] playerPassword = player.getPassword();
            if (Arrays.equals(playerPassword, password)) {
                System.out.println("Игрок " + username + " авторизован.");
                return username;
            }
        }
        System.out.println("Ошибка авторизации. Проверьте имя пользователя и пароль.");
        return null;
    }
    public int audit(String username) {
        if (walletService.getPlayers().containsKey(username)) {
            Player player = walletService.getPlayers().get(username);
            List<Action> actions = player.getActionsHistory();

            if (actions != null) {
                if (actions.isEmpty()) {
                    System.out.println("У игрока " + username + " нет аудита транзакций.");
                    return 0;
                } else {
                    System.out.println("Аудит операций для игрока " + username + ":");
                    for (Action action : actions) {
                        System.out.println("Операция: " + action.actionName() + " | " + action.identifierType());
                    }
                    return actions.size();
                }
            }
        } else {
            System.out.println("Пользователь " + username + " не найден");
            return -1;
        }
        return -1;
    }

    public void logOut(WalletConsole walletConsole) {
        System.out.println("Выход из аккаунта");
        walletConsole.setLoggedInUserName(null);
        walletConsole.setLogIn(false);
    }

    @Override
    public void exit() {
        System.out.println("Выход из приложения");
        System.exit(0);
    }

    @Override
    public void incorrect() {
        System.out.println("Некорректный выбор команды. Попробуйте еще раз.");
    }
}