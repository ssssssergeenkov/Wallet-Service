package com.ivan.wallet.service;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.dao.TransactionsDao;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.domain.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static com.ivan.wallet.domain.types.ActionType.CREDIT_ACTION;
import static com.ivan.wallet.domain.types.ActionType.DEBIT_ACTION;
import static com.ivan.wallet.domain.types.IdentifierType.FAIL;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
import static com.ivan.wallet.domain.types.TransactionType.CREDIT;
import static com.ivan.wallet.domain.types.TransactionType.DEBIT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionWalletService {
    private static final TransactionWalletService INSTANCE = new TransactionWalletService();

    public static TransactionWalletService getINSTANCE() {
        return INSTANCE;
    }

    PlayersDao playerDao = PlayersDao.getINSTANCE();
    TransactionsDao transactionsDao = TransactionsDao.getINSTANCE();
    AuditsDao auditsDao = AuditsDao.getINSTANCE();

    public void debit(String username, BigDecimal amount) {
        Player player = playerDao.findByName(username).orElse(null);

        if (player != null) {
            BigDecimal currentBalance = player.getBalance();

            if (currentBalance.compareTo(amount) >= 0) {
                player.setBalance(currentBalance.subtract(amount));
                playerDao.update(player);
                transactionsDao.createTransaction(username, DEBIT, amount, SUCCESS);
                auditsDao.createAudit(username, DEBIT_ACTION, SUCCESS);
                System.out.println("Дебетовая транзакция: " + amount + " списана со счёта игрока " + username);
            } else {
                System.out.println("Недостаточно средств для дебетовой транзакции.");
                auditsDao.createAudit(username, DEBIT_ACTION, FAIL);
            }
        } else {
            System.out.println("Игрок с именем " + username + " не найден");
        }
    }

    public void credit(String username, BigDecimal amount) {
        Player player = playerDao.findByName(username).orElse(null);

        if (player != null) {
            BigDecimal currentBalance = player.getBalance();
            player.setBalance(currentBalance.add(amount));
            playerDao.update(player);

            transactionsDao.createTransaction(username, CREDIT, amount, SUCCESS);
            auditsDao.createAudit(username, CREDIT_ACTION, SUCCESS);
            System.out.println("Кредитная транзакция: " + amount + " добавлена на счет игрока " + username);
        } else {
            System.out.println("Игрок с именем " + username + " не найден");
        }
    }

    public void showTransactionHistory(String username) {
        List<Transaction> transactionsList = transactionsDao.findAllByName(username);

        if (!transactionsList.isEmpty()) {
            System.out.println("История транзакций для игрока " + username + ":");
            System.out.println("―――――――――――――――――――――――――――――――――――――――――――――――――――――");
            System.out.printf("%-10s | %-12s | %-17s | %-14s | %s%n",
                    "ID", "Player Name", "Transaction Type", "Amount", "Identifier Type");
            System.out.println("------------------------------------------------------------------------");
            for (Transaction transaction : transactionsList) {
                System.out.printf("%-10s | %-12s | %-17s | %-14.2f | %s%n",
                        transaction.getId(),
                        transaction.getPlayerName(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getIdentifierType());
            }
            System.out.println("―――――――――――――――――――――――――――――――――――――――――――――――――――――");
        } else {
            System.out.println("У пользователя " + username + " нет аудита");
        }
    }
}