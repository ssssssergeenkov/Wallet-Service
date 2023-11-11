package com.ivan.wallet.service.wrapperService;

import com.ivan.wallet.in.WalletConsole;
import com.ivan.wallet.service.TransactionWalletService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

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

    public void wrapperDebit(WalletConsole player) {
        System.out.print("Введите сумму дебетовой транзакции: ");
        BigDecimal debitAmount = BigDecimal.ZERO;
        try {
            debitAmount = scanner.nextBigDecimal();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Очистка входного буфера
        }
        transactionWalletService.debit(player.getLoggedInUserName(), debitAmount);
    }

    public void wrapperCredit(WalletConsole player) {
        System.out.print("Введите сумму кредитной транзакции: ");
        BigDecimal creditAmount = BigDecimal.ZERO;
        try {
            creditAmount = scanner.nextBigDecimal();
        } catch (Exception e) {
            scanner.nextLine();
        }
        transactionWalletService.credit(player.getLoggedInUserName(), creditAmount);
    }

    public void wrapperTransactionHistory(WalletConsole player) {// тут с коллекциями работаь будем
        transactionWalletService.showTransactionHistory(player.getLoggedInUserName());// та же хуйня что и истории аудита
    }
}
