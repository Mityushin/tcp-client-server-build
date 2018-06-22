package ru.protei.server.database;

public class DBPool {
    private static DBPool instance = null;

    private DBPool() {}

    public static DBPool getInstance() {
        if (instance == null) {
            instance = new DBPool();
        }
        return instance;
    }
}
