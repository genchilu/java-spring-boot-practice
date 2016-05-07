package url.genchi.sql.hibernate.daos.write;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import url.genchi.sql.hibernate.entities.User;

@Transactional
public interface UserWriteDao extends CrudRepository<User, Long> {
    public User getById(Long id);
    public User getByEmail(String email);
} // class UserDao

