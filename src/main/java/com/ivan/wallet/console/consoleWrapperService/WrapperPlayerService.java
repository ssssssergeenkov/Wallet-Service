package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.console.WalletConsole;
import com.ivan.wallet.service.impl.PlayerWalletServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * The WrapperPlayerService class provides wrapper methods for interacting with the PlayerService.
 * It extends the AbstractWrapperSessionService class and handles player-related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperPlayerService extends AbstractWrapperSessionService {
    private static final WrapperPlayerService INSTANCE = new WrapperPlayerService();

    /**
     * Get the singleton instance of WrapperPlayerService.
     *
     * @return The instance of WrapperPlayerService.
     */
    public static WrapperPlayerService getINSTANCE() {
        return INSTANCE;
    }

    PlayerWalletServiceImpl playerWalletServiceImpl = PlayerWalletServiceImpl.getINSTANCE();

    Scanner scanner = new Scanner(System.in);

    /**
     * Wrapper method for retrieving the current balance of a player.
     *
     * @param player The player for which to retrieve the balance.
     */
    public void wrapperCurrentPlayerBalance(WalletConsole player) {
        BigDecimal balance = playerWalletServiceImpl.currentPlayerBalance(player.getLoggedInUserName());
        System.out.println("Текущий баланс игрока " + player.getLoggedInUserName() + ": " + balance);
    }

    /**
     * Wrapper method for deleting a player account.
     * Prompts the user for confirmation and calls the deleteAccount method of the PlayerWalletService.
     * If the account is deleted, updates the logged-in username in the WalletConsole.
     *
     * @param walletConsole The instance of WalletConsole.
     */
    public void wrapperDeleteAccount(WalletConsole walletConsole) {
        System.out.println("Вы уверены?");
        String answer = scanner.nextLine();

        if ("да".equals(answer) || "da".equals(answer)) {
            playerWalletServiceImpl.deleteAccount(walletConsole.getLoggedInUserName());
            System.out.println("Игрок " + walletConsole.getLoggedInUserName() + " удален");
            walletConsole.setLoggedInUserName(null);
        } else {
            System.out.println("Удаление отменено");
        }
    }
}