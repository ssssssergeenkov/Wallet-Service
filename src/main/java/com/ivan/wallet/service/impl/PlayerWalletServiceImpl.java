package com.ivan.wallet.service.impl;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.dao.impl.AuditsDaoImpl;
import com.ivan.wallet.dao.impl.PlayersDaoImpl;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.service.AbstractUserWalletService;
import com.ivan.wallet.service.PlayerWalletService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

import static com.ivan.wallet.domain.types.ActionType.CURRENT_BALANCE_ACTION;
import static com.ivan.wallet.domain.types.ActionType.DELETE_ACTION;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;

/**
 * The PlayerWalletServiceImpl class provides various operations related to player wallet management.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerWalletServiceImpl extends AbstractUserWalletService implements PlayerWalletService {
    private static final PlayerWalletServiceImpl INSTANCE = new PlayerWalletServiceImpl();

    /**
     * Get the singleton instance of PlayerWalletServiceImpl.
     *
     * @return The instance of PlayerWalletServiceImpl.
     */
    public static PlayerWalletServiceImpl getINSTANCE() {
        return INSTANCE;
    }

    PlayersDao<String, Player> playersDaoImpl = PlayersDaoImpl.getINSTANCE();
    AuditsDao<String, Audits> auditsDaoImpl = AuditsDaoImpl.getINSTANCE();

    /**
     * Get the balance of the current player.
     *
     * @param username The username of the player.
     * @return The balance of the player.
     */
    @Override
    public BigDecimal currentPlayerBalance(String username) {
        Optional<Player> player = playersDaoImpl.findByName(username);

        Audits audits = Audits.builder()
                .playerName(username)
                .actionType(CURRENT_BALANCE_ACTION)
                .identifierType(SUCCESS)
                .build();
        auditsDaoImpl.save(audits);

        return player.get().getBalance();
    }

    /**
     * Updates the balance of a player.
     *
     * @param name    the name of the player
     * @param balance the new balance to set
     */
    @Override
    public void updateBalance(String name, BigDecimal balance) {
        playersDaoImpl.updatePlayerBalance(name, balance);
    }

    /**
     * Deletes the account of a player.
     *
     * @param username The username of the player whose account should be deleted.
     */
    public void deleteAccount(String username) {
        Optional<Player> player = playersDaoImpl.findByName(username);

        Audits audits = Audits.builder()
                .playerName(username)
                .actionType(DELETE_ACTION)
                .identifierType(SUCCESS)
                .build();
        auditsDaoImpl.save(audits);

        player.ifPresent(value -> {
            playersDaoImpl.delete(value.getName());
        });
    }
}