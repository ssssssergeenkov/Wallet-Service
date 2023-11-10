package com.ivan.wallet.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс сущность.
 * Класс Player представляет игрока в нашем приложении.
 * Содержит информацию о имени игрока, пароле, балансе, истории транзакций и истории действий игрока (аудите).
 */
@Data
public class Player {
    private String name;
    private char[] password;
    private BigDecimal balance = BigDecimal.ZERO;
    private List<Transaction> transactionsHistory = new ArrayList<>();
    private List<Action> actionsHistory = new ArrayList<>();

    public Player(String name, char[] password) {
        this.name = name;
        this.password = password;
    }
}