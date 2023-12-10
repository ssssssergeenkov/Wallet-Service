package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.service.impl.AdminWalletServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Scanner;

/**
 * The WrapperAdminService class provides wrapper methods for interacting with the AdminService.
 * It extends the AbstractWrapperSessionService class and handles admin-related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperAdminService extends AbstractWrapperSessionService {
    private static final WrapperAdminService INSTANCE = new WrapperAdminService();

    /**
     * Get the singleton instance of WrapperAdminService.
     *
     * @return The instance of WrapperAdminService.
     */
    public static WrapperAdminService getINSTANCE() {
        return INSTANCE;
    }

    AdminWalletServiceImpl adminWalletServiceImpl = AdminWalletServiceImpl.getINSTANCE();

    Scanner scanner = new Scanner(System.in);

    /**
     * Wrapper method for displaying audit records for a specific user.
     * Prompts the admin to enter the username of the user for which to view the audit records.
     * Calls the showAudit method of the AdminWalletService and displays the audit records.
     */
    public void wrapperAudit() {
        System.out.println("admin, аудит какого пользователя хотите посмотреть?");
        wrapperShowAllPlayers();

        String username = scanner.nextLine();
        List<Audits> auditsList = adminWalletServiceImpl.showAudit(username);

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
    }

    /**
     * Wrapper method for displaying all players.
     * Calls the showAllPlayers method of the AdminWalletService and displays the list of players.
     */
    public void wrapperShowAllPlayers() {
        List<Player> players = adminWalletServiceImpl.showAllPlayers();
        if (!players.isEmpty()) {
            for (Player player : players) {
                System.out.print(player.getName() + ", ");
            }
            System.out.println("\nБольше никого нет, выбирай");
        } else {
            System.out.println("База данных пуста");
        }
    }
}