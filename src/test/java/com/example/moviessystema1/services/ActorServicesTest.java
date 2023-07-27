package com.example.moviessystema1.services;

import com.example.moviessystema1.domain.Actor;

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

class ActorServicesTest {

    @Test
    void addActor() throws SQLException {
            // Test data for the actor
            Actor actor = new Actor(1001, "John", "Doe", "1990-01-01", "American");

            // Create a mock Connection and PreparedStatement
            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

            // Mock the static method DBConn.getConnection() to return the mock connection
            try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
                mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

                // Mock the prepareStatement() method to return the mock prepared statement
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

                // Call the method to be tested
                ActorServices.addActor(actor);

                // Verify that the executeUpdate() method is called
                verify(mockPreparedStatement).executeUpdate();
            }
        }

    @Test
    void testUpdateActor_Success() throws SQLException {
        // Test data for the updated actor
        Actor updatedActor = new Actor(1001, "John", "Doe", "1990-01-01", "American");

        // Create a mock Connection and PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Call the method to be tested
            ActorServices.updateActor(updatedActor);

            // Verify that the executeUpdate() method is called
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void testDeleteActor_Success() throws SQLException {
        // Test data for the actorId to be deleted
        int actorId = 1001;

        // Create a mock Connection and PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            // Mock the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Call the method to be tested
            ActorServices.deleteActor(actorId);

            // Verify that the executeUpdate() method is called
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void testGetActorById_Success() throws SQLException {
        // Test data for the actorId to retrieve
        int actorId = 1001;
        String firstName = "John";
        String lastName = "Doe";
        String dateOfBirth = "1990-01-01";
        String nationality = "American";

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
            when(mockResultSet.getInt("actor_id")).thenReturn(actorId);
            when(mockResultSet.getString("first_name")).thenReturn(firstName);
            when(mockResultSet.getString("last_name")).thenReturn(lastName);
            when(mockResultSet.getString("date_of_birth")).thenReturn(dateOfBirth);
            when(mockResultSet.getString("nationality")).thenReturn(nationality);

            // Call the method to be tested
            Actor actor = ActorServices.getActorById(actorId);

            // Assert the result
            assertNotNull(actor);
            assertEquals(actorId, actor.getActorId());
            assertEquals(firstName, actor.getFirstName());
            assertEquals(lastName, actor.getLastName());
            assertEquals(dateOfBirth, actor.getDateOfBirth());
            assertEquals(nationality, actor.getNationality());
        }
    }

    @Test
    void testGetActorsByName_Success() throws SQLException {
        // Test data for the actorName to search for
        String actorName = "John";

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
            when(mockResultSet.next()).thenReturn(true).thenReturn(false); // One row for the actor
            when(mockResultSet.getInt("actor_id")).thenReturn(1001);
            when(mockResultSet.getString("first_name")).thenReturn("John");
            when(mockResultSet.getString("last_name")).thenReturn("Doe");
            when(mockResultSet.getString("date_of_birth")).thenReturn("1990-01-01");
            when(mockResultSet.getString("nationality")).thenReturn("American");

            // Call the method to be tested
            List<Actor> actors = ActorServices.getActorsByName(actorName);

            // Assert the result
            assertNotNull(actors);
            assertEquals(1, actors.size());

            Actor actor = actors.get(0);
            assertEquals(1001, actor.getActorId());
            assertEquals("John", actor.getFirstName());
            assertEquals("Doe", actor.getLastName());
            assertEquals("1990-01-01", actor.getDateOfBirth());
            assertEquals("American", actor.getNationality());
        }
    }

    @Test
    void testGetAllActors_Success() throws SQLException {
        // Test data for actors
        List<Actor> testActors = new ArrayList<>();
        testActors.add(new Actor(1001, "John", "Doe", "1990-01-01", "American"));
        testActors.add(new Actor(1002, "Jane", "Smith", "1992-03-15", "British"));
        testActors.add(new Actor(1003, "Michael", "Johnson", "1985-12-10", "American"));

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
            when(mockResultSet.next()).thenReturn(true, true, true, false); // Three rows for actors
            when(mockResultSet.getInt("actor_id")).thenReturn(1001, 1002, 1003);
            when(mockResultSet.getString("first_name")).thenReturn("John", "Jane", "Michael");
            when(mockResultSet.getString("last_name")).thenReturn("Doe", "Smith", "Johnson");
            when(mockResultSet.getString("date_of_birth")).thenReturn("1990-01-01", "1992-03-15", "1985-12-10");
            when(mockResultSet.getString("nationality")).thenReturn("American", "British", "American");

            // Call the method to be tested
            List<Actor> actors = ActorServices.getAllActors();

            // Assert the result
            assertEquals(testActors.size(), actors.size());
            for (int i = 0; i < testActors.size(); i++) {
                Actor expectedActor = testActors.get(i);
                Actor actualActor = actors.get(i);

                assertEquals(expectedActor.getActorId(), actualActor.getActorId());
                assertEquals(expectedActor.getFirstName(), actualActor.getFirstName());
                assertEquals(expectedActor.getLastName(), actualActor.getLastName());
                assertEquals(expectedActor.getDateOfBirth(), actualActor.getDateOfBirth());
                assertEquals(expectedActor.getNationality(), actualActor.getNationality());
            }
        }
    }

    @Test
    void testGetActorById_ActorNotFound() throws SQLException {
        // Test data for an actor ID that doesn't exist in the database
        int actorId = 9999;

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
            Actor actor = ActorServices.getActorById(actorId);

            // Assert the result
            assertNull(actor); // The actor should not be found, so it should be null
        }
    }

    @Test
    void testGetActorsByName_ActorsNotFound() throws SQLException {
        // Test data for an actor name that doesn't exist in the database
        String actorName = "NonExistentActor";

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
            List<Actor> actors = ActorServices.getActorsByName(actorName);

            // Assert the result
            assertTrue(actors.isEmpty()); // No actors should be found, so the list should be empty
        }
    }

    @Test
    void testGetAllActors_NoActorsInDatabase() throws SQLException {
        // Test data for no actors in the database
        List<Actor> testActors = new ArrayList<>();

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

            // Mock the result set to return an empty result (no actors in the database)
            when(mockResultSet.next()).thenReturn(false);

            // Call the method to be tested
            List<Actor> actors = ActorServices.getAllActors();

            // Assert the result
            assertTrue(actors.isEmpty()); // The list should be empty as there are no actors in the database
        }
    }


}