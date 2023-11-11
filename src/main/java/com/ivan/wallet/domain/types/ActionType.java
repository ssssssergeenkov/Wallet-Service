package com.ivan.wallet.domain.types;

/**
 * Класс, содержащий типы операций для аудита
 */
public enum ActionType {
    REGISTRATION_ACTION,
    AUTHORIZATION_ACTION,
    CURRENT_BALANCE_ACTION,
    DEBIT_ACTION,
    CREDIT_ACTION,
    TRANSACTION_HISTORY_ACTION,
    LOG_OUT_ACTION,
    EXIT_ACTION,
    DELETE_ACTION
}