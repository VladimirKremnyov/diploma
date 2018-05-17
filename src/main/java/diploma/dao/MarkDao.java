package diploma.dao;

import diploma.entity.Mark;
import diploma.entity.User;

import java.util.List;

public interface MarkDao {

    Mark addMark(Mark mark);

    List<Mark> getAllMarks();

    Mark getMarkByMarkId(long id);

    List<Mark> getMarksOfEstimatedByUserMovies(User user);

    List<Mark> getMarksByUserId(long id);

    void updateMark(Mark mark, int value);

    void deleteMark(Mark mark);

}
