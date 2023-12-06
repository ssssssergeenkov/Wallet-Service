package com.ivan.wallet.liquibase;

import com.ivan.wallet.util.ConnectionManager;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * The LiquibaseManager class is responsible for running database migrations using Liquibase.
 */
public class LiquibaseManager {
    private static final LiquibaseManager LIQUIBASE_MANAGER = new LiquibaseManager();

    private static final String SQL_CREATE_SCHEMA = "CREATE SCHEMA IF NOT EXISTS migration";

    /**
     * Run the database migrations using Liquibase.
     */
    public void runMigrations() {
        try {
            Connection connection = ConnectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_SCHEMA);
            preparedStatement.execute();

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setLiquibaseSchemaName("migration");

            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();

            System.out.println("Миграции успешно выполнены!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the singleton instance of LiquibaseManager.
     *
     * @return The instance of LiquibaseManager.
     */
    public static LiquibaseManager getInstance() {
        return LIQUIBASE_MANAGER;
    }
}