package com.ivan.wallet.service;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.domain.types.ActionType;
import com.ivan.wallet.in.WalletConsole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

import static com.ivan.wallet.domain.types.ActionType.*;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;

/**
 * The PlayerWalletService class provides various operations related to player wallet management.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerWalletService {
    private static final PlayerWalletService INSTANCE = new PlayerWalletService();

    /**
     * Get the singleton instance of PlayerWalletService.
     *
     * @return The instance of PlayerWalletService.
     */
    public static PlayerWalletService getINSTANCE() {
        return INSTANCE;
    }

    PlayersDao playerDao = PlayersDao.getINSTANCE();
    AuditsDao auditsDao = AuditsDao.getINSTANCE();

    /**
     * Register a new player with the given username and password.
     *
     * @param username The username of the player.
     * @param password The password of the player.
     * @return True if the registration is successful, false otherwise.
     */
    public boolean registration(String username, String password) {
        Optional<Player> existingPlayer = playerDao.findByName(username);

        if (username.isEmpty() && password.isEmpty()) {
            System.out.println("вы ввели пустое значение");
            return false; //потом создам отдельную директорию для валидации. чтоб в методе регистрации была только регистрация без проверок условий
        }

        if (existingPlayer.isEmpty()) {
            Player savedPlayer = playerDao.createUser(username, password);
            System.out.println("Игрок " + savedPlayer.getName() + " успешно зарегистрирован.");
            auditsDao.createAudit(savedPlayer.getName(), ActionType.REGISTRATION_ACTION, SUCCESS);
            return true;
        } else
            System.out.println("Игрок " + username + " уже существует.");
        return false;
    }

    /**
     * Authorize a player with the given username and password.
     *
     * @param username The username of the player.
     * @param password The password of the player.
     * @return True if the authorization is successful, false otherwise.
     */
    public boolean authorization(String username, String password) {
        Optional<Player> player = playerDao.findByName(username);

        if (player.isPresent() && player.get().getPassword() != null && player.get().getPassword().equals(password)) {
            System.out.println("Игрок " + username + " авторизован.");
            auditsDao.createAudit(username, ActionType.AUTHORIZATION_ACTION, SUCCESS);
            return true;
        } else {
            System.out.println("Ошибка авторизации. Проверьте имя пользователя и пароль.");
            return false;
        }
    }

    /**
     * Get the balance of the current player.
     *
     * @param username The username of the player.
     * @return The balance of the player.
     */
    public BigDecimal currentPlayerBalance(String username) {
        Optional<Player> player = playerDao.findByName(username);

        if (player.isPresent()) {
            auditsDao.createAudit(username, CURRENT_BALANCE_ACTION, SUCCESS);
            return player.get().getBalance();
        } else {
            System.out.println("Игрок с именем " + username + " не найден, у него нет баланса");
            return BigDecimal.ZERO;
        }
    }

    /**
     * Log out the current player.
     *
     * @param walletConsole The WalletConsole instance.
     */
    public void logOut(WalletConsole walletConsole) {
        System.out.println("Выхожу из аккаунта");
        auditsDao.createAudit(walletConsole.getLoggedInUserName(), LOG_OUT_ACTION, SUCCESS);
        walletConsole.setLoggedInUserName(null);
        walletConsole.setLogIn(false);
    }

    /**
     * Exit the application.
     *
     * @param walletConsole The WalletConsole instance.
     */
    public void exit(WalletConsole walletConsole) {
        auditsDao.createAudit(walletConsole.getLoggedInUserName(), EXIT_ACTION, SUCCESS);
        System.out.println("Выхожу из приложения");
        System.exit(0);
    }

    /**
     * Delete the account of the player with the given username.
     *
     * @param username The username of the player.
     */
    public void deleteAccount(String username) {
        Optional<Player> player = playerDao.findByName(username);

        auditsDao.createAudit(username, DELETE_ACTION, SUCCESS);
        player.ifPresent(value -> {
            playerDao.delete(value.getName());
        });
    }

    /**
     * Display an error message for an incorrect command.
     */
    public void incorrect() {
        System.out.println("Некорректный выбор команды. Попробуйте еще раз.");
    }
}