package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.console.WalletConsole;
import com.ivan.wallet.service.impl.PlayerWalletServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Класс WrapperPlayerService предоставляет обертки для вызова методов класса PlayerWalletServiceImpl.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperPlayerService extends AbstractWrapperSessionService {
    private static final WrapperPlayerService INSTANCE = new WrapperPlayerService();

    public static WrapperPlayerService getINSTANCE() {
        return INSTANCE;
    }

    PlayerWalletServiceImpl playerWalletServiceImpl = PlayerWalletServiceImpl.getINSTANCE();

    Scanner scanner = new Scanner(System.in);

    public void wrapperCurrentPlayerBalance(WalletConsole player) {
        BigDecimal balance = playerWalletServiceImpl.currentPlayerBalance(player.getLoggedInUserName());
        System.out.println("Текущий баланс игрока " + player.getLoggedInUserName() + ": " + balance);
    }

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