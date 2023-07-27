package com.example.moviessystema1.services;

import com.example.moviessystema1.domain.Director;
import com.example.moviessystema1.domain.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirectorServices {
    public static void createDirector(Director director) {
        String sql = "INSERT INTO Directors (director_id, first_name, last_name, date_of_birth, nationality) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, director.getDirectorId());
            preparedStatement.setString(2, director.getFirstName());
            preparedStatement.setString(3, director.getLastName());
            preparedStatement.setString(4, director.getDateOfBirth());
            preparedStatement.setString(5, director.getNationality());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDirector(Director updatedDirector) {
        String sql = "UPDATE Directors SET first_name = ?, last_name = ?, date_of_birth = ?, nationality = ? " +
                "WHERE director_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedDirector.getFirstName());
            preparedStatement.setString(2, updatedDirector.getLastName());
            preparedStatement.setString(3, updatedDirector.getDateOfBirth());
            preparedStatement.setString(4, updatedDirector.getNationality());
            preparedStatement.setInt(5, updatedDirector.getDirectorId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDirector(int directorId) {
        String sql = "DELETE FROM Directors WHERE director_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, directorId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Director> getAllDirectors() {
        List<Director> directors = new ArrayList<>();
        try (Connection connection = DBConn.getConnection()) {
            String query = "SELECT * FROM Directors";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
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

    public static Director getDirectorById(int directorId) {
        String sql = "SELECT * FROM Directors WHERE director_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, directorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Director(
                            resultSet.getInt("director_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("date_of_birth"),
                            resultSet.getString("nationality")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Director> getDirectorsByName(String directorName) {
        List<Director> directors = new ArrayList<>();
        String sql = "SELECT * FROM Directors WHERE first_name LIKE ? OR last_name LIKE ?";

        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + directorName + "%");
            preparedStatement.setString(2, "%" + directorName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

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


}
