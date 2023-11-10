package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.service.AuditWalletUserService;
import com.ivan.wallet.service.PlayerWalletService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Scanner;

/**
 * Класс WrapperAuditService предоставляет обертки для вызова методов класса AuditWalletUserService.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperAuditService {
    private static final WrapperAuditService INSTANCE = new WrapperAuditService();
    public static WrapperAuditService getINSTANCE() {
        return INSTANCE;
    }

    PlayerWalletService walletService = PlayerWalletService.getINSTANCE();
    AuditWalletUserService auditWalletUserService = AuditWalletUserService.getINSTANCE();

    Scanner scanner = new Scanner(System.in);

    /**
     * Обертка для метода showAudits класса AuditWalletUserService.
     */
    public void wrapperAudit() {
        System.out.println("admin, аудит какого пользователя хотите посмотреть?");
        for (String users : walletService.getPlayers().keySet()) {
            System.out.println("---" + users + "---");
        }
        String username = scanner.nextLine();
        auditWalletUserService.showAudits(username);
    }
}