package diploma.dao;

import diploma.entity.Mark;
import diploma.entity.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class MarkDaoImpl extends AbstractDao implements MarkDao {

    public Mark addMark(Mark mark) {
        session().save(mark);
        session().flush();
        return mark;
    }

    @SuppressWarnings("unchecked")
    public List<Mark> getAllMarks() {
        return session().createQuery("from Mark m JOIN FETCH m.user JOIN FETCH m.movie").list();
    }

    public Mark getMarkByMarkId(long id) {
        return session().get(Mark.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Mark> getMarksOfEstimatedByUserMovies(User user) {
        EntityManager em = session().getEntityManagerFactory().createEntityManager();
        javax.persistence.Query query = em.createQuery("SELECT DISTINCT m FROM Mark m JOIN FETCH m.movie WHERE m.user.id = :id");
        query.setParameter("id", user.getId());
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Mark> getMarksByUserId(long id) {
        Query query = session().
                createNativeQuery("SELECT * FROM marks INNER JOIN movies ON marks.movie_id = movies.id WHERE user_id = :id");
        query.setParameter("id", id);
        return query.list();
    }

    public void updateMark(Mark mark, int value) {
        mark.setMark(value);
        session().update(mark);
    }

    public void deleteMark(Mark mark) {
        session().delete(mark);
    }

}
