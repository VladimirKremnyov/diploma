package diploma.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie extends EntityParent {

    private String name;
    private String genre;
    private List<Mark> marks;

    public Movie(){}

    @Basic
    @Column(name = "movie_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "genre", nullable = false)
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @OneToMany(orphanRemoval = true, mappedBy = "movie", fetch=FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

}
