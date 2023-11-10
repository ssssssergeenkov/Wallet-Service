package com.ivan.wallet.service;

import com.ivan.wallet.domain.Action;
import com.ivan.wallet.domain.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс AuditWalletUserService предоставляет методы для отображения аудита транзакций игроков.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditWalletUserService {
    private static final AuditWalletUserService INSTANCE = new AuditWalletUserService();
    PlayerWalletService walletService = PlayerWalletService.getINSTANCE();

    public static AuditWalletUserService getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Метод для отображения аудита транзакций игрока.
     *
     * @param username - имя игрока
     */
    public void showAudits(String username) {
        if (walletService.getPlayers().containsKey(username)) {
            Player player = walletService.getPlayers().get(username);
            List<Action> actions = player.getActionsHistory();

            if (actions != null) {
                if (actions.isEmpty()) {
                    System.out.println("У игрока " + username + " нет аудита транзакций.");
                } else {
                    System.out.println("Аудит операций для игрока " + username + ":");
                    for (Action action : actions) {
                        System.out.println("Операция: " + action.actionName() + " | " + action.identifierType());
                    }
                }
            }
        } else {
            System.out.println("Пользователь " + username + " не найден");
        }
    }
}