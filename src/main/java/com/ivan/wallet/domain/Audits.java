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
    /**
     * The unique identifier of the audit record
     */
    private Integer id;

    /**
     * The full name of the player associated with the audit
     */
    private String playerName;

    /**
     * Type of action to audit, such as logging
     */
    private ActionType actionType;

    /**
     * The type of audit, such as success or failure
     */
    private IdentifierType identifierType;
}