package com.ivan.wallet.dao;

import com.ivan.wallet.domain.Transaction;
import com.ivan.wallet.domain.types.IdentifierType;
import com.ivan.wallet.domain.types.TransactionType;
import com.ivan.wallet.exception.DaoException;
import com.ivan.wallet.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The TransactionsDao class is responsible for interacting with the database
 * and performing CRUD operations on the "transactions" table.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionsDao implements Dao<String, Transaction> {
    public static final TransactionsDao INSTANCE = new TransactionsDao();

    /**
     * Get the singleton instance of TransactionsDao.
     *
     * @return The instance of TransactionsDao.
     */
    public static TransactionsDao getINSTANCE() {
        return INSTANCE;
    }

    private static final String FIND_BY_NAME_SQL = """
            SELECT id,
            player_full_name,
            type,
            amount,
            identifier_type
            FROM wallet.transactions
            WHERE player_full_name = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO wallet.transactions (player_full_name, type, amount, identifier_type) VALUES 
            (?,?,?,?)
            """;

    /**
     * Find all transactions by player name.
     *
     * @param name The name of the player.
     * @return A list of transactions.
     * @throws DaoException If an error occurs while executing the query.
     */
    public List<Transaction> findAllByName(String name) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                transactions.add(buildTransaction(resultSet));
            }
            return transactions;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Save a transaction to the database.
     *
     * @param transaction The transaction to be saved.
     * @return The saved transaction.
     * @throws DaoException If an error occurs while executing the query.
     */
    @Override
    public Transaction save(Transaction transaction) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, transaction.getPlayerName()); //transaction.getPlayerName() и меняем тип с Player на String. или просто делает dto
            preparedStatement.setObject(2, transaction.getType().name());
            preparedStatement.setBigDecimal(3, transaction.getAmount());
            preparedStatement.setObject(4, transaction.getIdentifierType().name());

            preparedStatement.executeUpdate();
            return transaction;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Build a Transaction object from the ResultSet.
     *
     * @param resultSet The ResultSet containing transaction data.
     * @return The built Transaction object.
     * @throws SQLException If an error occurs while retrieving data from the ResultSet.
     */
    private Transaction buildTransaction(ResultSet resultSet) throws SQLException {
        return Transaction.builder()
                .id(resultSet.getInt("id"))
                .playerName(resultSet.getString("player_full_name"))
                .type(TransactionType.valueOf(resultSet.getString("type")))
                .amount(resultSet.getBigDecimal("amount"))
                .identifierType(IdentifierType.valueOf(resultSet.getString("identifier_type")))
                .build();
    }

    /**
     * Create a new transaction and save it to the database.
     *
     * @param playerName     The name of the player.
     * @param type           The type of the transaction.
     * @param amount         The amount of the transaction.
     * @param identifierType The identifier type of the transaction.
     * @return The created and saved Transaction object.
     */
    public Transaction createTransaction(String playerName, TransactionType type, BigDecimal amount, IdentifierType identifierType) {
        Transaction transaction = Transaction.builder()
                .playerName(playerName)
                .type(type)
                .amount(amount)
                .identifierType(identifierType)
                .build();
        return save(transaction);
    }
}