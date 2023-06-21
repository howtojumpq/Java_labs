package Models;

public class User {
    private int id;
    private String login;
    private String password;
    private String  role;

    public User(String login, String password, String role){
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public  User() {}

    public int getID() {return id;}
    public void setID(int id) { this.id = id;}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String GetPublicInfo() {
        return "login: " + login + ", role: " + role;
    }
}
