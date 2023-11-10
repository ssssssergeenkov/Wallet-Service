package com.ivan.wallet.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс сущность.
 * Класс Player представляет игрока в нашем приложении.
 * Содержит информацию о имени игрока, пароле, балансе, истории транзакций и истории действий игрока (аудите).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private String name;
    private String password;
    private BigDecimal balance = BigDecimal.ZERO;
}