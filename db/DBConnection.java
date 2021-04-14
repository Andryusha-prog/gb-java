package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/chatusersdb?useUnicode=true&serverTimezone=UTC", "root", "12345");
            //почему-то при соединении IDEA просила указать часовой пояс... Слава великому StackOverflow, что подсказал вписать ?useUnicode=true&serverTimezone=UTC :)
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
