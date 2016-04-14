package url.genchi.models;


import java.util.List;




import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "read")
public class UserDao {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Transactional(value = "write")
    public void save(User user) {
        getSession().save(user);
        return;
    }

    public void testSave(User user) {
        getSession().save(user);
        return;
    }

    public void delete(User user) {
        getSession().delete(user);
        return;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return getSession().createQuery("from User").list();
    }

    public User getByEmail(String email) {
        return (User) getSession().createQuery(
            "from User where email = :email")
            .setParameter("email", email)
            .uniqueResult();
    }

    public User getById(long id) {
        return (User) getSession().get(User.class, id);
    }

    public void update(User user) {
        getSession().update(user);
        return;
    }

    //it is not secure enough when other method that is not readonrl call this method
    @Transactional(readOnly = true)
    public void readOnlysave(User user) {
        getSession().save(user);
        return;
    }

    public void overrideReadOnlysave(User user) {
        readOnlysave(user);
        return;
    }
} // class UserDao

