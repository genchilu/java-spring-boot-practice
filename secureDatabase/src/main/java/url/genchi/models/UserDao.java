package url.genchi.models;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDao {

    @Autowired
    @Qualifier(value = "read")
    private SessionFactory _readSessionFactory;

    @Autowired
    @Qualifier(value = "write")
    private SessionFactory _writeSessionFactory;

    private Session getReadSession() {
        return _readSessionFactory.getCurrentSession();
    }

    private Session getWriteSession() {
        return _writeSessionFactory.getCurrentSession();
    }

    @Transactional
    public void saveAtReadSession(User user) {
        getReadSession().save(user);
        return;
    }

    public void saveAtWriteSession(User user) {
        getWriteSession().save(user);
        return;
    }

    public User getById(long id) {
        return (User) getReadSession().get(User.class, id);
    }

    public void update(User user) {
        getWriteSession().update(user);
        return;
    }
} // class UserDao

