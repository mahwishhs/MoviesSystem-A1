package com.example.moviessystema1.services;

import com.example.moviessystema1.domain.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreCRUD {
    public static void createGenre(Genre genre) {
        String sql = "INSERT INTO Genres (genre_id, name) VALUES (?, ?)";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, genre.getGenreId());
            preparedStatement.setString(2, genre.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateGenre(Genre updatedGenre) {
        String sql = "UPDATE Genres SET name = ? WHERE genre_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedGenre.getName());
            preparedStatement.setInt(2, updatedGenre.getGenreId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteGenre(int genreId) {
        String sql = "DELETE FROM Genres WHERE genre_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, genreId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = DBConn.getConnection()) {
            String query = "SELECT * FROM Genres";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int genreId = resultSet.getInt("genre_id");
                String name = resultSet.getString("name");

                Genre genre = new Genre(genreId, name);
                genres.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }
    public static Genre getGenreById(int genreId) {
        String sql = "SELECT * FROM Genres WHERE genre_id = ?";
        try (Connection connection = DBConn.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, genreId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Genre(
                            resultSet.getInt("genre_id"),
                            resultSet.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
