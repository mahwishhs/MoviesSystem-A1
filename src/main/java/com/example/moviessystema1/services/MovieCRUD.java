package com.example.moviessystema1.services;

import com.example.moviessystema1.domain.Actor;
import com.example.moviessystema1.domain.Director;
import com.example.moviessystema1.domain.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieCRUD {
    public static List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = DBConn.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Movies")) {

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                String releaseDate = resultSet.getString("release_date");
                int duration = resultSet.getInt("duration");
                String plotSummary = resultSet.getString("plot_summary");
                String language = resultSet.getString("language");
                double budget = resultSet.getDouble("budget");
                double boxOfficeCollection = resultSet.getDouble("box_office_collection");
                String genreString = resultSet.getString("genre"); // Fetch genre as a string from the database

                Movie movie = new Movie(movieId, title, releaseDate, duration, plotSummary, language, budget, boxOfficeCollection);
                List<String> genres = Arrays.asList(genreString.split(","));
                movie.setGenres(genres); // Set the genres for the movie


                // Get associated actors, directors, and genres for this movie
                List<Actor> actors = getActorsForMovie(movieId);
                List<Director> directors = getDirectorsForMovie(movieId);

                // Set the associated lists for the movie
                movie.setActors(actors);
                movie.setDirectors(directors);

                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private static List<Actor> getActorsForMovie(int movieId) {
        List<Actor> actors = new ArrayList<>();

        try (Connection connection = DBConn.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Movie_Cast " +
                     "JOIN Actors ON Movie_Cast.actor_id = Actors.actor_id " +
                     "WHERE movie_id = " + movieId)) {

            while (resultSet.next()) {
                int actorId = resultSet.getInt("actor_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String dateOfBirth = resultSet.getString("date_of_birth");
                String nationality = resultSet.getString("nationality");

                Actor actor = new Actor(actorId, firstName, lastName, dateOfBirth, nationality);
                actors.add(actor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actors;
    }

    private static List<Director> getDirectorsForMovie(int movieId) {
        List<Director> directors = new ArrayList<>();

        try (Connection connection = DBConn.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Movie_Directors " +
                     "JOIN Directors ON Movie_Directors.director_id = Directors.director_id " +
                     "WHERE movie_id = " + movieId)) {

            while (resultSet.next()) {
                int directorId = resultSet.getInt("director_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String dateOfBirth = resultSet.getString("date_of_birth");
                String nationality = resultSet.getString("nationality");

                Director director = new Director(directorId, firstName, lastName, dateOfBirth, nationality);
                directors.add(director);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return directors;
    }


//--------------------FOR ADDINGGGGG MOVIESSSS------------

    public static void addMovie(Movie movie) {
        try (Connection connection = DBConn.getConnection()) {
            // Save the movie details into the Movies table
            String insertMovieQuery = "INSERT INTO Movies (movie_id, title, release_date, duration, plot_summary, language, budget, box_office_collection, genre) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertMovieQuery)) {
                preparedStatement.setInt(1, movie.getMovieId());
                preparedStatement.setString(2, movie.getTitle());
                preparedStatement.setString(3, movie.getReleaseDate());
                preparedStatement.setInt(4, movie.getDuration());
                preparedStatement.setString(5, movie.getPlotSummary());
                preparedStatement.setString(6, movie.getLanguage());
                preparedStatement.setDouble(7, movie.getBudget());
                preparedStatement.setDouble(8, movie.getBoxOfficeCollection());
                preparedStatement.setString(9, String.join(",", movie.getGenres())); // Assuming genres are stored as a comma-separated string

                preparedStatement.executeUpdate();

                // Save associated actors and directors in their respective tables
                saveActorsForMovie(connection, movie.getMovieId(), movie.getActors());
                saveDirectorsForMovie(connection, movie.getMovieId(), movie.getDirectors());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private static void saveActorsForMovie(Connection connection, int movieId, List<Actor> actors) throws SQLException {
        String insertActorQuery = "INSERT INTO Movie_Cast (movie_id, actor_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertActorQuery)) {
            for (Actor actor : actors) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, actor.getActorId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    // Method to save directors for a movie in the database
    private static void saveDirectorsForMovie(Connection connection, int movieId, List<Director> directors) throws SQLException {
        String insertDirectorQuery = "INSERT INTO Movie_Directors (movie_id, director_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDirectorQuery)) {
            for (Director director : directors) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, director.getDirectorId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }


    public static void deleteMovie(int movieId) {
        try (Connection connection = DBConn.getConnection()) {
            // Delete the movie from the Movies table
            String deleteMovieQuery = "DELETE FROM Movies WHERE movie_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteMovieQuery)) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.executeUpdate();
            }

            // Also delete associated records from the Movie_Cast and Movie_Directors tables
            deleteActorsForMovie(connection, movieId);
            deleteDirectorsForMovie(connection, movieId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private static void deleteActorsForMovie(Connection connection, int movieId) throws SQLException {
        String deleteActorsQuery = "DELETE FROM Movie_Cast WHERE movie_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteActorsQuery)) {
            preparedStatement.setInt(1, movieId);
            preparedStatement.executeUpdate();
        }
    }

    private static void deleteDirectorsForMovie(Connection connection, int movieId) throws SQLException {
        String deleteDirectorsQuery = "DELETE FROM Movie_Directors WHERE movie_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteDirectorsQuery)) {
            preparedStatement.setInt(1, movieId);
            preparedStatement.executeUpdate();
        }
    }

    public static Movie getMovieById(int movieId) {
        Movie movie = null;

        try (Connection connection = DBConn.getConnection()) {
            String query = "SELECT * FROM Movies WHERE movie_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, movieId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String title = resultSet.getString("title");
                        String releaseDate = resultSet.getString("release_date");
                        int duration = resultSet.getInt("duration");
                        String plotSummary = resultSet.getString("plot_summary");
                        String language = resultSet.getString("language");
                        double budget = resultSet.getDouble("budget");
                        double boxOfficeCollection = resultSet.getDouble("box_office_collection");
                        String genre = resultSet.getString("genre");

                        movie = new Movie(movieId, title, releaseDate, duration, plotSummary, language, budget, boxOfficeCollection);
                        movie.setActors(getActorsForMovie(movieId));
                        movie.addGenre(genre); // Assuming a single genre is stored as a string in the genre column

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        return movie;
    }

    public static List<Movie> getMoviesByActorName(String actorName) {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = DBConn.getConnection()) {
            String query = "SELECT m.movie_id, m.title, m.release_date, m.duration, m.plot_summary, m.language, m.budget, m.box_office_collection, m.genre " +
                    "FROM Movies m " +
                    "JOIN Movie_Cast mc ON m.movie_id = mc.movie_id " +
                    "JOIN Actors a ON mc.actor_id = a.actor_id " +
                    "WHERE CONCAT(a.first_name, ' ', a.last_name) LIKE ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + actorName + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int movieId = resultSet.getInt("movie_id");
                        String title = resultSet.getString("title");
                        String releaseDate = resultSet.getString("release_date");
                        int duration = resultSet.getInt("duration");
                        String plotSummary = resultSet.getString("plot_summary");
                        String language = resultSet.getString("language");
                        double budget = resultSet.getDouble("budget");
                        double boxOfficeCollection = resultSet.getDouble("box_office_collection");
                        String genreString = resultSet.getString("genre"); // Fetch genre as a string from the database

                        Movie movie = new Movie(movieId, title, releaseDate, duration, plotSummary, language, budget, boxOfficeCollection);
                        List<String> genres = Arrays.asList(genreString.split(","));
                        movie.setGenres(genres); // Set the genres for the movie

                        // Get associated actors and directors for this movie
                        List<Actor> actors = getActorsForMovie(movieId);
                        List<Director> directors = getDirectorsForMovie(movieId);

                        // Set the associated lists for the movie
                        movie.setActors(actors);
                        movie.setDirectors(directors);

                        movies.add(movie);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        return movies;
    }

    public static List<Movie> getMoviesByGenre(String genre) {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Movies WHERE genre LIKE ?")) {

            preparedStatement.setString(1, "%" + genre + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int movieId = resultSet.getInt("movie_id");
                    String title = resultSet.getString("title");
                    String releaseDate = resultSet.getString("release_date");
                    int duration = resultSet.getInt("duration");
                    String plotSummary = resultSet.getString("plot_summary");
                    String language = resultSet.getString("language");
                    double budget = resultSet.getDouble("budget");
                    double boxOfficeCollection = resultSet.getDouble("box_office_collection");
                    String genreString = resultSet.getString("genre"); // Fetch genre as a string from the database

                    Movie movie = new Movie(movieId, title, releaseDate, duration, plotSummary, language, budget, boxOfficeCollection);
                    List<String> genres = Arrays.asList(genreString.split(","));
                    movie.setGenres(genres); // Set the genres for the movie

                    // Get associated actors and directors for this movie
                    List<Actor> actors = getActorsForMovie(movieId);
                    List<Director> directors = getDirectorsForMovie(movieId);

                    // Set the associated lists for the movie
                    movie.setActors(actors);
                    movie.setDirectors(directors);

                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static List<Movie> getMoviesByDirector(String directorName) {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Movies " +
                             "JOIN Movie_Directors ON Movies.movie_id = Movie_Directors.movie_id " +
                             "JOIN Directors ON Movie_Directors.director_id = Directors.director_id " +
                             "WHERE Directors.first_name LIKE ? OR Directors.last_name LIKE ?")) {

            preparedStatement.setString(1, "%" + directorName + "%");
            preparedStatement.setString(2, "%" + directorName + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int movieId = resultSet.getInt("movie_id");
                    String title = resultSet.getString("title");
                    String releaseDate = resultSet.getString("release_date");
                    int duration = resultSet.getInt("duration");
                    String plotSummary = resultSet.getString("plot_summary");
                    String language = resultSet.getString("language");
                    double budget = resultSet.getDouble("budget");
                    double boxOfficeCollection = resultSet.getDouble("box_office_collection");
                    String genreString = resultSet.getString("genre"); // Fetch genre as a string from the database

                    Movie movie = new Movie(movieId, title, releaseDate, duration, plotSummary, language, budget, boxOfficeCollection);
                    List<String> genres = Arrays.asList(genreString.split(","));
                    movie.setGenres(genres); // Set the genres for the movie

                    // Get associated actors and directors for this movie
                    List<Actor> actors = getActorsForMovie(movieId);
                    List<Director> directors = getDirectorsForMovie(movieId);

                    // Set the associated lists for the movie
                    movie.setActors(actors);
                    movie.setDirectors(directors);

                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static List<Movie> getMoviesByActorAndDirector(String actorName, String directorName) {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Movies " +
                             "JOIN Movie_Cast ON Movies.movie_id = Movie_Cast.movie_id " +
                             "JOIN Actors ON Movie_Cast.actor_id = Actors.actor_id " +
                             "JOIN Movie_Directors ON Movies.movie_id = Movie_Directors.movie_id " +
                             "JOIN Directors ON Movie_Directors.director_id = Directors.director_id " +
                             "WHERE Actors.first_name LIKE ? OR Actors.last_name LIKE ? " +
                             "AND (Directors.first_name LIKE ? OR Directors.last_name LIKE ?)")) {

            preparedStatement.setString(1, "%" + actorName + "%");
            preparedStatement.setString(2, "%" + actorName + "%");
            preparedStatement.setString(3, "%" + directorName + "%");
            preparedStatement.setString(4, "%" + directorName + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int movieId = resultSet.getInt("movie_id");
                    String title = resultSet.getString("title");
                    String releaseDate = resultSet.getString("release_date");
                    int duration = resultSet.getInt("duration");
                    String plotSummary = resultSet.getString("plot_summary");
                    String language = resultSet.getString("language");
                    double budget = resultSet.getDouble("budget");
                    double boxOfficeCollection = resultSet.getDouble("box_office_collection");
                    String genreString = resultSet.getString("genre"); // Fetch genre as a string from the database

                    Movie movie = new Movie(movieId, title, releaseDate, duration, plotSummary, language, budget, boxOfficeCollection);
                    List<String> genres = Arrays.asList(genreString.split(","));
                    movie.setGenres(genres); // Set the genres for the movie

                    // Get associated actors and directors for this movie
                    List<Actor> actors = getActorsForMovie(movieId);
                    List<Director> directors = getDirectorsForMovie(movieId);

                    // Set the associated lists for the movie
                    movie.setActors(actors);
                    movie.setDirectors(directors);

                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static void updateMovie(int id, Movie movie) {
        try (Connection connection = DBConn.getConnection()) {
            // Update the movie details in the Movies table
            String updateMovieQuery = "UPDATE Movies SET title = ?, release_date = ?, duration = ?, plot_summary = ?, " +
                    "language = ?, budget = ?, box_office_collection = ?, genre = ? WHERE movie_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateMovieQuery)) {
                preparedStatement.setString(1, movie.getTitle());
                preparedStatement.setString(2, movie.getReleaseDate());
                preparedStatement.setInt(3, movie.getDuration());
                preparedStatement.setString(4, movie.getPlotSummary());
                preparedStatement.setString(5, movie.getLanguage());
                preparedStatement.setDouble(6, movie.getBudget());
                preparedStatement.setDouble(7, movie.getBoxOfficeCollection());
                preparedStatement.setString(8, String.join(",", movie.getGenres()));
                preparedStatement.setInt(9, movie.getMovieId());

                preparedStatement.executeUpdate();

                // Update associated actors and directors in their respective tables
                updateActorsForMovie(connection, movie.getMovieId(), movie.getActors());
                updateDirectorsForMovie(connection, movie.getMovieId(), movie.getDirectors());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private static void updateActorsForMovie(Connection connection, int movieId, List<Actor> actors) throws SQLException {
        // First, delete existing actors for the movie
        String deleteActorsQuery = "DELETE FROM Movie_Cast WHERE movie_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteActorsQuery)) {
            preparedStatement.setInt(1, movieId);
            preparedStatement.executeUpdate();
        }

        // Then, insert the updated list of actors
        String insertActorQuery = "INSERT INTO Movie_Cast (movie_id, actor_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertActorQuery)) {
            for (Actor actor : actors) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, actor.getActorId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    // Method to update directors for a movie in the database
    private static void updateDirectorsForMovie(Connection connection, int movieId, List<Director> directors) throws SQLException {
        // First, delete existing directors for the movie
        String deleteDirectorsQuery = "DELETE FROM Movie_Directors WHERE movie_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteDirectorsQuery)) {
            preparedStatement.setInt(1, movieId);
            preparedStatement.executeUpdate();
        }

        // Then, insert the updated list of directors
        String insertDirectorQuery = "INSERT INTO Movie_Directors (movie_id, director_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDirectorQuery)) {
            for (Director director : directors) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.setInt(2, director.getDirectorId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }





}



