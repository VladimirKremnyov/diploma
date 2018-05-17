package diploma.dao;

import diploma.entity.Movie;

import java.util.List;


public interface MovieDao {

    Movie addMovie(Movie movie);

    List<Movie> getAllMovies();

    Movie getMovieByMovieId(long id);

    long getMoviesCount();

    List<Movie> getRecommendationsMovieList(List<Long> ids, long userId);

    List<Movie> getMoviesWatchedByUser(long userId);

    void updateMovie(Movie movie, String name);

    void deleteMovie(Movie movie);

}
