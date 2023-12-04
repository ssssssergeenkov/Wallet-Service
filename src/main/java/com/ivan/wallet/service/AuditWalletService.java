package com.ivan.wallet.service;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.domain.Audits;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The AuditWalletService class is responsible for displaying audit information for a given username.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditWalletService {
    public static final AuditWalletService INSTANCE = new AuditWalletService();

    /**
     * Get the singleton instance of AuditWalletService.
     *
     * @return The instance of AuditWalletService.
     */
    public static AuditWalletService getINSTANCE() {
        return INSTANCE;
    }

    AuditsDao auditsDao = AuditsDao.getINSTANCE();

    /**
     * Show audit information for a given username.
     *
     * @param username The username for which to display audit information.
     */
    public void showAudit(String username) {
        List<Audits> auditsList = auditsDao.findAllByName(username);

        if (!auditsList.isEmpty()) {
            System.out.println("Аудит для пользователя " + username + ":");
            System.out.println("―――――――――――――――――――――――――――――――――――――――――――");
            System.out.printf("%-10s | %-14s | %-26s | %s%n",
                    "ID", "Player Name", "Action Type", "Identifier Type");
            System.out.println("------------------------------------------------------------------");
            for (Audits audits : auditsList) {
                System.out.printf("%-10s | %-14s | %-26s | %-14s%n",
                        audits.getId(),
                        audits.getPlayerName(),
                        audits.getActionType(),
                        audits.getIdentifierType());
            }
            System.out.println("―――――――――――――――――――――――――――――――――――――――――――");
        } else {
            System.out.println("Пользователя " + username + " не существует");
        }
    }
}