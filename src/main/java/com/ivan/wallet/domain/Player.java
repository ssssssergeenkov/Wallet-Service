package com.ivan.wallet.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс сущность.
 * Класс Player представляет игрока в нашем приложении.
 * Содержит информацию об имени игрока, пароле, балансе, истории транзакций и истории действий игрока (аудите).
 */
@Data
public class Player {
    private String name;
    private String password;
    private BigDecimal balance = BigDecimal.ZERO;
    private List<Transaction> transactionsHistory = new ArrayList<>();
    private List<Action> actionsHistory = new ArrayList<>();

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
    }
}