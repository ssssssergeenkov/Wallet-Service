package com.ivan.wallet.service;

import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;

import java.util.List;

/**
 * The AdminWalletService interface represents a service for administrative audit operations.
 */
public interface AdminWalletService {

    /**
     * Retrieves the audit records for a specific player.
     *
     * @param username the username of the player
     * @return a list of Audit objects representing the audit records
     */
    List<Audits> showAudit(String username);

    /**
     * Retrieves all players in the system.
     *
     * @return a list of Player objects representing all players in the system
     */
    List<Player> showAllPlayers();
}