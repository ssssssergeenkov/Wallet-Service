package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.service.TransactionWalletService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

/**
 * Класс WrapperTransactionService предоставляет обертки для вызова методов класса TransactionWalletService.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperTransactionService {
    private static final WrapperTransactionService INSTANCE = new WrapperTransactionService();
    public static WrapperTransactionService getINSTANCE() {
        return INSTANCE;
    }
    TransactionWalletService transactionWalletService = TransactionWalletService.getINSTANCE();

    Scanner scanner = new Scanner(System.in);

    /**
     * Обертка для метода debit класса TransactionWalletService.
     *
     * @param player - экземпляр класса WalletConsole
     */
    public void wrapperDebit(WalletConsole player) {
        UUID transactionId = UUID.randomUUID();
        System.out.println("Введите сумму дебетовой транзакции:");
        BigDecimal debitAmount = BigDecimal.ZERO;
        try {
            debitAmount = scanner.nextBigDecimal();
        } catch (InputMismatchException e) {
            System.out.println("Введите число");
            scanner.nextLine(); // Очистка входного буфера
        }
        transactionWalletService.debit(player.getLoggedInUserName(), transactionId, debitAmount);
    }

    /**
     * Обертка для метода credit класса TransactionWalletService.
     *
     * @param player - экземпляр класса WalletConsole
     */
    public void wrapperCredit(WalletConsole player) {
        UUID transactionId = UUID.randomUUID();
        System.out.println("Введите сумму кредитной транзакции:");
        BigDecimal creditAmount = BigDecimal.ZERO;
        try {
            creditAmount = scanner.nextBigDecimal();
        } catch (Exception e) {
            System.out.println("введите число");
        }
        transactionWalletService.credit(player.getLoggedInUserName(), transactionId, creditAmount);
    }

    /**
     * Обертка для метода transactionHistory класса TransactionWalletService.
     *
     * @param player - экземпляр класса WalletConsole
     */
    public void wrapperTransactionHistory(WalletConsole player) {
        transactionWalletService.transactionHistory(player.getLoggedInUserName());
    }
}