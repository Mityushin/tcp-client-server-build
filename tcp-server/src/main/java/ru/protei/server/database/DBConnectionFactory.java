package ru.protei.server.database;

public class DBConnectionFactory {
    private static DBConnectionFactory instance = null;

    private DBConnectionFactory() {}

    public static DBConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DBConnectionFactory();
        }
        return instance;
    }
}
