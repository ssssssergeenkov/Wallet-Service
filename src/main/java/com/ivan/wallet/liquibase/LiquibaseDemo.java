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
 * The LiquibaseDemo class is responsible for running database migrations using Liquibase.
 */
public class LiquibaseDemo {
    private static final LiquibaseDemo liquibaseDemo = new LiquibaseDemo();

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
            System.out.println("провал!");
            e.printStackTrace();
        }
    }

    /**
     * Get the singleton instance of LiquibaseDemo.
     *
     * @return The instance of LiquibaseDemo.
     */
    public static LiquibaseDemo getInstance() {
        return liquibaseDemo;
    }
}