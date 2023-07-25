package com.example.moviessystema1.services;

import com.example.moviessystema1.domain.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieCRUD {
    public static void addMovie(Movie movie) {
        String sql = "INSERT INTO Movies (movie_id, title, release_date, duration, plot_summary, language, budget, box_office_collection) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movie.getMovieId());
            preparedStatement.setString(2, movie.getTitle());
            preparedStatement.setString(3, movie.getReleaseDate());
            preparedStatement.setInt(4, movie.getDuration());
            preparedStatement.setString(5, movie.getPlotSummary());
            preparedStatement.setString(6, movie.getLanguage());
            preparedStatement.setDouble(7, movie.getBudget());
            preparedStatement.setDouble(8, movie.getBoxOfficeCollection());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = DBConn.getConnection()) {
            String query = "SELECT * FROM Movies";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                String releaseDate = resultSet.getString("release_date");
                int duration = resultSet.getInt("duration");
                String plotSummary = resultSet.getString("plot_summary");
                String language = resultSet.getString("language");
                double budget = resultSet.getDouble("budget");
                double boxOfficeCollection = resultSet.getDouble("box_office_collection");

                Movie movie = new Movie(movieId, title, releaseDate, duration, plotSummary, language, budget, boxOfficeCollection);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static void updateMovie(Movie updatedMovie) {
        String sql = "UPDATE Movies SET title = ?, release_date = ?, duration = ?, plot_summary = ?, " +
                "language = ?, budget = ?, box_office_collection = ? WHERE movie_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedMovie.getTitle());
            preparedStatement.setString(2, updatedMovie.getReleaseDate());
            preparedStatement.setInt(3, updatedMovie.getDuration());
            preparedStatement.setString(4, updatedMovie.getPlotSummary());
            preparedStatement.setString(5, updatedMovie.getLanguage());
            preparedStatement.setDouble(6, updatedMovie.getBudget());
            preparedStatement.setDouble(7, updatedMovie.getBoxOfficeCollection());
            preparedStatement.setInt(8, updatedMovie.getMovieId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovie(int movieId) {
        String sql = "DELETE FROM Movies WHERE movie_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Movie getMovieById(int movieId) {
        String sql = "SELECT * FROM Movies WHERE movie_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Movie(
                            resultSet.getInt("movie_id"),
                            resultSet.getString("title"),
                            resultSet.getString("release_date"),
                            resultSet.getInt("duration"),
                            resultSet.getString("plot_summary"),
                            resultSet.getString("language"),
                            resultSet.getDouble("budget"),
                            resultSet.getDouble("box_office_collection")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
