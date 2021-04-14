package Server;

import db.DBConnection;
import db.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class AutentificationService {

    public Optional<Users> findEntryByCredentials(String login, String password) {
        //return entries.stream().filter(entry -> entry.getLogin().equals(login) && entry.getPassword().equals(password)).findFirst();

        Connection connection = DBConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Name, Login, Password FROM users WHERE Login = \"" + login + "\" AND Password = \"" + password + "\"");

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

    public static class Entry {
        public String name;
        public String login;
        public String password;

        public Entry(String name, String login, String password) {
            this.name = name;
            this.login = login;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }
}
