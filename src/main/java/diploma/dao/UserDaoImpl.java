package diploma.dao;

import diploma.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl extends AbstractDao implements UserDao {

    public User addUser(User user) {
        session().save(user);
        session().flush();
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        EntityManager em = session().getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT DISTINCT u FROM User u JOIN FETCH u.marks mark JOIN FETCH mark.movie", User.class).getResultList();
    }

    public User getUserByUserId(long id) {
        return session().get(User.class, id);
    }

    public void updateUserName(User user, String name) {
        user.setName(name);
        session().update(user);
    }

    public void updateUserAge(User user, int age) {
        user.setAge(age);
        session().update(user);
    }

    public void deleteUser(User user) {
        session().delete(user);
    }

}
