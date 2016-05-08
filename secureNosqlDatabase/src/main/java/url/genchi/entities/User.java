package url.genchi.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String email;

    private String name;

    public User() { }

    public User(String id) {
        this.id = id;
    }

    public User(String name, String email) {
        this.email = email;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "{id: " + this.id + ", name: " + this.name + ", email: " + this.email + "}";
    }
} // class User