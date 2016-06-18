package url.genchi.entities;

/**
 * Created by mac on 2016/6/17.
 */
public class User {
    private String user;
    private Boolean isAdmin;
    public User(String user, Boolean isAdmin) {
        this.user = user;
        this.isAdmin = isAdmin;
    }
}
