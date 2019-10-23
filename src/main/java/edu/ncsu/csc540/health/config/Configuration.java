package edu.ncsu.csc540.health.config;

public class Configuration {
    private DatabaseConfiguration db;

    public DatabaseConfiguration getDb() {
        return db;
    }

    public void setDb(DatabaseConfiguration db) {
        this.db = db;
    }
}
