package DB_Conection;

public class CurrentUser {
    private static CurrentUser instance;
    private String username;
    private String password;

    private CurrentUser() {
    }

    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

