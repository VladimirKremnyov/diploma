package diploma.server;

import diploma.dao.MarkDao;
import diploma.dao.MovieDao;
import diploma.dao.UserDao;
import diploma.dto.MarkDTO;
import diploma.dto.MovieDTO;
import diploma.dto.UserDTO;
import diploma.entity.Mark;
import diploma.entity.User;


import java.util.*;

import static diploma.utils.Translator.*;

public class DiplomaServiceImpl implements DiplomaService {

    private MarkDao markDao;
    private MovieDao movieDao;
    private UserDao userDao;

    public void setMarkDao(MarkDao markDao) {
        this.markDao = markDao;
    }

    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private static Map<Integer, Double> marksMap = new HashMap<>();

    static {
        marksMap.put(1, -5.0);
        marksMap.put(2, -2.5);
        marksMap.put(3, 0.0);
        marksMap.put(4, 2.5);
        marksMap.put(5, 5.0);
    }


    public List<Long> findFiveClosestUsers(UserDTO user, List<UserDTO> allUsers) {
        long moviesCount = movieDao.getMoviesCount();
        List<Mark> allMarks = markDao.getAllMarks();
        List<Double> similarityList = similarityList(fromDTOToUser(user), fromDTOListToUserList(allUsers), moviesCount, allMarks);
        List<Long> indexesOfFiveMaxValues = new ArrayList<>();

        for (long i = 0; i < 5; i++) {
            long maxIndex = 0;

            double max = 0;
            for (long j = 0; j < similarityList.size(); j++) {
                Double currentElem = similarityList.get((int) j);
                if (currentElem == null || indexesOfFiveMaxValues.contains(j + 1)) {
                    continue;
                }
                if (currentElem > max) {
                    maxIndex = j;
                }
            }
            indexesOfFiveMaxValues.add(maxIndex + 1);
        }

        return indexesOfFiveMaxValues;

    }


    private List<Double> similarityList(User user, List<User> allUsers, long moviesCount, List<Mark> listOfMarks) {
        List<Double> similarityList = new ArrayList<>();
        for (User u : allUsers) {
            if (Objects.equals(u.getId(), user.getId())) {
                similarityList.add(null);
                continue;
            }
            similarityList.add(findCosineMeasureForTwoUsers(user, u, moviesCount, listOfMarks));
        }
        return similarityList;
    }

    private double findCosineMeasureForTwoUsers(User userA, User userB, long moviesCount, List<Mark> listOfMarks) {

        List<Double> vectorA = buildVectorForUser(userA, moviesCount, listOfMarks);
        List<Double> vectorB = buildVectorForUser(userB, moviesCount, listOfMarks);

        double ab = findVectorsMultiple(vectorA, vectorB);
        double moduleAB = findVectorsLengthsMultiple(vectorA, vectorB);
        double result = 0.0;
        if (moduleAB != 0.0) {
            result = ab / moduleAB;
        }
        return result;

    }

    private static double findVectorsLengthsMultiple(List<Double> vectorA, List<Double> vectorB) {
        double aLengthSquare = 0;
        double bLengthSquare = 0;
        for (int i = 0; i < vectorA.size(); i++) {
            aLengthSquare += Math.pow(vectorA.get(i), 2);
            bLengthSquare += Math.pow(vectorB.get(i), 2);
        }
        return Math.sqrt(aLengthSquare) * Math.sqrt(bLengthSquare);
    }

    private static double findVectorsMultiple(List<Double> vectorA, List<Double> vectorB) {
        double result = 0;
        for (int i = 0; i < vectorA.size(); i++) {
            result += vectorA.get(i) * vectorB.get(i);
        }
        return result;
    }

    private List<Double> buildVectorForUser(User user, long moviesCount, List<Mark> listOfAllMarks) {

        List<Mark> listOfMarks = marksOfEstimatedByUserMovies(listOfAllMarks, user);
        List<Double> resultList = new ArrayList<>((int) moviesCount);
        for (int i = 0; i < moviesCount; i++) {
            resultList.add(0.0);
        }

        for (Mark mark : listOfMarks) {
            resultList.set((int) (mark.getMovie().getId() - 1), marksMap.get(mark.getMark()));
        }

        return resultList;

    }

    private List<Mark> marksOfEstimatedByUserMovies(List<Mark> marks, User user) {
        List<Mark> resultList = new ArrayList<>();
        for (Mark mark : marks) {
            if (Objects.equals(mark.getUser().getId(), user.getId())) {
                resultList.add(mark);
            }
        }
        return resultList;
    }

    @Override
    public MarkDTO addMark(MarkDTO dto) {
        Mark mark = fromDTOToMark(dto);
        markDao.addMark(mark);
        return dto;
    }

    @Override
    public List<MarkDTO> getAllMarks() {
        return fromMarkListToDTOList(markDao.getAllMarks());
    }

    @Override
    public MarkDTO getMarkByMarkId(long id) {
        return fromMarkToDTO(markDao.getMarkByMarkId(id));
    }

    @Override
    public List<MarkDTO> getMarksOfEstimatedByUserMovies(UserDTO dto) {
        User user = fromDTOToUser(dto);
        return fromMarkListToDTOList(markDao.getMarksOfEstimatedByUserMovies(user));
    }

    @Override
    public List<MarkDTO> getMarksByUserId(UserDTO user) {
        return fromMarkListToDTOList(markDao.getMarksByUserId(user.getId()));
    }

    @Override
    public void updateMark(MarkDTO mark, int value) {
        markDao.updateMark(fromDTOToMark(mark), value);
    }

    @Override
    public void deleteMark(MarkDTO mark) {
        markDao.deleteMark(fromDTOToMark(mark));
    }

    @Override
    public MovieDTO addUser(MovieDTO movie) {
        movieDao.addMovie(fromDTOToMovie(movie));
        return movie;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return fromMovieListToDTOList(movieDao.getAllMovies());
    }

    @Override
    public MovieDTO getMovieByMovieId(long id) {
        return fromMovieToDTO(movieDao.getMovieByMovieId(id));
    }

    @Override
    public long getMoviesCount() {
        return movieDao.getMoviesCount();
    }

    @Override
    public List<MovieDTO> getRecommendationsMovieList(UserDTO selectedUser, List<UserDTO> usersList, long userId) {
        List<Long> ids = findFiveClosestUsers(selectedUser, usersList);
        return fromMovieListToDTOList(movieDao.getRecommendationsMovieList(ids, userId));
    }

    @Override
    public List<MovieDTO> getMoviesWatchedByUser(long userId) {
        return fromMovieListToDTOList(movieDao.getMoviesWatchedByUser(userId));
    }

    @Override
    public void updateMovie(MovieDTO movie, String name) {
        movieDao.updateMovie(fromDTOToMovie(movie), name);
    }

    @Override
    public void deleteMovie(MovieDTO movie) {
        movieDao.deleteMovie(fromDTOToMovie(movie));
    }

    @Override
    public UserDTO addUser(UserDTO user) {
        userDao.addUser(fromDTOToUser(user));
        return user;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return fromUserListToDTOList(userDao.getAllUsers());
    }

    @Override
    public UserDTO getUserByUserId(long id) {
        return fromUserToDTO(userDao.getUserByUserId(id));
    }

    @Override
    public void updateUserName(UserDTO user, String name) {
        userDao.updateUserName(fromDTOToUser(user), name);
    }

    @Override
    public void updateUserAge(UserDTO user, int age) {
        userDao.updateUserAge(fromDTOToUser(user), age);
    }

    @Override
    public void deleteUser(UserDTO user) {
        userDao.deleteUser(fromDTOToUser(user));
    }

}
