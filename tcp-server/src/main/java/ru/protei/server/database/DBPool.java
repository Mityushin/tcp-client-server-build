package ru.protei.server.database;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBPool {
    private static final Logger log = Logger.getLogger(DBPool.class);

    private static final String JDBC_DB_URL = "jdbc:h2:mem:";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    private Connection connection;
    private static DBPool instance;

    private DBPool() {}

    public static DBPool getInstance() {
        if (instance == null) {
            instance = new DBPool();
        }
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASSWORD);
            } catch (SQLException e) {
                log.fatal("Can't connect to database", e);
            }
        }
        return connection;
    }
}
