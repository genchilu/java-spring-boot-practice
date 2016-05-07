package url.genchi.sql.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    @Qualifier("readJdbc")
    JdbcTemplate readJdbcTemplate;

    @Autowired
    @Qualifier("writeJdbc")
    JdbcTemplate writeJdbcTemplate;

    @RequestMapping(value = "/unsecure/get", method = RequestMethod.GET)
    @Transactional
    //it would be dangerous while name is %27%20OR%20%271%27=%271
    public String getunsecure(@RequestParam("name") String name) {
        return readJdbcTemplate.query("SELECT * FROM USERS WHERE NAME = '" + name + "'", new UserRowMapper()).toString();
    }

    @RequestMapping(value = "/secure/get", method = RequestMethod.GET)
    @Transactional
    public String getsecure(@RequestParam("name") String name) {
        name = name.replaceAll("'", "''");
        return readJdbcTemplate.query("SELECT * FROM USERS WHERE NAME = '" + name + "'", new UserRowMapper()).toString();
    }

    @RequestMapping(value = "/preparedstatement/get", method = RequestMethod.GET)
    @Transactional
    public String getpreparedstatement(@RequestParam("name") String name) {
        return readJdbcTemplate.query("SELECT * FROM USERS WHERE NAME = ?", new Object[] {name}, new UserRowMapper()).toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String add(@RequestParam("email") String email, @RequestParam("name") String name) {
        try {
            Long id = writeJdbcTemplate.queryForObject("select nextval ('hibernate_sequence')", Long.class);
            writeJdbcTemplate.execute("INSERT INTO USERS (ID, NAME, EMAIL) VALUES(" + id + ", '" + name  + "', '" + email + "')");
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return "User succesfully saved!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    class UserRowMapper implements RowMapper<User>
    {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.id = rs.getInt("id");
            user.name = rs.getString("name");
            user.email = rs.getString("email");
            return user;
        }
    }

    public class User
    {
        public Integer id;
        public String name;
        public String email;
        public String toString() {
            return "{id: " + Long.toString(this.id) + ", name: " + this.name + ", email: " + this.email + "}";
        }
    }
}

