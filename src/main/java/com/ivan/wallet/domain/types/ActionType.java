package com.ivan.wallet.domain.types;

/**
 * Класс, содержащий типы действий в системе для аудита
 */
public enum ActionType {
    REGISTRATION_ACTION,
    AUTHORIZATION_ACTION,
    CURRENT_PLAYER_BALANCE_ACTION,
    DEBIT_ACTION,
    CREDIT_ACTION,
    TRANSACTION_HISTORY_ACTION,
    LOGOUT_ACTION,
    EXIT_ACTION
}