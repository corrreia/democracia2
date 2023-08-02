package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.democracia2.controllers.LawProjectController;
import pt.ul.fc.css.democracia2.controllers.LawProposalController;
import pt.ul.fc.css.democracia2.dto.LawProjectDTO;
import pt.ul.fc.css.democracia2.dto.LawProposalDTO;
import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.services.CitizenService;
import pt.ul.fc.css.democracia2.services.DelegateService;
import pt.ul.fc.css.democracia2.services.LawProjectService;
import pt.ul.fc.css.democracia2.services.LawProposalService;
import pt.ul.fc.css.democracia2.services.ThemeService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RestTests {

    @Autowired
    private LawProjectService lawProjectService;

    @Autowired
    private LawProjectController lawProjectController;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private DelegateService delegateService;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private LawProposalService lawProposalService;

    @Autowired
    private LawProposalController lawProposalController;

    @Test
    public void testGetActiveLawProjects() {

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        lawProjectService.verifyAndCreateLawProject("title", "desc", LocalDate.now(),
                LocalDate.now().plusDays(20), theme, delegate);

        // Perform the GET request
        ResponseEntity<?> response = lawProjectController.getActiveLawProjects();

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<LawProjectDTO> lawProjectsDTO = (List<LawProjectDTO>) response.getBody();
        assertEquals(1, lawProjectsDTO.size()); // Assuming only one law project is created

        LawProjectDTO lawProjectDTO = lawProjectsDTO.get(0);
        assertEquals("title", lawProjectDTO.getTitle()); // Replace "title" with the expected title of the law project
        assertEquals("desc", lawProjectDTO.getDescription()); // Replace "desc" with the expected description of the law
                                                              // project
    }

    @Test
    public void testGetOngoingLawProjectsEmpty() {
        // Perform the GET request
        ResponseEntity<?> response = lawProjectController.getActiveLawProjects();

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<LawProjectDTO> lawProjectsDTO = (List<LawProjectDTO>) response.getBody();
        assertEquals(0, lawProjectsDTO.size()); // Assuming only one law project is created
    }

    @Test
    @Transactional
    public void testSignLawProject_Success() throws Exception {

        // Mocking data
        Long lawProjectId = 1L;
        String citizenGov = "{\"citizenGov\":\"1234567890\"}";

        citizenService.registerCitizen("name", "surname", "1234567890");

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "12345");

        lawProjectService.verifyAndCreateLawProject("title", "desc", LocalDate.now(),
                LocalDate.now().plusDays(20), theme, delegate);

        // Perform the POST request
        ResponseEntity<?> response = lawProjectController.signLawProject(lawProjectId, citizenGov);

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lawProjectService.findLawProjectById(lawProjectId).getSigners().size(), 1);
    }

    @Test
    public void getOngoingLawProposals() {
        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        lawProposalService.verifyAndCreateLawProposal("title", "description", LocalDate.now(),
                LocalDate.now().plusDays(20), "theme", null);

        // Perform the GET request
        ResponseEntity<?> response = lawProposalController.getOngoingLawProposals();

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<LawProjectDTO> lawProjectsDTO = (List<LawProjectDTO>) response.getBody();
        assertEquals(1, lawProjectsDTO.size()); // Assuming only one law project is created
    }

    @Test
    public void getOngoingLawProposalsEmpty() {
        // Perform the GET request
        ResponseEntity<?> response = lawProposalController.getOngoingLawProposals();

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<LawProjectDTO> lawProjectsDTO = (List<LawProjectDTO>) response.getBody();
        assertEquals(0, lawProjectsDTO.size()); // Assuming only one law project is created
    }

    @Test
    public void voteOnLawProposal() {
        themeService.verifyAndCreateTheme(null, "theme");

        lawProposalService.verifyAndCreateLawProposal("title", "description", LocalDate.now(),
                LocalDate.now().plusDays(20), "theme", null);

        // Perform the POST request to vote

        citizenService.registerCitizen("name", "surname", "1234567890");

        String voteJson = "{\"citizenGov\":\"1234567890\", \"vote\":\"APROVE\"}";

        ResponseEntity<?> response = lawProposalController.voteLawProposal(1L, voteJson);

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());

        LawProposalDTO lawProposalDTO = (LawProposalDTO) response.getBody();
        assertEquals(1, lawProposalDTO.getId()); // Assuming only one law project is created

        assertEquals("1234567890",
                lawProposalService.getLawProposalById(1L).getVotes().iterator().next().getCitizen().getGovernmentId());

    }

}
