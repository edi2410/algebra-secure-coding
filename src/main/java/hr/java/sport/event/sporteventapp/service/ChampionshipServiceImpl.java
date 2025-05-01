package hr.java.sport.event.sporteventapp.service;

import hr.java.sport.event.sporteventapp.domain.Championship;
import hr.java.sport.event.sporteventapp.repository.SpringChampionshipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChampionshipServiceImpl implements ChampionshipService {

    private SpringChampionshipRepository championshipRepository;
    private ClubService clubService;

    @Override
    public List<Championship> getChampionships() {
        return championshipRepository.findAll();
    }
}
