package diploma.client;

import diploma.dto.MovieDTO;
import diploma.dto.UserDTO;

import diploma.server.DiplomaServiceImpl;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Scope("session")
public class UiBean {

    private boolean isDisabledRecommendButton = true;

    private UserDTO selectedUser;
    private List<UserDTO> usersList = new ArrayList<>();
    private List<MovieDTO> moviesList = new ArrayList<>();
    private List<MovieDTO> recommendationList = Collections.emptyList();

    @Autowired
    private DiplomaServiceImpl service;

    @PostConstruct
    public void init(){
        usersList = service.getAllUsers();
    }

    public void initMovies(){
        if(selectedUser != null){
            moviesList = service.getMoviesWatchedByUser(selectedUser.getId());
        }else{
            moviesList = new ArrayList<>();
        }
    }

    public long onRowSelect(SelectEvent event) {
        recommendationList = Collections.emptyList();
        selectedUser = ((UserDTO) event.getObject());
        initMovies();
        isDisabledRecommendButton = false;
        return selectedUser.getId();
    }

    public List<MovieDTO> onRecommendButtonClick(){
        recommendationList = service.getRecommendationsMovieList(selectedUser, usersList, selectedUser.getId());
        return recommendationList;
    }

    public boolean isDisabledRecommendButton() {
        return isDisabledRecommendButton;
    }

    public void setDisabledRecommendButton(boolean disabledRecommendButton) {
        isDisabledRecommendButton = disabledRecommendButton;
    }

    public List<UserDTO> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<UserDTO> usersList) {
        this.usersList = usersList;
    }

    public List<MovieDTO> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<MovieDTO> moviesList) {
        this.moviesList = moviesList;
    }

    public DiplomaServiceImpl getService() {
        return service;
    }

    public void setService(DiplomaServiceImpl service) {
        this.service = service;
    }

    public UserDTO getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDTO selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<MovieDTO> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(List<MovieDTO> recommendationList) {
        this.recommendationList = recommendationList;
    }

}
