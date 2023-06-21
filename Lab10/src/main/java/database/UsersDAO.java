package database;

import Models.User;
import Models.UserType;
import Utils.Hasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UsersDAO {

    public enum LoginResult
    {
        UserNotFound,
        WrongPassword,
        Success
    }

    private final Connection _connection;
    private final Hasher _hasher;

    public UsersDAO() throws Exception {
        _connection = new DbConnection().Connect();
        _hasher = new Hasher();
    }

    public void Register(String login, String password, UserType userType) throws SQLException {
        String query = " INSERT INTO users(login, password, userType) VALUES (?, ?, ?);";
        PreparedStatement statement = _connection.prepareStatement(query);

        //String hashedPassword = _hasher.hash(password);
        statement.setString(1, login);
        statement.setString(2, password);
        statement.setString(3, userType.toString());
        statement.executeUpdate();

        statement.close();
    }

    public LoginResult Login(String login, String password) throws SQLException {
        PreparedStatement statement = _connection.prepareStatement("SELECT password FROM users WHERE login='" + login + "';");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {

            if (Objects.equals(password, resultSet.getString("password"))) {

                return LoginResult.Success;
            }
            return LoginResult.WrongPassword;
        }

        return LoginResult.UserNotFound;
    }

    public User GetUser(String login) throws SQLException {
        PreparedStatement statement = _connection.prepareStatement("SELECT * FROM users WHERE login='" + login + "';");

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setID(resultSet.getInt(4));
            user.setLogin(resultSet.getString(1));
            user.setPassword(resultSet.getString(2));
            user.setRole(String.valueOf(resultSet.getString(3)));

            return user;
        }

        return null;
    }
}
