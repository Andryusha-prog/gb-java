package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UsersRepos {

    public Optional<Users> findUsers(String login, String pass) {
        Connection connection = DBConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Name, Login, Password FROM users WHERE Login = \"" + login + "\" AND Password = \"" + pass + "\"");

            if (resultSet.next()) {
                return Optional.of(
                        new Users(
                                resultSet.getString("Name"),
                                resultSet.getString("Login"),
                                resultSet.getString("Password")
                        )
                );
            }
            return Optional.empty();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}
