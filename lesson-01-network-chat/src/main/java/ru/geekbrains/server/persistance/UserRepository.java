package ru.geekbrains.server.persistance;

import ru.geekbrains.server.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class UserRepository {

    private final Connection connection;

    public UserRepository(DriverManagerDataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public UserRepository(String url, String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
        createTableIfNotExists(connection);
    }

    public void insert(User user) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "insert into users(login, password) values (?, ?);")) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.execute();
        }
    }

    public User findByLogin(String login) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select id, login, password from users where login = ?")) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }
        return new User(-1, "", "");
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> res = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, login, password from users");

            while (rs.next()) {
                res.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        }
        return res;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists users (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    login varchar(25),\n" +
                    "    password varchar(25),\n" +
                    "    unique index uq_login(login)\n" +
                    ");");
        }
    }
}
