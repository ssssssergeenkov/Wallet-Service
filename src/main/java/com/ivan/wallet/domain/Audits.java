package com.ivan.wallet.domain;

import com.ivan.wallet.domain.types.ActionType;
import com.ivan.wallet.domain.types.IdentifierType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс Audits представляет действие с указанными свойствами.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Audits {
    private Integer id;
    private String playerName;
    private ActionType actionType;
    private IdentifierType identifierType;
}
