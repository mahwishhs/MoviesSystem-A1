package com.example.moviessystema1.resources;

import com.example.moviessystema1.domain.Movie;
import com.example.moviessystema1.services.DBConn;
import com.example.moviessystema1.services.MovieServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class MoviesResourceTest {
    private MoviesResource moviesResource;
    private MovieServices movieServices;
    private Connection mockConnection;

    @BeforeEach
    void setUp() {

    }


    @Test
    void testAddMovie_Success() {
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(null);
        }
        moviesResource = new MoviesResource();
        movieServices = mock(MovieServices.class);


        // Test data for a new movie
        Movie newMovie = new Movie(4, "New Movie", "Release Date 4", 140, "Plot Summary 4",
                "Language 4", 1200000.00, 2200000.00);

        // Mock the behavior of MovieServices.addMovie()
        doNothing().when(movieServices).addMovie(newMovie);

        // Call the addMovie method in moviesResource
        Response response = moviesResource.addMovie(newMovie);

        // Assert the response status
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("Movie saved successfully.", response.getEntity());
    }

    @Test
    void testDeleteMovie_Success() {

        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(null);
        }
        moviesResource = new MoviesResource();
        movieServices = mock(MovieServices.class);
        // Test data for an existing movie
        int movieId = 1;

        // Mock the behavior of MovieServices.deleteMovie()
        doNothing().when(movieServices).deleteMovie(movieId);

        // Call the deleteMovie method in moviesResource
        Response response = moviesResource.deleteMovie(movieId);

        // Assert the response status
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals("Movie deleted successfully.", response.getEntity());
    }

    @Test
    void getAllMovies(){
        moviesResource = new MoviesResource();
        movieServices = new MovieServices();
        Response response = moviesResource.getAllMovies();

        // Assert the response status
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // Assert the response entity (movies)
        List<Movie> responseMovies = (List<Movie>) response.getEntity();
        // Add further assertions based on your test data and implementation
    }

//    //------------------
//    @Test
//    void testGetMovieById_Success() {
//        moviesResource = new MoviesResource();
//        movieServices = new MovieServices();
//
//        int testMovieId = 192;
//
//        Movie testMovie = new Movie(testMovieId, "Test Movie", "2023-07-27", 120, "Test plot summary", "English", 1000000.0, 2000000.0);
//        testMovie.addGenre("Action"); // Use addGenre to add genres
//        movieServices.addMovie(testMovie);
//
//        Response response = moviesResource.getMovieById(testMovieId);
//
//        // Assert the response status
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//
//        // Assert the response entity (movie)
//        Movie responseMovie = (Movie) response.getEntity();
//        assertEquals(testMovie, responseMovie);
//
//    }
//
//    @Test
//    void testGetMovieById_NotFound() {
//        moviesResource = new MoviesResource();
//        movieServices = new MovieServices();
//        // Mock the behavior of MovieServices.getMovieById() to return null, simulating not found
//        when(movieServices.getMovieById(1)).thenReturn(null);
//
//        // Call the getMovieById method in moviesResource with a non-existent ID
//        Response response = moviesResource.getMovieById(1);
//
//        // Assert the response status
//        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
//
//        // Assert the response entity (error message)
//        String errorMessage = (String) response.getEntity();
//        assertEquals("Movie not found with ID: 1", errorMessage);
//    }
//
//    @Test
//    void testUpdateMovie_Success() {
//        moviesResource = new MoviesResource();
//        movieServices = new MovieServices();
//        // Create a test movie with a known ID
//        Movie testMovie = new Movie(1, "Test Movie", "2023-07-27", 120, "Test plot summary",
//                "English", 1000000.00, 2000000.00);
//
//        // Call the updateMovie method in moviesResource with the test movie as updatedMovie
//        Response response = moviesResource.updateMovie(1, testMovie);
//
//        // Assert the response status
//        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
//
//        // Verify that the MovieServices.updateMovie() method was called with the correct arguments
//        verify(movieServices, times(1)).updateMovie(eq(1), eq(testMovie));
//    }
//
//    @Test
//    void testUpdateMovie_InternalServerError() {
//        moviesResource = new MoviesResource();
//        movieServices = new MovieServices();
//        // Create a test movie with a known ID
//        Movie testMovie = new Movie(1, "Test Movie", "2023-07-27", 120, "Test plot summary",
//                "English", 1000000.00, 2000000.00);
//
//        // Mock the behavior of MovieServices.updateMovie() to throw an exception
//        doThrow(new RuntimeException("Failed to update movie")).when(movieServices).updateMovie(eq(1), eq(testMovie));
//
//        // Call the updateMovie method in moviesResource with the test movie as updatedMovie
//        Response response = moviesResource.updateMovie(1, testMovie);
//
//        // Assert the response status
//        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
//
//        // Assert the response entity (error message)
//        String errorMessage = (String) response.getEntity();
//        assertEquals("Failed to update the movie.", errorMessage);
//    }

}
