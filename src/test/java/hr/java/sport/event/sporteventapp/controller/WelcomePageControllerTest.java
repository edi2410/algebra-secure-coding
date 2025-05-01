package hr.java.sport.event.sporteventapp.controller;

import hr.java.sport.event.sporteventapp.service.ChampionshipService;
import hr.java.sport.event.sporteventapp.service.ClubService;
import hr.java.sport.event.sporteventapp.domain.Club;
import hr.java.sport.event.sporteventapp.domain.Championship;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;


@WebMvcTest(WelcomePageController.class)
class WelcomePageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ChampionshipService championshipService;
  @MockBean
  private ClubService clubService;

  @Test
  @WithMockUser(username="testuser")
  void testShowWelcomePage() throws Exception {
    List<Championship> championships = Arrays.asList(new Championship(), new Championship());
    when(championshipService.getChampionships()).thenReturn(championships);

    mockMvc.perform(MockMvcRequestBuilders.get("/welcome"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("championshipList"))
        .andExpect(view().name("welcome"));
  }

  @Test
  @WithMockUser(username="testuser")
  void testEditClubGet() throws Exception {
    Championship c = new Championship();
    Club club = new Club();
    club.setId(1);

    when(championshipService.getChampionships()).thenReturn(Arrays.asList(c));
    when(clubService.findById(1)).thenReturn(Optional.of(club));

    mockMvc.perform(MockMvcRequestBuilders.get("/welcome/editClub.html?id=1"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("championshipList"))
        .andExpect(model().attributeExists("club"))
        .andExpect(view().name("editClub"));
  }

}