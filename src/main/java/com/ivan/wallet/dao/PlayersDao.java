package com.ivan.wallet.dao;

import com.ivan.wallet.domain.Player;
import com.ivan.wallet.exception.DaoException;
import com.ivan.wallet.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayersDao implements Dao<String, Player> {
    private static final PlayersDao INSTANCE = new PlayersDao();

    /**
     * Get the singleton instance of PlayersDao.
     *
     * @return The instance of PlayersDao.
     */
    public static PlayersDao getINSTANCE() {
        return INSTANCE;
    }

    private static final String DELETE_SQL = """
            DELETE FROM wallet.players
            WHERE full_name = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO wallet.players (full_name, password, balance) VALUES
            (?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE wallet.players
            SET balance = ?
            WHERE full_name = ?
            """;
    private static final String FIND_BY_NAME_SQL = """
            SELECT id,
            full_name,
            password,
            balance
            FROM wallet.players
            WHERE full_name = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id,
            full_name,
            password,
            balance
            FROM wallet.players
            """;

    /**
     * Find a player by name.
     *
     * @param name The name of the player.
     * @return An optional Player object.
     * @throws DaoException If an error occurs while executing the query.
     */
    public Optional<Player> findByName(String name) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            Player player = null;
            if (resultSet.next()) {
                player = buildPlayer(resultSet);
            }
            return Optional.ofNullable(player);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Find all players.
     *
     * @return A list of players.
     * @throws DaoException If an error occurs while executing the query.
     */
    public List<Player> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Player> players = new ArrayList<>();
            while (resultSet.next()) {
                players.add(buildPlayer(resultSet));
            }
            return players;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    /**
     * Update a player's balance.
     *
     * @param player The player object with updated balance.
     * @throws DaoException If an error occurs while executing the query.
     */
    public void update(Player player) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setBigDecimal(1, player.getBalance());
            preparedStatement.setString(2, player.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Save a player to the database.
     *
     * @param player The player to be saved.
     * @return The saved player.
     * @throws DaoException If an error occurs while executing the query.
     */
    @Override
    public Player save(Player player) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, player.getName());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.setBigDecimal(3, player.getBalance());

            preparedStatement.executeUpdate();
            return player;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Delete a player by name.
     *
     * @param name The name of the player to be deleted.
     * @return True if the deletion is successful, false otherwise.
     * @throws DaoException If an error occurs while executing the query.
     */
    public boolean delete(String name) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setString(1, name);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Build a Player object from the ResultSet.
     *
     * @param resultSet The ResultSet containing player data.
     * @return The built Player object.
     * @throws SQLException If an error occurs while retrieving data from the ResultSet.
     */
    private Player buildPlayer(ResultSet resultSet) throws SQLException {
        return new Player(
                resultSet.getInt("id"),
                resultSet.getString("full_name"),
                resultSet.getString("password"),
                resultSet.getBigDecimal("balance")
        );
    }

    /**
     * Create a new player and save it to the database.
     *
     * @param username The username of the player.
     * @param password The password of the player.
     * @return The created and saved Player object.
     */
    public Player createUser(String username, String password) {
        Player newPlayer = Player.builder()
                .name(username)
                .password(password)
                .balance(BigDecimal.ZERO) //можно попробовать через лямбду как в TicketService
                .build();
        return save(newPlayer);
    }
}