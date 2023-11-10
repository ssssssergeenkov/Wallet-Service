package com.ivan.wallet.service;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.model.Action;
import com.ivan.wallet.model.Player;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.ivan.wallet.model.types.ActionType.*;
import static com.ivan.wallet.model.types.IdentifierType.FAIL;
import static com.ivan.wallet.model.types.IdentifierType.SUCCESS;

/**
 * Класс WalletService предоставляет сервис для работы с кошельком игроков.
 * Позволяет регистрировать и авторизовывать игроков, выполнять дебетовые и кредитные транзакции,
 * просматривать историю транзакций и аудит действий игрока.
 */
public class PlayerWalletService implements UserServiceImpl {
    private static final PlayerWalletService INSTANCE = new PlayerWalletService();

    public static PlayerWalletService getINSTANCE() {
        return INSTANCE;
    }

    private PlayerWalletService() {
        this.players = new HashMap<>();
        players.put("admin", new Player("admin", "admin".toCharArray()));
    }

    @Getter
    private final Map<String, Player> players;

    /**
     * Регистрирует нового игрока с указанным именем и паролем.
     *
     * @param username имя игрока
     * @param password пароль игрока
     */
    public void registration(String username, char[] password) {
        Action action;
        Player newPlayer = new Player(username, password);
        if (!getPlayers().containsKey(username)) {
            getPlayers().put(username, newPlayer);
            action = new Action(REGISTRATION_ACTION, SUCCESS);
            newPlayer.getActionsHistory().add(action);
            System.out.println("Игрок " + username + " зарегистрирован");
        } else {
            action = new Action(REGISTRATION_ACTION, FAIL);
            newPlayer.getActionsHistory().add(action);
            System.out.println("Игрок " + username + " уже существует.");
        }
    }

    /**
     * Авторизует игрока с указанным именем и паролем.
     *
     * @param username имя игрока
     * @param password пароль игрока
     * @return true, если авторизация успешна, в противном случае - false
     */
    @Override
    public String authorization(String username, char[] password) {
        Player player = getPlayers().get(username);
        if (getPlayers().containsKey(username)) {
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

    /**
     * Возвращает текущий баланс игрока с указанным именем.
     *
     * @param username имя игрока
     * @return текущий баланс игрока
     */
    public BigDecimal currentPlayerBalance(String username) {
        Player player = getPlayers().get(username);
        if (getPlayers().containsKey(username)) {
            Action action = new Action(CURRENT_PLAYER_BALANCE_ACTION, SUCCESS);
            player.getActionsHistory().add(action);
            return player.getBalance();
        } else {
            System.out.println("Игрок с именем " + username + " не найден, у не го нет баланса");
            return BigDecimal.ZERO;
        }
    }


    @Override
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
