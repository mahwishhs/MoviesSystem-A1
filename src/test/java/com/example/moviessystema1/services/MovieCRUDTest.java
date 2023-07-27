//package com.example.moviessystema1.services;
//
//import com.example.moviessystema1.domain.Actor;
//import com.example.moviessystema1.domain.Director;
//import com.example.moviessystema1.domain.Movie;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.api.mockito.PowerMockito;
//
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//
//@PrepareForTest({DBConn.class, MovieServices.class})
//class MovieCRUDTest {
//    private Connection connection;
//    private Statement statement;
//    private ResultSet resultSet;
//    private MovieServices movieCRUD;
//
//    @BeforeEach
//    public void setUp() throws SQLException {
//        connection = mock(Connection.class);
//        statement = mock(Statement.class);
//        resultSet = mock(ResultSet.class);
//        movieCRUD = new MovieServices();
//
//        // Set up the mock interactions
//        when(connection.createStatement()).thenReturn(statement);
//        when(statement.executeQuery(anyString())).thenReturn(resultSet);
//    }
//
//    @Test
//
//    public void testGetAllMovies() throws Exception {
//        // Mock the DBConn class and its getConnection() method
//        Connection connection = mock(Connection.class);
//        Statement statement = mock(Statement.class);
//        ResultSet resultSet = mock(ResultSet.class);
//
//        // Mock the behavior of static method DBConn.getConnection()
//        PowerMockito.mockStatic(DBConn.class);
//        when(DBConn.getConnection()).thenReturn(connection);
//
//        // Mock the behavior of connection.createStatement()
//        when(connection.createStatement()).thenReturn(statement);
//
//        // Mock the behavior of statement.executeQuery()
//        String query = "SELECT * FROM Movies";
//        when(statement.executeQuery(query)).thenReturn(resultSet);
//
//        // Mock the result set data
//        when(resultSet.next()).thenReturn(true).thenReturn(false);
//        when(resultSet.getInt("movie_id")).thenReturn(1);
//        when(resultSet.getString("title")).thenReturn("Movie 1");
//        // Mock other properties as needed...
//
//        // Call the method under test
//        MovieServices movieCRUD = new MovieServices();
//        List<Movie> movies = movieCRUD.getAllMovies();
//
//        // Verify the expected interactions
//        PowerMockito.verifyStatic(DBConn.class);
//        DBConn.getConnection();
//        verify(connection).createStatement();
//        verify(statement).executeQuery(query);
//        // Verify other interactions...
//
//        // Assert the expected result
//        assertEquals(1, movies.size());
//        // Assert other properties...
//    }
//
//    @Test
//    void addMovie() {
//    }
//
//    @Test
//    void deleteMovie() {
//    }
//
//    @Test
//    void getMovieById() {
//    }
//
//    @Test
//    void getMoviesByActorName() {
//    }
//
//    @Test
//    void getMoviesByGenre() {
//    }
//
//    @Test
//    void getMoviesByDirector() {
//    }
//
//    @Test
//    void getMoviesByActorAndDirector() {
//    }
//
//    @Test
//    void updateMovie() {
//    }
//}