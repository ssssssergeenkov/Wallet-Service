package com.ivan.wallet.service;

import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;

import java.util.List;

/**
 * The AdminWalletService interface represents a service for administrative audit operations.
 */
public interface AdminWalletService {

    List<Audits> showAudit(String username);

    List<Player> showAllPlayers();
}
