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
    @NotNull
    private String roles;
    public User() {}
    public User(String username) {}
    public User(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
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
    public void setRoles(String roles) {
        this.roles = roles;
    }
    public String getRoles() {
        return this.roles;
    }
    public String toString() {
        return "{username:" + this.username + ", password: " + this.password + ", roles: " + this.roles + "}";
    }
}
