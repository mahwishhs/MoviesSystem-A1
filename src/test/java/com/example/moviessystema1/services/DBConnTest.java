package com.example.moviessystema1.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnTest {

    @BeforeEach
    void setUp() {
        DBConn.initializeDataSource();

    }

    @AfterEach
    void tearDown() {
        DBConn.close();

    }

    @Test
    void getConnection() {
        try {
            // Try to get a database connection
            Connection connection = DBConn.getConnection();

            // If the connection is successful, the connection object should not be null
            assertNotNull(connection, "Database connection should not be null");

            // Don't forget to close the connection
            connection.close();
        } catch (SQLException e) {
            // If there is an exception, fail the test with an error message
            fail("Failed to connect to the database: " + e.getMessage());
        }
    }

}