package diploma.server;

import diploma.dto.MarkDTO;
import diploma.dto.MovieDTO;
import diploma.dto.UserDTO;
import diploma.entity.Movie;

import java.util.List;

public interface DiplomaService {

    MarkDTO addMark(MarkDTO mark);

    List<MarkDTO> getAllMarks();

    MarkDTO getMarkByMarkId(long id);

    List<MarkDTO> getMarksOfEstimatedByUserMovies(UserDTO user);

    List<MarkDTO> getMarksByUserId(UserDTO user);

    void updateMark(MarkDTO mark, int value);

    void deleteMark(MarkDTO mark);

    MovieDTO addUser(MovieDTO movie);

    List<MovieDTO> getAllMovies();

    MovieDTO getMovieByMovieId(long id);

    long getMoviesCount();

    List<MovieDTO> getRecommendationsMovieList(UserDTO selectedUser, List<UserDTO> usersList, long userId);

    public List<MovieDTO> getMoviesWatchedByUser(long userId);

    void updateMovie(MovieDTO movie, String name);

    void deleteMovie(MovieDTO movie);

    UserDTO addUser(UserDTO user);

    List<UserDTO> getAllUsers();

    UserDTO getUserByUserId(long id);

    void updateUserName(UserDTO user, String name);

    void updateUserAge(UserDTO user, int age);

    void deleteUser(UserDTO user);

}
