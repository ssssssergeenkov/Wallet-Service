package com.ivan.wallet.service;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.domain.types.ActionType;
import com.ivan.wallet.in.WalletConsole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import static com.ivan.wallet.domain.types.ActionType.*;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;

/**
 * Класс WalletService предоставляет сервис для работы с кошельком игроков.
 * Позволяет регистрировать и авторизовать игроков, выполнять дебетовые и кредитные транзакции,
 * просматривать историю транзакций и аудит действий игрока.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerWalletService {
    private static final PlayerWalletService INSTANCE = new PlayerWalletService();

    public static PlayerWalletService getINSTANCE() {
        return INSTANCE;
    }

    PlayersDao playerDao = PlayersDao.getINSTANCE();
    AuditsDao auditsDao = AuditsDao.getINSTANCE();

    public boolean registration(String username, String password) {
        Optional<Player> existingPlayer = playerDao.findByName(username); //вот так мы можем не делать класс qaisar service. и переименовать PlayerWalletService в сервис

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

    public boolean authorization(String username, String password) {
        Optional<Player> player = playerDao.findByName(username);

        if (player.isPresent() && player.get().getPassword() != null && player.get().getPassword().equals(password)) {
            System.out.println("Игрок " + username + " авторизован.");
            auditsDao.createAudit(username, ActionType.AUTHORIZATION_ACTION, SUCCESS);
            return true;
        } else {
            System.out.println("Ошибка авторизации. Проверьте имя пользователя и пароль."); //нужно добавить exceptions
            return false;
        }
    }

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

    public void logOut(WalletConsole walletConsole) {
        walletConsole.setLoggedInUserName(null);
        walletConsole.setLogIn(false);
    }

    public void exit() {
        System.out.println("Выход из приложения");
        System.exit(0);
    }


    public void deleteAccount(String username) {
        Optional<Player> player = playerDao.findByName(username);

        auditsDao.createAudit(username, DELETE_ACTION, SUCCESS);
        player.ifPresent(value -> {
            playerDao.delete(value.getName());
        });
    }

    public void incorrect() {
        System.out.println("Некорректный выбор команды. Попробуйте еще раз.");
    }
}