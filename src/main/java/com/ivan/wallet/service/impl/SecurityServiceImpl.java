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

public class SecurityServiceImpl implements SecurityService {
    private static final SecurityServiceImpl INSTANCE = new SecurityServiceImpl();

    public static SecurityServiceImpl getINSTANCE() {
        return INSTANCE;
    }

    PlayersDao<String, Player> playersDaoImpl = PlayersDaoImpl.getINSTANCE();
    AuditsDao<String, Audits> auditsDaoImpl = AuditsDaoImpl.getINSTANCE();

    @Override
    public Player registration(String username, String password) {
        Optional<Player> existingPlayer = playersDaoImpl.findByName(username);

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("вы ввели пустое значение");
            return null;
        }

        if (existingPlayer.isPresent()) {
            System.out.println("The player with this login already exists.");
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

    @Override
    public Player authorization(String username, String password) {
        Optional<Player> optionalPlayer = playersDaoImpl.findByName(username);

        if (optionalPlayer.isEmpty() || !optionalPlayer.get().getPassword().equals(password)) {
            System.out.println("The username or password you entered is incorrect");
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
