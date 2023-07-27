package com.example.moviessystema1.services;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.moviessystema1.domain.Actor;

import com.example.moviessystema1.domain.Movie;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MovieServicesTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @InjectMocks
    private MovieServices movieServices;

    @Test
    public void testAddMovie() throws Exception {
        // Create a mock Movie object and set its properties
        Movie testMovie = new Movie(100, "Test Movie", "2023-07-27", 120, "Test plot summary", "English", 1000000.0, 2000000.0);
        testMovie.setGenres(Arrays.asList("Action", "Adventure"));

        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            // Mock the DBConn class and connection
            Connection mockConnection = mock(Connection.class);
            when(DBConn.getConnection()).thenReturn(mockConnection);

            // Create a mock PreparedStatement
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
            doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
            doNothing().when(mockPreparedStatement).setDouble(anyInt(), anyDouble());
            // Add more behavior setups if needed

            // Call the addMovie method
            MovieServices.addMovie(testMovie);

            // Verify that the getConnection method is called on DBConn
            mockedDBConn.verify(() -> DBConn.getConnection());

            // Verify that other relevant methods are called with appropriate arguments
            verify(mockPreparedStatement, times(1)).setInt(1, 100);
            verify(mockPreparedStatement).setString(2, "Test Movie");
            verify(mockPreparedStatement).setString(3, "2023-07-27");
            // Verify other arguments and method calls as needed
        }
    }

    @Test
    public void testUpdateMovie() throws Exception {
        int movieId = 100;
        Movie testMovie = new Movie(movieId, "Updated Movie", "2023-07-28", 130, "Updated plot summary", "English", 1500000.0, 3000000.0);
        testMovie.setGenres(Arrays.asList("Action", "Adventure", "Sci-Fi"));

        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            Connection mockConnection = mock(Connection.class);
            when(DBConn.getConnection()).thenReturn(mockConnection);

            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
            doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
            doNothing().when(mockPreparedStatement).setDouble(anyInt(), anyDouble());

            // Call the updateMovie method
            MovieServices.updateMovie(movieId, testMovie);

            // Verify that the getConnection method is called on DBConn
            mockedDBConn.verify(() -> DBConn.getConnection());

            // Verify that other relevant methods are called with appropriate arguments
            verify(mockPreparedStatement, times(1)).setString(1, "Updated Movie");
            verify(mockPreparedStatement).setString(2, "2023-07-28");
            verify(mockPreparedStatement).setInt(3, 130);
            // Verify other arguments and method calls as needed
        }
    }


    @Test
    void testDeleteMovie_Success() throws SQLException {
        // Create a mock Connection
        Connection mockConnection = mock(Connection.class);
        // Create a mock PreparedStatement
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        // Mock the static method DBConn.getConnection() to return the mock connection
        try (MockedStatic<DBConn> mockedDBConn = mockStatic(DBConn.class)) {
            mockedDBConn.when(DBConn::getConnection).thenReturn(mockConnection);

            int movieId = 1;

            // Mocking the prepareStatement() method to return the mock prepared statement
            when(mockConnection.prepareStatement(any())).thenReturn(mockPreparedStatement);

            // Mocking the executeUpdate() method to return the number of rows deleted
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            // Call the method to be tested
            MovieServices.deleteMovie(movieId);

            // Verify that the appropriate methods are called with the correct arguments
            verify(mockConnection).prepareStatement("DELETE FROM Movies WHERE movie_id = ?");
            verify(mockPreparedStatement).setInt(1, movieId);
            verify(mockPreparedStatement).executeUpdate();
            verify(mockConnection).close();
        }
    }


    @Test
    void testGetMovieById_NonExistingId_ReturnsNull() {
        // Test data for a non-existing movie ID
        int nonExistingId = 9999;

        // Create an empty test list of movies (no movies added)

        // Create an instance of MovieServices with the empty test list of movies
        MovieServices movieServices = new MovieServices();

        // Call the getMovieById method with the non-existing movie ID
        Movie resultMovie = movieServices.getMovieById(nonExistingId);

        // Assert that the returned movie object is null for non-existing ID
        assertNull(resultMovie);
    }
}














// Add more test cases to cover other methods in the MovieServices class as needed


//
//
////
////
////    @BeforeEach
////    void setUp() {
////    }
////
////    @AfterEach
////    void tearDown() {
////    }
////
////    @Test
////    void getAllMovies() {
////    }
////
////    @Test
////    void getActorsForMovie() {
////    }
////
////    @Test
////    void getDirectorsForMovie() {
////    }
////
////    @Test
////    void addMovie() {
////    }
////
////    @Test
////    void deleteMovie() {
////    }
////
////    @Test
////    void getMovieById() {
////    }
////
////    @Test
////    void getMoviesByActorName() {
////    }
////
////    @Test
////    void getMoviesByGenre() {
////    }
////
////    @Test
////    void getMoviesByDirector() {
////    }
////
////    @Test
////    void getMoviesByActorAndDirector() {
////    }
////
////    @Test
////    void updateMovie() {
////    }

