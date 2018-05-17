package diploma.dao;

import diploma.entity.Movie;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional
public class MovieDaoImpl extends AbstractDao implements MovieDao {

    public Movie addMovie(Movie movie) {
        session().save(movie);
        session().flush();
        return movie;
    }

    @SuppressWarnings("unchecked")
    public List<Movie> getAllMovies() {
        EntityManager em = session().getEntityManagerFactory().createEntityManager();
        List<Movie> movies = em.createQuery("SELECT DISTINCT m FROM Movie m JOIN FETCH m.marks", Movie.class).getResultList();
        return movies;
    }

    public Movie getMovieByMovieId(long id) {
        return session().get(Movie.class, id);
    }

    public long getMoviesCount() {
        EntityManager em = session().getEntityManagerFactory().createEntityManager();
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Movie.class)));
        return em.createQuery(cq).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Movie> getRecommendationsMovieList(List<Long> ids, long userId) {
        EntityManager em = session().getEntityManagerFactory().createEntityManager();
        TypedQuery query = em.createQuery("SELECT DISTINCT m FROM Movie m " +
                "JOIN FETCH m.marks mar JOIN FETCH mar.user WHERE mar.user.id IN (?1) " +
                "AND m.name NOT IN (SELECT m.name FROM Movie m INNER JOIN " +
                "m.marks mar WHERE mar.user.id = (?2))", Movie.class);
        query.setParameter("1",ids);
        query.setParameter("2", userId);
        query.setMaxResults(5);
        return query.getResultList();

    }

    @SuppressWarnings("unchecked")
    public List<Movie> getMoviesWatchedByUser(long userId) {
        EntityManager em = session().getEntityManagerFactory().createEntityManager();
        TypedQuery query = em.createQuery("SELECT DISTINCT movie FROM Movie movie JOIN FETCH movie.marks mark JOIN FETCH mark.user WHERE mark.user.id =:userId", Movie.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public void updateMovie(Movie movie, String name) {
        movie.setName(name);
        session().update(movie);
    }

    public void deleteMovie(Movie movie) {
        session().delete(movie);
    }

}
