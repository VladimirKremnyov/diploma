package diploma.utils;

import diploma.entity.Mark;
import diploma.entity.Movie;
import diploma.entity.User;
import diploma.dto.MarkDTO;
import diploma.dto.MovieDTO;
import diploma.dto.UserDTO;
import diploma.server.DiplomaServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Translator {

    /**
     * From ENTITY to VO
     */
    public static MarkDTO fromMarkToDTO(Mark mark) {
        MarkDTO dto = new MarkDTO();
        dto.setId(mark.getId());
        dto.setMark(mark.getMark());
        dto.setMovieId(mark.getMovie().getId());
        dto.setUserId(mark.getUser().getId());
        return dto;
    }

    public static MovieDTO fromMovieToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setGenre(movie.getGenre());
        dto.setId(movie.getId());
        dto.setName(movie.getName());
        if (movie.getMarks() != null) {
            List<MarkDTO> marks = fromMarkListToDTOList(movie.getMarks());
            dto.setMarks(marks);
        } else {
            dto.setMarks(new ArrayList<>());
        }
        return dto;
    }

    public static UserDTO fromUserToDTO(User user) {

        List<Mark> marks = user.getMarks();
        int moviesWatched = marks.size();
        double sum = 0;
        for (Mark mark : marks) {
            sum += mark.getMark();
        }
        double averageMark = sum / moviesWatched;

        UserDTO dto = new UserDTO();
        dto.setAge(user.getAge());
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setTariff(user.getTariff());
        dto.setMoviesWatched(moviesWatched);
        dto.setAverageMark(averageMark);
        if (user.getMarks() != null) {
            List<MarkDTO> marksList = fromMarkListToDTOList(user.getMarks());
            dto.setMarks(marksList);
        } else {
            dto.setMarks(new ArrayList<>());
        }

        return dto;
    }

    /**
     * From VO to ENTITY
     */
    public static Mark fromDTOToMark(MarkDTO dto) {
        Mark mark = new Mark();
        mark.setMark(dto.getMark());
        Movie movie = new Movie();
        movie.setId(dto.getMovieId());
        mark.setMovie(movie);
        User user = new User();
        user.setId(dto.getUserId());
        mark.setUser(user);
        mark.setId(dto.getId());
        return mark;
    }

    public static Movie fromDTOToMovie(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setName(dto.getName());
        movie.setGenre(dto.getGenre());
        List<Mark> marks = fromDTOListToMarkList(dto.getMarks());
        movie.setMarks(marks);
        return movie;
    }

    public static User fromDTOToUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setTariff(dto.getTariff());
        user.setAge(dto.getAge());
        List<Mark> marks = fromDTOListToMarkList(dto.getMarks());
        user.setMarks(marks);
        return user;
    }

    public static List<Mark> fromDTOListToMarkList(List<MarkDTO> dtoList) {
        List<Mark> markList = new ArrayList<>(dtoList.size());
        for (MarkDTO dto : dtoList) {
            markList.add(fromDTOToMark(dto));
        }
        return markList;
    }

    public static List<Movie> fromDTOListToMovieList(List<MovieDTO> dtoList) {
        List<Movie> movieList = new ArrayList<>(dtoList.size());
        for (MovieDTO dto : dtoList) {
            movieList.add(fromDTOToMovie(dto));
        }
        return movieList;
    }

    public static List<User> fromDTOListToUserList(List<UserDTO> dtoList) {
        List<User> userList = new ArrayList<>(dtoList.size());
        for (UserDTO dto : dtoList) {
            userList.add(fromDTOToUser(dto));
        }
        return userList;
    }

    public static List<MarkDTO> fromMarkListToDTOList(List<Mark> marksList) {
        List<MarkDTO> dtoList = new ArrayList<>(marksList.size());
        for (Mark mark : marksList) {
            dtoList.add(fromMarkToDTO(mark));
        }
        return dtoList;
    }

    public static List<MovieDTO> fromMovieListToDTOList(List<Movie> moviesList) {
        List<MovieDTO> dtoList = new ArrayList<>(moviesList.size());
        for (Movie movie : moviesList) {
            dtoList.add(fromMovieToDTO(movie));
        }
        return dtoList;
    }

    public static List<UserDTO> fromUserListToDTOList(List<User> usersList) {
        List<UserDTO> dtoList = new ArrayList<>(usersList.size());
        for (User user : usersList) {
            dtoList.add(fromUserToDTO(user));
        }
        return dtoList;
    }

}
