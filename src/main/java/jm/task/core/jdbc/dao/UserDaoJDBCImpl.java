package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Statement statement;

    static {
        try {
            statement = Util.connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS users(id BIGINT primary key auto_increment, name TEXT, lastName TEXT, age TINYINT UNSIGNED);");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void dropUsersTable() {
        try {
            statement.execute("DROP TABLE IF EXISTS users;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            statement.executeUpdate(String.format("INSERT INTO users (name, lastName, age) VALUES ('%s', '%s', %d);", name, lastName, age));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) {
        try {
            statement.executeUpdate(String.format("DELETE FROM users WHERE id=%d;", id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            statement.execute("TRUNCATE TABLE users;");
            Util.connection.commit();
        } catch (SQLException throwables) {
            try {
                Util.connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }
}
