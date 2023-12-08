package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.console.WalletConsole;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.service.impl.AdminWalletServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperAdminService {
    private static final WrapperAdminService INSTANCE = new WrapperAdminService();

    public static WrapperAdminService getINSTANCE() {
        return INSTANCE;
    }

    AdminWalletServiceImpl adminWalletServiceImpl = AdminWalletServiceImpl.getINSTANCE();

    Scanner scanner = new Scanner(System.in);

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

    public void wrapperLogOut(WalletConsole logged) {
        adminWalletServiceImpl.logOut(logged);
    }

    public void wrapperExit(WalletConsole walletConsole) {
        adminWalletServiceImpl.exit(walletConsole);
    }

    public void wrapperIncorrect() {
        System.out.println("Некорректный выбор команды. Попробуйте еще раз.");
    }
}