package com.ivan.wallet.dao.impl;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.types.ActionType;
import com.ivan.wallet.domain.types.IdentifierType;
import com.ivan.wallet.exception.DaoException;
import com.ivan.wallet.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditsDaoImpl implements AuditsDao<String, Audits> {
    private static final AuditsDaoImpl INSTANCE = new AuditsDaoImpl();

    /**
     * Get the singleton instance of AuditsDaoImpl.
     *
     * @return The instance of AuditsDaoImpl.
     */
    public static AuditsDaoImpl getINSTANCE() {
        return INSTANCE;
    }

    private static final String FIND_BY_NAME_SQL = """
            SELECT id,
            player_full_name,
            action_name,
            identifier_type
            FROM wallet.audits
            WHERE player_full_name = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO wallet.audits (player_full_name, action_name, identifier_type) VALUES 
            (?, ?, ?)
            """;

    /**
     * Find all audits by player name.
     *
     * @param name The name of the player.
     * @return A list of audits.
     * @throws DaoException If an error occurs while executing the query.
     */
    public List<Audits> findByName(String name) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Audits> accounts = new ArrayList<>();

            while (resultSet.next()) {
                accounts.add(buildAudits(resultSet));
            }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Save an audit to the database.
     *
     * @param audits The audit to be saved.
     * @return The saved audit.
     * @throws DaoException If an error occurs while executing the query.
     */
    public Audits save(Audits audits) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, audits.getPlayerName());
            preparedStatement.setObject(2, audits.getActionType().name());
            preparedStatement.setObject(3, audits.getIdentifierType().name());

            preparedStatement.executeUpdate();
            return audits;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Build an Audits object from the ResultSet.
     *
     * @param resultSet The ResultSet containing audit data.
     * @return The built Audits object.
     * @throws SQLException If an error occurs while retrieving data from the ResultSet.
     */
    private Audits buildAudits(ResultSet resultSet) throws SQLException {
        return Audits.builder()
                .id(resultSet.getInt("id"))
                .playerName(resultSet.getString("player_full_name"))
                .actionType(ActionType.valueOf(resultSet.getString("action_name")))
                .identifierType(IdentifierType.valueOf(resultSet.getString("identifier_type")))
                .build();
    }
}