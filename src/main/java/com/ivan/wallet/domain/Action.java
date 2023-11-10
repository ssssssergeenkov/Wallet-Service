package com.ivan.wallet.domain;

import com.ivan.wallet.domain.types.ActionType;
import com.ivan.wallet.domain.types.IdentifierType;

/**
 * Класс Action представляет действие с указанными свойствами.
 *
 * @param actionName     название действия
 * @param identifierType тип идентификатора
 */
public record Action(ActionType actionName, IdentifierType identifierType) {
}