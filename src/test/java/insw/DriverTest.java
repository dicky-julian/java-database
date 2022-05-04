package insw;

import java.sql.*;
import com.mysql.cj.jdbc.Driver;
import insw.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DriverTest {
    @Test
    // Register mysql's driver to Java Database Connection
    void testRegister() {
        try {
            Driver mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    // Create Connection to Database
    void testConnection() {
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/sakila";
            String username = "root";
            String password = "171198";

            Connection connection = DriverManager.getConnection(dbUrl, username, password);

            // close connection
            connection.close();
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }
    }

    @Test
    // Create Connection Pool
    void testConnectionPool() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        // Read DML use executeQuery()
        String query = "SELECT * FROM actor";
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
;
            System.out.println(firstName + " " + lastName);
        }

        // Write DML use executeUpdate()
        String queryUpdate = """
                INSERT INTO actor(first_name, last_name)
                VALUES ("DICKY", "JULIAN");
                """;
        int recordExecuted = statement.executeUpdate(queryUpdate);
        System.out.println("record executed: " + recordExecuted);

        // close query
        result.close();

        // close statement
        statement.close();

        // turn back connection (keep alive)
        connection.close();
    }

    @Test
    // prepared statement is good to avoid SQL Injection
    void testPreparedStatement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String query = "SELECT * FROM actor WHERE first_name = ? AND last_name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "MARSHA");
        statement.setString(2, "LENATHEA");

        ResultSet result = statement.executeQuery();
        while (result.next()) {
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");

            System.out.println(firstName + " " + lastName);
        }

        result.close();
        connection.close();
    }

    @Test
    // execute update as a batch
    void testExecuteBatch() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        for (int i = 0; i < 100; i++) {
            String query = "INSERT INTO my_info (column_1, column_2) VALUES (" + "'user_" + i + "', '" + "user_sub_" + i + "')";
            // push query to statement batch
            statement.addBatch(query);
        }

        // just execute when executeBatch() running
        statement.executeBatch();
        statement.close();
        connection.close();
    }

    @Test
    // execute update as a batch with prepare statement
    void testPrepareExecuteBatch() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String query = "INSERT INTO my_info (column_1, column_2) VALUES (?, ?)";

        // Statement.RETURN_GENERATED_KEYS will return auto increment key
        PreparedStatement statement = connection.prepareStatement(query);

        for (int i = 0; i < 100; i++) {
            // clear last parameter
            statement.clearParameters();

            // declare new parameter
            statement.setString(1, "user_" + i);
            statement.setString(2, "user_" + i + "@gmail.com");

            // push query to statement batch
            statement.addBatch();
        }

        // just execute when executeBatch() running
        statement.executeBatch();
        statement.close();
        connection.close();
    }

    @Test
    // Get auto increment key
    void testGeneratedId() throws  SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String query = "INSERT INTO my_info(column_1, column_2) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, "DICKY JULIAN");
        statement.setString(2, "dicky.julian@gmail.com");

        statement.executeUpdate();

        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) {
            System.out.println("Generated ID: " + result.getInt(1));
        }
        result.close();
        statement.close();
        connection.close();
    }

    @Test
    void testTransaction() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        connection.setAutoCommit(false);

        String query = "INSERT INTO my_info(column_1, column_2) VALUES (?, ?)";
        for (int i = 0; i < 100; i++) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "user_" + i);
            statement.setString(2, "user_" + i + "@ech.com");
            statement.executeUpdate();
            statement.close();
        }

        connection.commit();
        // OR
        // connection.rollback();
        connection.close();
    }
}
