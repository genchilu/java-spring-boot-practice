package url.genchi.sql.hibernate.daos.read;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import url.genchi.sql.hibernate.entities.User;

@Transactional
public interface UserReadDao extends CrudRepository<User, Long> {
    public User getById(Long id);
    public User getByEmail(String email);
} // class UserDao

