package hr.java.sport.event.sporteventapp.controller;

import hr.java.sport.event.sporteventapp.domain.Championship;
import hr.java.sport.event.sporteventapp.domain.Club;
import hr.java.sport.event.sporteventapp.service.ChampionshipService;
import hr.java.sport.event.sporteventapp.service.ClubService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/welcome")
public class WelcomePageController {

    private final ChampionshipService championshipService;
    private final ClubService clubService;

    public WelcomePageController(final ChampionshipService championshipService, final ClubService clubService) {
        this.championshipService = championshipService;
        this.clubService = clubService;
    }

    @GetMapping
    public String showWelcomePage(Model model) {
        List<Championship> championshipList = championshipService.getChampionships();
        model.addAttribute("championshipList", championshipList);
        return "welcome";
    }

    @GetMapping("/editClub.html")
    public String editClub(@RequestParam Integer id, Model model) {
        List<Championship> championshipList = championshipService.getChampionships();
        Optional<Club> maybeClub = clubService.findById(id);

        if (maybeClub.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Club Not Found");
        }

        model.addAttribute("championshipList", championshipList);
        model.addAttribute("club", maybeClub.get());
        return "editClub";
    }


    @PostMapping("/editClub.html")
    public String editClub(@ModelAttribute Club club, Model model) {
        clubService.update(club);
        return "redirect:/welcome";
    }

    @PostMapping("/addNewClub.html")
    public String addNewClub(@ModelAttribute Club club, Model model) {
        clubService.save(club);
        return "redirect:/welcome";
    }


}
