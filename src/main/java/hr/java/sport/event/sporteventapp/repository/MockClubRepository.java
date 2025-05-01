package hr.java.sport.event.sporteventapp.repository;

import hr.java.sport.event.sporteventapp.domain.Championship;
import hr.java.sport.event.sporteventapp.domain.Club;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MockClubRepository implements ClubRepository {

    private static List<Club> championshipClubs = new ArrayList<>();

    static {

        Championship championship = new Championship();
        championship.setName("Hrvatska nogometna liga");
        championship.setId(1);
        championship.setClubList(championshipClubs);


        Club firstClub = new Club();
        Club secondClub = new Club();

        firstClub.setId(1);
        firstClub.setName("Dinamo Zagreb");

        secondClub.setId(2);
        secondClub.setName("HNK Rijeka");

        championshipClubs.add(firstClub);
        championshipClubs.add(secondClub);
    }

    @Override
    public List<Club> findAll() {
        return championshipClubs;
    }

    @Override
    public void update(Club updatedClub) {
        Optional<Club> clubOptional = championshipClubs.stream().filter(c -> c.getId().equals(
                updatedClub.getId())).findFirst();

        if(clubOptional.isPresent()) {
            Club clubToUpdate = clubOptional.get();
            clubToUpdate.setName(updatedClub.getName());
        }
    }

    @Override
    public void save(Club newClub) {
        championshipClubs.add(newClub);
    }
}
