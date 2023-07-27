package com.example.moviessystema1.resources;

import com.example.moviessystema1.services.ActorServices;
import com.example.moviessystema1.services.DBConn;
import org.junit.jupiter.api.Test;

import com.example.moviessystema1.domain.Director;
import com.example.moviessystema1.services.DirectorServices;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DirectorResourceTest {

    private DirectorResource directorResource;
    private DirectorServices directorServices;
    private Connection mockConnection;

    @BeforeEach
    void setUp() {
        // Mock the static method DBConn.getConnection()
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            // Return null for DBConn.getConnection()
            mockedDBConn.when(DBConn::getConnection).thenReturn(null);
        }

        directorResource = new DirectorResource();

        directorServices = mock(DirectorServices.class);
    }

    @Test
    void testCreateDirector_Success() {
        // Test data for the director to be created
        Director director = new Director(1001, "John", "Doe", "1990-01-01", "American");

        // Mock the createDirector method to return a successful response
        // Call the createDirector method in directorResource
        Response response = directorResource.createDirector(director);

        // Assert the response status
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("Director saved successfully.", response.getEntity());
    }

    @Test
    void testUpdateDirector() {
        // Test data for an existing director
        Director existingDirector = new Director(1001, "John", "Doe", "1990-01-01", "American");
        Director updatedDirector = new Director(1001, "JohnDoe", "Updated", "1990-01-01", "American");

        Response response = directorResource.updateDirector(1001, updatedDirector);

        // Assert the response status
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals("Director updated successfully.", response.getEntity());
    }

    @Test
    void testDeleteDirector() {
        Director existingDirector = new Director(1001, "John", "Doe", "1990-01-01", "American");

        // Call the deleteDirector method in directorResource
        Response response = directorResource.deleteDirector(1001);

        // Assert the response status
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals("Director deleted successfully.", response.getEntity());
    }
}

