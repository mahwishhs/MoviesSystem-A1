//package com.example.moviessystema1.resources;
//
//import com.example.moviessystema1.domain.Movie;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import com.example.moviessystema1.services.MovieServices;
//
//import org.mockito.Mockito;
//
//import javax.ws.rs.core.Response;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//
//class MoviesResourceTest {
//    private MovieServices movieCRUD;
//    private MoviesResource moviesResource;
//
//    @BeforeEach
//    void setUp() {
//        movieCRUD = Mockito.mock(MovieServices.class);
//        moviesResource = new MoviesResource(movieCRUD);
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void getAllMovies() {
//        // Create a list of movies for testing
//        List<Movie> movies = new ArrayList<>();
//        movies.add(new Movie(1, "Movie 1", "2023-07-26", 120, "Plot summary 1", "English", 1000000, 2000000));
//        movies.add(new Movie(2, "Movie 2", "2023-07-27", 130, "Plot summary 2", "Spanish", 1500000, 2500000));
//
//        // Mock the MovieServices.getAllMovies() method to return the test list
//        when(movieCRUD.getAllMovies()).thenReturn(movies);
//
//        // Call the method under test
//        Response response = moviesResource.getAllMovies();
//
//        // Verify the response
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        assertEquals(movies, response.getEntity());
//    }
//
////    @Test
////    void addMovie() {
////    }
////
////    @Test
////    void updateMovie() {
////    }
////
////    @Test
////    void deleteMovie() {
////    }
////
////    @Test
////    void getMoviesByGenreName() {
////    }
////
////    @Test
////    void getMoviesByActorName() {
////    }
////
////    @Test
////    void getMoviesByDirectorName() {
////    }
//}