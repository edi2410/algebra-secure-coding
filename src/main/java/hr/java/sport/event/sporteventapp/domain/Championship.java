package hr.java.sport.event.sporteventapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Championship {
    @Id
    private Integer id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "CHAMPIONSHIP_CLUB",
            joinColumns = @JoinColumn(name = "CHAMPIONSHIP_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLUB_ID"))
    private List<Club> clubList;

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public List<Club> getClubList() {
        return clubList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

}
