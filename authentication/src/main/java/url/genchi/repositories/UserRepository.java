package url.genchi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import url.genchi.entities.User;

/**
 * Created by mac on 2016/6/5.
 */
@Transactional
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, String> {
    User getByUsername(String username);
}
