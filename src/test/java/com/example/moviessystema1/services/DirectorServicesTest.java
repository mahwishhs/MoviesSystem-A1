package com.example.moviessystema1.services;

import com.example.moviessystema1.domain.Director;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DirectorServicesTest {

    @Test
    void testGetAllDirectors_Success() throws SQLException {
        // Test data for directors
        List<Director> testDirectors = new ArrayList<>();
        testDirectors.add(new Director(2001, "John", "Doe", "1990-01-01", "American"));
        testDirectors.add(new Director(2002, "Jane", "Smith", "1992-03-15", "British"));
        testDirectors.add(new Director(2003, "Michael", "Johnson", "1985-12-10", "American"));

        // Create a mock Connection, PreparedStatement, and ResultSet
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Mock the executeQuery() method to return the mock result set
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Mock the result set to return the test data
            when(mockResultSet.next()).thenReturn(true, true, true, false); // Three rows for directors
            when(mockResultSet.getInt("director_id")).thenReturn(2001, 2002, 2003);
            when(mockResultSet.getString("first_name")).thenReturn("John", "Jane", "Michael");
            when(mockResultSet.getString("last_name")).thenReturn("Doe", "Smith", "Johnson");
            when(mockResultSet.getString("date_of_birth")).thenReturn("1990-01-01", "1992-03-15", "1985-12-10");
            when(mockResultSet.getString("nationality")).thenReturn("American", "British", "American");

            // Call the method to be tested
            List<Director> directors = DirectorServices.getAllDirectors();

            // Assert the result
            assertEquals(testDirectors.size(), directors.size());
            for (int i = 0; i < testDirectors.size(); i++) {
                Director expectedDirector = testDirectors.get(i);
                Director actualDirector = directors.get(i);

                assertEquals(expectedDirector.getDirectorId(), actualDirector.getDirectorId());
                assertEquals(expectedDirector.getFirstName(), actualDirector.getFirstName());
                assertEquals(expectedDirector.getLastName(), actualDirector.getLastName());
                assertEquals(expectedDirector.getDateOfBirth(), actualDirector.getDateOfBirth());
                assertEquals(expectedDirector.getNationality(), actualDirector.getNationality());
            }
        }
    }

    @Test
    void testCreateDirector_Success() throws SQLException {
        // Test data for the director
        Director director = new Director(2001, "John", "Doe", "1990-01-01", "American");

        // Create a mock Connection and PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Call the method to be tested
            DirectorServices.createDirector(director);

            // Verify that the correct values were set in the prepared statement and it was executed
            verify(mockPreparedStatement).setInt(1, director.getDirectorId());
            verify(mockPreparedStatement).setString(2, director.getFirstName());
            verify(mockPreparedStatement).setString(3, director.getLastName());
            verify(mockPreparedStatement).setString(4, director.getDateOfBirth());
            verify(mockPreparedStatement).setString(5, director.getNationality());
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void testUpdateDirector_Success() throws SQLException {
        // Test data for the updated director
        Director updatedDirector = new Director(2001, "John", "Smith", "1990-01-01", "American");

        // Create a mock Connection and PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Call the method to be tested
            DirectorServices.updateDirector(updatedDirector);

            // Verify that the correct values were set in the prepared statement and it was executed
            verify(mockPreparedStatement).setString(1, updatedDirector.getFirstName());
            verify(mockPreparedStatement).setString(2, updatedDirector.getLastName());
            verify(mockPreparedStatement).setString(3, updatedDirector.getDateOfBirth());
            verify(mockPreparedStatement).setString(4, updatedDirector.getNationality());
            verify(mockPreparedStatement).setInt(5, updatedDirector.getDirectorId());
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void testDeleteDirector_Success() throws SQLException {
        // Test data for the director ID to be deleted
        int directorId = 2001;

        // Create a mock Connection and PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Call the method to be tested
            DirectorServices.deleteDirector(directorId);

            // Verify that the correct value was set in the prepared statement and it was executed
            verify(mockPreparedStatement).setInt(1, directorId);
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void testGetDirectorById_Success() throws SQLException {
        // Test data for the director ID to be retrieved
        int directorId = 2001;

        // Test data for the retrieved director
        Director testDirector = new Director(2001, "John", "Doe", "1990-01-01", "American");

        // Create a mock Connection, PreparedStatement, and ResultSet
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Mock the executeQuery() method to return the mock result set
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Mock the result set to return the test data
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("director_id")).thenReturn(testDirector.getDirectorId());
            when(mockResultSet.getString("first_name")).thenReturn(testDirector.getFirstName());
            when(mockResultSet.getString("last_name")).thenReturn(testDirector.getLastName());
            when(mockResultSet.getString("date_of_birth")).thenReturn(testDirector.getDateOfBirth());
            when(mockResultSet.getString("nationality")).thenReturn(testDirector.getNationality());

            // Call the method to be tested
            Director director = DirectorServices.getDirectorById(directorId);

            // Assert the result
            assertNotNull(director);
            assertEquals(testDirector.getDirectorId(), director.getDirectorId());
            assertEquals(testDirector.getFirstName(), director.getFirstName());
            assertEquals(testDirector.getLastName(), director.getLastName());
            assertEquals(testDirector.getDateOfBirth(), director.getDateOfBirth());
            assertEquals(testDirector.getNationality(), director.getNationality());
        }
    }


    @Test
    void testGetDirectorsByName_Success() throws SQLException {
        // Test data for the director name to be searched
        String directorName = "John";

        // Test data for directors with the given name
        List<Director> testDirectors = new ArrayList<>();
        testDirectors.add(new Director(2001, "John", "Doe", "1990-01-01", "American"));
        testDirectors.add(new Director(2004, "John", "Smith", "1992-03-15", "British"));

        // Create a mock Connection, PreparedStatement, and ResultSet
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Mock the executeQuery() method to return the mock result set
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Mock the result set to return the test data
            when(mockResultSet.next()).thenReturn(true, true, false); // Two rows for directors
            when(mockResultSet.getInt("director_id")).thenReturn(2001, 2004);
            when(mockResultSet.getString("first_name")).thenReturn("John");
            when(mockResultSet.getString("last_name")).thenReturn("Doe", "Smith");
            when(mockResultSet.getString("date_of_birth")).thenReturn("1990-01-01", "1992-03-15");
            when(mockResultSet.getString("nationality")).thenReturn("American", "British");

            // Call the method to be tested
            List<Director> directors = DirectorServices.getDirectorsByName(directorName);

            // Assert the result
            assertEquals(testDirectors.size(), directors.size());
            for (int i = 0; i < testDirectors.size(); i++) {
                Director expectedDirector = testDirectors.get(i);
                Director actualDirector = directors.get(i);

                assertEquals(expectedDirector.getDirectorId(), actualDirector.getDirectorId());
                assertEquals(expectedDirector.getFirstName(), actualDirector.getFirstName());
                assertEquals(expectedDirector.getLastName(), actualDirector.getLastName());
                assertEquals(expectedDirector.getDateOfBirth(), actualDirector.getDateOfBirth());
                assertEquals(expectedDirector.getNationality(), actualDirector.getNationality());
            }
        }
    }

    @Test
    void testGetDirectorById_DirectorNotFound() throws SQLException {
        // Test data for a director ID that doesn't exist in the database
        int directorId = 9999;

        // Create a mock Connection, PreparedStatement, and ResultSet
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Mock the executeQuery() method to return the mock result set
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Mock the result set to return an empty result (no matching record found)
            when(mockResultSet.next()).thenReturn(false);

            // Call the method to be tested
            Director director = DirectorServices.getDirectorById(directorId);

            // Assert the result
            assertNull(director); // The director should not be found, so it should be null
        }
    }

    @Test
    void testGetDirectorsByName_DirectorsNotFound() throws SQLException {
        // Test data for a director name that doesn't exist in the database
        String directorName = "NonExistentDirector";

        // Create a mock Connection, PreparedStatement, and ResultSet
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Mock the executeQuery() method to return the mock result set
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Mock the result set to return an empty result (no matching record found)
            when(mockResultSet.next()).thenReturn(false);

            // Call the method to be tested
            List<Director> directors = DirectorServices.getDirectorsByName(directorName);

            // Assert the result
            assertTrue(directors.isEmpty()); // No directors should be found, so the list should be empty
        }
    }
}