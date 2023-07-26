package com.example.moviessystema1.services;

import com.example.moviessystema1.domain.Actor;
import com.example.moviessystema1.domain.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorCRUD {
    public static void addActor(Actor actor){
        String sql = "INSERT INTO Actors (actor_id, first_name, last_name, date_of_birth, nationality) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, actor.getActorId());
            preparedStatement.setString(2, actor.getFirstName());
            preparedStatement.setString(3, actor.getLastName());
            preparedStatement.setString(4, actor.getDateOfBirth());
            preparedStatement.setString(5, actor.getNationality());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateActor(Actor updatedActor) {
        String sql = "UPDATE Actors SET first_name = ?, last_name = ?, date_of_birth = ?, nationality = ? " +
                "WHERE actor_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedActor.getFirstName());
            preparedStatement.setString(2, updatedActor.getLastName());
            preparedStatement.setString(3, updatedActor.getDateOfBirth());
            preparedStatement.setString(4, updatedActor.getNationality());
            preparedStatement.setInt(5, updatedActor.getActorId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteActor(int actorId) {
        String sql = "DELETE FROM Actors WHERE actor_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, actorId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Actor> getAllActors() {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = DBConn.getConnection()) {
            String query = "SELECT * FROM Actors";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
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

    public static Actor getActorById(int actorId) {
        String sql = "SELECT * FROM Actors WHERE actor_id = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, actorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Actor(
                            resultSet.getInt("actor_id"),
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

    public static List<Movie> getMoviesByActorName(String actorName) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT m.movie_id, m.title, m.release_date, m.duration, m.plot_summary, m.language, m.budget, m.box_office_collection " +
                "FROM Movies m " +
                "JOIN Movie_Cast mc ON m.movie_id = mc.movie_id " +
                "JOIN Actors a ON mc.actor_id = a.actor_id " +
                "WHERE CONCAT(a.first_name, ' ', a.last_name) = ?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, actorName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Movie movie = new Movie(
                            resultSet.getInt("movie_id"),
                            resultSet.getString("title"),
                            resultSet.getString("release_date"),
                            resultSet.getInt("duration"),
                            resultSet.getString("plot_summary"),
                            resultSet.getString("language"),
                            resultSet.getDouble("budget"),
                            resultSet.getDouble("box_office_collection")
                    );
                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
