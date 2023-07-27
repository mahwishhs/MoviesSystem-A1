package com.example.moviessystema1.services;

import com.example.moviessystema1.domain.Actor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorServices {

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

    public static List<Actor> getActorsByName(String actorName) {
        List<Actor> actors = new ArrayList<>();
        String sql = "SELECT * FROM Actors WHERE first_name LIKE ? OR last_name LIKE ?";

        try (Connection connection = DBConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + actorName + "%");
            preparedStatement.setString(2, "%" + actorName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

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



}
