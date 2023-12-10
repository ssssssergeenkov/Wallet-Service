package com.ivan.wallet.service.impl;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.dao.impl.AuditsDaoImpl;
import com.ivan.wallet.dao.impl.PlayersDaoImpl;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.service.SecurityService;

import java.math.BigDecimal;
import java.util.Optional;

import static com.ivan.wallet.domain.types.ActionType.AUTHORIZATION_ACTION;
import static com.ivan.wallet.domain.types.ActionType.REGISTRATION_ACTION;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;

/**
 * The SecurityServiceImpl class implements the SecurityService interface and provides
 * methods for player registration and authorization.
 */
public class SecurityServiceImpl implements SecurityService {
    private static final SecurityServiceImpl INSTANCE = new SecurityServiceImpl();

    /**
     * Returns the singleton instance of the SecurityServiceImpl class.
     *
     * @return the singleton instance of the SecurityServiceImpl class
     */
    public static SecurityServiceImpl getINSTANCE() {
        return INSTANCE;
    }

    PlayersDao<String, Player> playersDaoImpl = PlayersDaoImpl.getINSTANCE();
    AuditsDao<String, Audits> auditsDaoImpl = AuditsDaoImpl.getINSTANCE();

    /**
     * Registers a new player with the specified username and password.
     *
     * @param username the username of the player
     * @param password the password of the player
     * @return the Player object representing the registered player, or null if registration fails
     */
    @Override
    public Player registration(String username, String password) {
        Optional<Player> existingPlayer = playersDaoImpl.findByName(username);

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("вы ввели пустое значение");
            return null;
        }

        if (existingPlayer.isPresent()) {
            System.out.println("Игрок с таким именем уже существует.");
            return null;
        }

        Player savedPlayer = Player.builder()
                .name(username)
                .password(password)
                .balance(BigDecimal.ZERO)
                .build();
        playersDaoImpl.save(savedPlayer);

        System.out.println("Игрок " + savedPlayer.getName() + " успешно зарегистрирован.");

        Audits audits = Audits.builder()
                .playerName(username)
                .actionType(REGISTRATION_ACTION)
                .identifierType(SUCCESS)
                .build();
        auditsDaoImpl.save(audits);
        return savedPlayer;
    }

    /**
     * Authorizes a player with the specified username and password.
     *
     * @param username the username of the player
     * @param password the password of the player
     * @return the Player object representing the authorized player, or null if authorization fails
     */
    @Override
    public Player authorization(String username, String password) {
        Optional<Player> optionalPlayer = playersDaoImpl.findByName(username);

        if (optionalPlayer.isEmpty() || !optionalPlayer.get().getPassword().equals(password)) {
            System.out.println("Неверное имя пользователя или пароль");
            return null;
        }

        System.out.println("Игрок " + username + " авторизован.");
        Audits audits = Audits.builder()
                .playerName(username)
                .actionType(AUTHORIZATION_ACTION)
                .identifierType(SUCCESS)
                .build();
        auditsDaoImpl.save(audits);
        return optionalPlayer.get();
    }
}