package com.example.moviessystema1.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.example.moviessystema1.common.Constants.*;

public class DBConn {
    private static HikariDataSource dataSource;
    private DBConn() {
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initializeDataSource();}

        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    static void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL + DATABASE_NAME);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setDriverClassName(DRIVER_CLASS_NAME);
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
    }

}
