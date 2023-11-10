package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.service.TransactionService;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class WrapperTransactionService {
    private static final WrapperTransactionService INSTANCE = new WrapperTransactionService();
    public static WrapperTransactionService getINSTANCE() {
        return INSTANCE;
    }
    private WrapperTransactionService() {
    }
    TransactionService transactionService = TransactionService.getINSTANCE();

    Scanner scanner = new Scanner(System.in);

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
        transactionService.debit(player.getLoggedInUserName(), transactionId, debitAmount);
    }

    public void wrapperCredit(WalletConsole player) {
        UUID transactionId = UUID.randomUUID();
        System.out.println("Введите сумму кредитной транзакции:");
        BigDecimal creditAmount = BigDecimal.ZERO;
        try {
            creditAmount = scanner.nextBigDecimal();
        } catch (Exception e) {
            System.out.println("введите число");
        }
        transactionService.credit(player.getLoggedInUserName(), transactionId, creditAmount);
    }

    public void wrapperTransactionHistory(WalletConsole player) {
        transactionService.transactionHistory(player.getLoggedInUserName());
    }
}