package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.service.AuditWalletService;
import com.ivan.wallet.service.PlayerWalletService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
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

    AuditWalletService auditWalletService = AuditWalletService.getINSTANCE();
    PlayersDao playersDao = PlayersDao.getINSTANCE();
    Scanner scanner = new Scanner(System.in);

    /**
     * Обертка для метода showAudits класса AuditWalletUserService.
     */
    public void wrapperAudit() {
        System.out.println("admin, аудит какого пользователя хотите посмотреть?");

        List<Player> players = playersDao.findAll();

        if (!players.isEmpty()) {
            for (Player player : players) {
                System.out.print(player.getName() +  ", ");
            }
            System.out.println("\nБольше никого нет");
        } else {
            System.out.println("База данных пуста");
        }
        String username = scanner.nextLine();
        auditWalletService.showAudit(username);
    }
}