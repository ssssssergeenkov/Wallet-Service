package com.ivan.wallet.service;

import com.ivan.wallet.domain.Action;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.domain.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.ivan.wallet.domain.types.ActionType.*;
import static com.ivan.wallet.domain.types.IdentifierType.FAIL;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
import static com.ivan.wallet.domain.types.TransactionType.CREDIT;
import static com.ivan.wallet.domain.types.TransactionType.DEBIT;

/**
 * Класс TransactionWalletService предоставляет методы для выполнения транзакций со счетами игроков.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionWalletService {
    private static final TransactionWalletService INSTANCE = new TransactionWalletService();
    PlayerWalletService walletService = PlayerWalletService.getINSTANCE();
    public static TransactionWalletService getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Метод для списания средств с баланса игрока.
     *
     * @param username - имя игрока
     * @param transactionId - идентификатор транзакции
     * @param amount - сумма списания
     */
    public void debit(String username, UUID transactionId, BigDecimal amount) {
        if (walletService.getPlayers().containsKey(username)) {
            Player player = walletService.getPlayers().get(username);
            BigDecimal currentBalance = player.getBalance();
            Action action;

            if (currentBalance.compareTo(amount) >= 0) {
                Transaction transaction = new Transaction(transactionId, DEBIT, amount); //идентификатор транзакции
                player.setBalance(currentBalance.subtract(amount));

                action = new Action(DEBIT_ACTION, SUCCESS);
                player.getActionsHistory().add(action);

                player.getTransactionsHistory().add(transaction);
                System.out.println("Дебетовая транзакция: " + amount + " списана со счёта игрока " + username);
            } else {
                System.out.println("Недостаточно средств для дебетовой транзакции.");
                action = new Action(DEBIT_ACTION, FAIL);
                player.getActionsHistory().add(action);
            }
        } else {
            System.out.println("Игрок с именем " + username + " не найден");
        }
    }

    /**
     * Зачисляет указанную сумму на баланс игрока.
     *
     * @param username      имя игрока
     * @param transactionId идентификатор транзакции
     * @param amount        сумма зачисления
     */
    public void credit(String username, UUID transactionId, BigDecimal amount) {
        Action action;
        Player player;
        if (walletService.getPlayers().containsKey(username)) {
            player = walletService.getPlayers().get(username);
            BigDecimal currentBalance = player.getBalance();
            player.setBalance(currentBalance.add(amount));

            Transaction transaction = new Transaction(transactionId, CREDIT, amount);
            player.getTransactionsHistory().add(transaction);

            action = new Action(CREDIT_ACTION, SUCCESS);
            player.getActionsHistory().add(action);
            System.out.println("Кредитная транзакция: " + amount + " добавлена на счет игрока " + username);
        } else {
            System.out.println("Игрока " + username + " не существует");
        }
    }

    /**
     * Выводит историю транзакций для указанного игрока.
     *
     * @param username имя игрока
     */
    public void transactionHistory(String username) {
        if (walletService.getPlayers().containsKey(username)) {
            Action action;
            Player player = walletService.getPlayers().get(username);
            List<Transaction> history = player.getTransactionsHistory();
            if (history != null) { // Проверка на null перед вызовом isEmpty()
                action = new Action(TRANSACTION_HISTORY_ACTION, SUCCESS);
                player.getActionsHistory().add(action);
                if (history.isEmpty()) {
                    System.out.println("У игрока " + username + " нет истории транзакций.");
                    action = new Action(TRANSACTION_HISTORY_ACTION, FAIL);
                    player.getActionsHistory().add(action);
                } else {
                    System.out.println("История транзакций для игрока " + username + ":");
                    for (Transaction transaction : history) {
                        System.out.println("Идентификатор транзакции: " + transaction.id() + " | " + transaction.type() + " | " + transaction.amount() + " | " + action.identifierType());
                    }
                }
            } else {
                System.out.println("История транзакций игрока " + username + " не инициализирована.");
            }
        } else {
            System.out.println("Пользователь " + username + " не найден");
        }
    }
}