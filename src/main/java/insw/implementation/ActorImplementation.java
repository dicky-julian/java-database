package insw.implementation;

import insw.entity.Actor;
import insw.repository.ActorRepository;
import insw.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorImplementation implements ActorRepository {
    @Override
    public void insert(Actor actor) {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String query = "INSERT INTO actor (first_name, last_name) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, actor.getFirstName());
                statement.setString(2, actor.getLastName());
                statement.executeUpdate();

                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        actor.setId(result.getInt(1));
                    }
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Actor findById(Integer actorId) {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String query = "SELECT * FROM actor WHERE actor_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, actorId);

                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        return new Actor(
                          result.getInt("actor_id"),
                          result.getString("first_name"),
                          result.getString("last_name")
                        );
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Actor> findAll() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String query = "SELECT * FROM actor";

            try (Statement statement = connection.createStatement()) {

                try (ResultSet result = statement.executeQuery(query)) {
                    List<Actor> actors = new ArrayList<>();
                    while (result.next()) {
                        actors.add(new Actor(
                                result.getInt("actor_id"),
                                result.getString("first_name"),
                                result.getString("last_name")
                        ));
                    }
                    return actors;
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Actor> findAllByFirstName(String firstName) {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String query = "SELECT * FROM actor WHERE first_name = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, firstName);

                try (ResultSet result = statement.executeQuery()) {
                    List<Actor> actors = new ArrayList<>();
                    while (result.next()) {
                        actors.add(new Actor(
                                result.getInt("actor_id"),
                                result.getString("first_name"),
                                result.getString("last_name")
                        ));
                    }

                    return actors;
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
