package Server;

import db.DBConnection;
import db.Users;

import java.sql.*;
import java.util.Optional;

public class AutentificationService {

    public Optional<Users> findEntryByCredentials(String login, String password) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Name, Login, Password FROM users WHERE Login = ? AND Password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();

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
