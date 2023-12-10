package com.ivan.wallet.service.impl;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.dao.impl.AuditsDaoImpl;
import com.ivan.wallet.dao.impl.PlayersDaoImpl;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.service.AbstractSessionService;
import com.ivan.wallet.service.AdminWalletService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The AdminWalletServiceImpl class is responsible for displaying audit information for a given username.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminWalletServiceImpl extends AbstractSessionService implements AdminWalletService {
    public static final AdminWalletServiceImpl INSTANCE = new AdminWalletServiceImpl();

    /**
     * Get the singleton instance of AdminWalletServiceImpl.
     *
     * @return The instance of AdminWalletServiceImpl.
     */
    public static AdminWalletServiceImpl getINSTANCE() {
        return INSTANCE;
    }

    AuditsDao<String, Audits> auditsDaoImpl = AuditsDaoImpl.getINSTANCE();
    PlayersDao<String, Player> playersDaoImpl = PlayersDaoImpl.getINSTANCE();

    @Override
    public List<Audits> showAudit(String username) {
        return auditsDaoImpl.findByName(username);
    }

    @Override
    public List<Player> showAllPlayers() {
        return playersDaoImpl.findAll();
    }
}