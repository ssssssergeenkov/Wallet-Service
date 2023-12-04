package com.ivan.wallet.dao;

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
public class AuditsDao implements Dao<String, Audits> {
    private static final AuditsDao INSTANCE = new AuditsDao();

    /**
     * Get the singleton instance of AuditsDao.
     *
     * @return The instance of AuditsDao.
     */
    public static AuditsDao getINSTANCE() {
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
    public List<Audits> findAllByName(String name) {
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
    @Override
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

    /**
     * Create a new audit and save it to the database.
     *
     * @param playerName     The name of the player.
     * @param actionType     The type of action.
     * @param identifierType The type of identifier.
     * @return The created and saved Audits object.
     */
    public Audits createAudit(String playerName, ActionType actionType, IdentifierType identifierType) {
        Audits audits = Audits.builder()
                .playerName(playerName)
                .actionType(actionType)
                .identifierType(identifierType)
                .build();
        return save(audits);
    }
}