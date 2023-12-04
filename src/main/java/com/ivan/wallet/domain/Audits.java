package com.ivan.wallet.domain;

import com.ivan.wallet.domain.types.ActionType;
import com.ivan.wallet.domain.types.IdentifierType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Audits class represents an audit entity.
 * It contains information about an audit, including its ID, player name, action type, and identifier type.
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