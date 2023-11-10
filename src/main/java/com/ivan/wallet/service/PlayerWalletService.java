package com.ivan.wallet.service;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.domain.Action;
import com.ivan.wallet.domain.Player;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.ivan.wallet.domain.types.ActionType.*;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;

/**
 * Класс WalletService предоставляет сервис для работы с кошельком игроков.
 * Позволяет регистрировать и авторизовывать игроков, выполнять дебетовые и кредитные транзакции,
 * просматривать историю транзакций и аудит действий игрока.
 */
public class PlayerWalletService {
    private static final PlayerWalletService INSTANCE = new PlayerWalletService();

    public static PlayerWalletService getINSTANCE() {
        return INSTANCE;
    }

    private PlayerWalletService() {
        this.players = new HashMap<>();
        players.put("admin", new Player("admin", "admin"));
    }

    @Getter
    private final Map<String, Player> players;

    /**
     * Регистрирует нового игрока с указанным именем и паролем.
     *
     * @param username имя игрока
     * @param password пароль игрока
     */
    public void registration(String username, String password) {
        Action action;
        Player newPlayer = new Player(username, password);

        if (username.isEmpty() && password.isEmpty()) {
            System.out.println("Вы ввели пустое значение");
        }

        if (!getPlayers().containsKey(username)) {
            getPlayers().put(username, newPlayer);
            action = new Action(REGISTRATION_ACTION, SUCCESS);
            newPlayer.getActionsHistory().add(action);
            System.out.println("Игрок " + username + " зарегистрирован");
        } else {
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
    public String authorization(String username, String password) {
        Player player = getPlayers().get(username);

        if (getPlayers().containsKey(username) && player.getPassword() != null && player.getPassword().equals(password)) {
            Action action = new Action(AUTHORIZATION_ACTION, SUCCESS);
            player.getActionsHistory().add(action);
            System.out.println("Игрок " + username + " авторизован.");
            return username;
        } else {
            System.out.println("Ошибка авторизации. Проверьте имя пользователя и пароль.");
            return null;
        }
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

    /**
     * Метод для выхода из аккаунта пользователя.
     *
     * @param walletConsole - экземпляр класса WalletConsole
     */
    public void logOut(WalletConsole walletConsole) {
        System.out.println("Выход из аккаунта");
        walletConsole.setLoggedInUserName(null);
        walletConsole.setLogIn(false);
    }

    /**
     * Метод для выхода из приложения.
     */
    public void exit() {
        System.out.println("Выход из приложения");
        System.exit(0);
    }

    /**
     * Метод для вывода сообщения о некорректном выборе команды.
     */
    public void incorrect() {
        System.out.println("Некорректный выбор команды. Попробуйте еще раз.");
    }
}