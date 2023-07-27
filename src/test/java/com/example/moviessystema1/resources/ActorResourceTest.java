package com.example.moviessystema1.resources;

import com.example.moviessystema1.domain.Actor;
import com.example.moviessystema1.services.ActorServices;
import com.example.moviessystema1.services.DBConn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.example.moviessystema1.domain.Actor;
import com.example.moviessystema1.services.ActorServices;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ActorResourceTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private ActorServices actorServices;

    @InjectMocks
    private ActorResource actorResource;

    @BeforeEach
    void setUp() throws SQLException {
        // Prepare the mock ResultSet with test data
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("actor_id")).thenReturn(1);
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Doe");
        when(resultSet.getString("date_of_birth")).thenReturn("1990-01-01");
        when(resultSet.getString("nationality")).thenReturn("USA");

        // Prepare the mock PreparedStatement
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Prepare the mock Connection and PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }



    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddActor() {
        // Create a new actor for testing
        Actor actor = new Actor(1, "John", "Doe", "1990-01-01", "American");

        // Stub the addActor method in ActorServices
        doNothing().when(actorServices).addActor(actor);

        // Call the method under test
        Response response = actorResource.addActor(actor);

        // Verify the response
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("Actor added successfully.", response.getEntity());
    }

    @Test
    void testUpdateActor() {
        // Create an updated actor for testing
        Actor updatedActor = new Actor(1, "John", "Smith", "1990-01-01", "American");

        // Stub the updateActor method in ActorServices
        doNothing().when(actorServices).updateActor(updatedActor);

        // Call the method under test
        Response response = actorResource.updateActor(1, updatedActor);

        // Verify the response
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals("Actor updated successfully.", response.getEntity());
    }

    @Test
    void testDeleteActor() {
        // Stub the deleteActor method in ActorServices
        doNothing().when(actorServices).deleteActor(1);

        // Call the method under test
        Response response = actorResource.deleteActor(1);

        // Verify the response
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals("Actor deleted successfully.", response.getEntity());
    }

    @Test
    void testGetAllActors() {
        // Create a list of actors for testing
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1, "John", "Doe", "1990-01-01", "American"));
        actors.add(new Actor(2, "Jane", "Smith", "1995-05-05", "British"));

        // Stub the getAllActors method in ActorServices
        when(ActorServices.getAllActors()).thenReturn(actors);

        // Call the method under test
        Response response = ActorResource.getAllActors();

        // Verify the response
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(actors, response.getEntity());
    }

    @Test
    void testGetActorById() throws SQLException {
        // Prepare test data
        Actor actor = new Actor(1, "John", "Doe", "1990-01-01", "USA");

        // Mock the behavior of the ActorServices.getActorById() method
        when(actorServices.getActorById(1)).thenReturn(actor);

        // Call the method under test
        Response response = actorResource.getActorById(1);

        // Verify the response status
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // Verify the response entity (actor object)
        Actor responseActor = (Actor) response.getEntity();
        assertEquals(actor, responseActor);

    }


    @Test
    void testGetActorsByName() {
        // Create a list of actors for testing
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1, "John", "Doe", "1990-01-01", "American"));
        actors.add(new Actor(2, "John", "Smith", "1995-05-05", "British"));

        // Stub the getActorsByName method in ActorServices
        when(actorServices.getActorsByName("John")).thenReturn(actors);

        // Call the method under test
        Response response = actorResource.getActorsByName("John");

        // Verify the response
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(actors, response.getEntity());
    }
}
