package url.genchi.entities;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by mac on 2016/6/5.
 */
@Entity
@Table(name="AUTH_USER")
public class User {
    @Id
    private String username;
    @NotNull
    private String password;
    public User() {}
    public User(String username) {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }
    public String toString() {
        return "{username:" + this.username + ", password: " + this.password + "}";
    }
}
