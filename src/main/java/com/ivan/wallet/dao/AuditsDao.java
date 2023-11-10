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
public class AuditsDao implements Dao<String, Audits>{
    private static final AuditsDao INSTANCE = new AuditsDao();

    public static AuditsDao getINSTANCE() {
        return INSTANCE;
    }

    private static final String FIND_BY_NAME_SQL = """
            SELECT id,
            player_full_name,
            action_name,
            identifierType
            FROM audits
            WHERE player_full_name = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO audits (player_full_name, action_name, identifierType) VALUES 
            (?, ?, ?)
            """;

    public List<Audits> findAllByName(String name) {
        try (Connection connection = ConnectionManager.get();
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

    @Override
    public Audits save(Audits audits) {
        try (Connection connection = ConnectionManager.get();
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

    private Audits buildAudits(ResultSet resultSet) throws SQLException {
        return Audits.builder()
                .id(resultSet.getInt("id"))
                .playerName(resultSet.getString("player_full_name"))
                .actionType(ActionType.valueOf(resultSet.getString("action_name")))
                .identifierType(IdentifierType.valueOf(resultSet.getString("identifierType")))
                .build();
    }

    public Audits createAudit(String playerName, ActionType actionType, IdentifierType identifierType) {
        Audits audits = Audits.builder()
                .playerName(playerName)
                .actionType(actionType)
                .identifierType(identifierType)
                .build();
        return save(audits);
    }
}