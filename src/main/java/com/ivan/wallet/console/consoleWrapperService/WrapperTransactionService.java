package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.console.WalletConsole;
import com.ivan.wallet.domain.Transaction;
import com.ivan.wallet.service.impl.TransactionWalletServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Класс WrapperTransactionService предоставляет обертки для вызова методов класса TransactionWalletServiceImpl.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperTransactionService {
    private static final WrapperTransactionService INSTANCE = new WrapperTransactionService();

    public static WrapperTransactionService getINSTANCE() {
        return INSTANCE;
    }

    TransactionWalletServiceImpl transactionWalletServiceImpl = TransactionWalletServiceImpl.getINSTANCE();

    Scanner scanner = new Scanner(System.in);

    public void wrapperDebit(WalletConsole player) {
        System.out.print("Введите сумму дебетовой транзакции: ");
        BigDecimal debitAmount = BigDecimal.ZERO;
        try {
            debitAmount = scanner.nextBigDecimal();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Очистка входного буфера
        }
        transactionWalletServiceImpl.debit(player.getLoggedInUserName(), debitAmount);
    }

    public void wrapperCredit(WalletConsole player) {
        System.out.print("Введите сумму кредитной транзакции: ");
        BigDecimal creditAmount = BigDecimal.ZERO;
        try {
            creditAmount = scanner.nextBigDecimal();
        } catch (Exception e) {
            scanner.nextLine();
        }
        transactionWalletServiceImpl.credit(player.getLoggedInUserName(), creditAmount);
    }

    public void wrapperTransactionHistory(WalletConsole player) {
        List<Transaction> transactions = transactionWalletServiceImpl.showTransactionHistory(player.getLoggedInUserName());

        System.out.println("История транзакций для игрока " + player.getLoggedInUserName() + ":");
        System.out.println("―――――――――――――――――――――――――――――――――――――――――――――――――――――");
        System.out.printf("%-10s | %-12s | %-17s | %-14s | %s%n",
                "ID", "Player Name", "Transaction Type", "Amount", "Identifier Type");
        System.out.println("------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            System.out.printf("%-10s | %-12s | %-17s | %-14.2f | %s%n",
                    transaction.getId(),
                    transaction.getPlayerName(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getIdentifierType());
        }
        System.out.println("―――――――――――――――――――――――――――――――――――――――――――――――――――――");
    }
}