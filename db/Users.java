package db;

public class Users {

    private String UserName;
    private String Login;
    private String Pass;

    public Users(String userName, String login, String pass) {
        UserName = userName;
        Login = login;
        Pass = pass;
    }


    public String getUserName() {
        return UserName;
    }

    public String getLogin() {
        return Login;
    }

    public String getPass() {
        return Pass;
    }

    @Override
    public String toString() {
        return "Users{" +
                "UserName='" + UserName + '\'' +
                ", Login='" + Login + '\'' +
                ", Pass='" + Pass + '\'' +
                '}';
    }
}
