package com.ivan.wallet.model;

import com.ivan.wallet.model.types.ActionType;
import com.ivan.wallet.model.types.IdentifierType;

/**
 * Класс Action представляет действие с указанными свойствами.
 *
 * @param actionName     название действия
 * @param identifierType тип идентификатора
 */
public record Action(ActionType actionName, IdentifierType identifierType) {
}