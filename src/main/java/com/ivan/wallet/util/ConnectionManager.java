package com.ivan.wallet.util;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The ConnectionManager class is a utility class that manages database connections.
 */
@UtilityClass
public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    public static final String DRIVER_KEY = "db.driver";

    static {
        loadDriver();
    }

    /**
     * Load the database driver.
     *
     * @throws RuntimeException If the driver class is not found.
     */
    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a database connection.
     *
     * @return The database connection.
     * @throws RuntimeException If an error occurs while getting the connection.
     */
    public static Connection getConnection() {
        try {

            Class.forName(PropertiesUtil.get(DRIVER_KEY));

            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get a database connection.", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}