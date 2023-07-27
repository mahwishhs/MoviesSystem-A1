package com.example.moviessystema1.resources;

import com.example.moviessystema1.domain.Actor;
import com.example.moviessystema1.services.ActorServices;
import com.example.moviessystema1.services.DBConn;
import com.example.moviessystema1.services.MovieServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActorResourceTest {

    private ActorResource actorResource;
    private ActorServices actorServices;
    private Connection mockConnection;

    @Test
    void testAddActor_Success() {
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(null);
        }
        actorResource = new ActorResource();
        actorServices = mock(ActorServices.class);
        // Test data for the actor to be added
        Actor actor = new Actor(1001, "John", "Doe", "1990-01-01", "American");

        // Call the addActor method in actorResource
        Response response = actorResource.addActor(actor);

        // Assert the response status
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("Actor added successfully.", response.getEntity());
    }
    @Test
    void testUpdateActor_Success() {
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(null);
        }
        actorResource = new ActorResource();
        actorServices = mock(ActorServices.class);
        // Test data for the updated actor
        Actor updatedActor = new Actor(1001, "John", "Doe", "1990-01-01", "American");

        // Call the updateActor method in actorResource
        Response response = actorResource.updateActor(1001, updatedActor);

        // Assert the response status
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals("Actor updated successfully.", response.getEntity());
    }

    @Test
    void testDeleteActor_Success() {
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(null);
        }
        actorResource = new ActorResource();
        actorServices = mock(ActorServices.class);
        // Call the deleteActor method in actorResource
        Response response = actorResource.deleteActor(1001);

        // Assert the response status
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals("Actor deleted successfully.", response.getEntity());
    }

}
