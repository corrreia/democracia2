/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.LawProposal;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.enums.FinishState;
import pt.ul.fc.css.democracia2.enums.VoteType;
import pt.ul.fc.css.democracia2.repositories.ThemeMapperRepository;
import pt.ul.fc.css.democracia2.services.CitizenService;
import pt.ul.fc.css.democracia2.services.DelegateService;
import pt.ul.fc.css.democracia2.services.LawProposalService;
import pt.ul.fc.css.democracia2.services.ThemeService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UseCaseKTests {

    LocalDate yesterday = LocalDate.now().minusDays(1);

    @Autowired
    private LawProposalService lawProposalService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private DelegateService delegateService;

    @Autowired
    private ThemeMapperRepository themeMapperRepository;

    @Test
    public void testCloseLawProposalWithoutDelegates() {

        // create 3 citizens
        citizenService.registerCitizen("Pato", "Miguel", "umIDQualquer");
        citizenService.registerCitizen("Cavaco", "Sousa", "umIDQualquer1");
        citizenService.registerCitizen("Marcelo", "Rebelo", "umIDQualquer2");

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        // Create a law proposal
        LawProposal l = lawProposalService.verifyAndCreateLawProposal("Titulo", "Descricao",
                LocalDate.now().minusDays(2), yesterday, theme.getName(), null);

        // check if the law proposal was created
        assertEquals(1, lawProposalService.getAllProposals().size());
        assertEquals(l.getId(), lawProposalService.getLawProposalById(l.getId()).getId());

        // vote on the law proposal
        lawProposalService.voteOnLawProposal("umIDQualquer", l.getId(), VoteType.APPROVE);
        lawProposalService.voteOnLawProposal("umIDQualquer1", l.getId(), VoteType.APPROVE);
        lawProposalService.voteOnLawProposal("umIDQualquer2", l.getId(), VoteType.DISAPPROVE);

        assertEquals(3, lawProposalService.getLawProposalById(l.getId()).getVotes().size());
        assertEquals(2, lawProposalService.getLawProposalById(l.getId()).getAproveVotes());
        assertEquals(1, lawProposalService.getLawProposalById(l.getId()).getDisaproveVotes());

        // check if there are any IN_PROGRESS law proposals
        assertEquals(0, lawProposalService.getActiveLawProposals().size());

        // close the law proposal
        lawProposalService.checkLawProposals();

        // check if the law proposal is closed
        assertNotEquals(FinishState.IN_PROGRESS, lawProposalService.getLawProposalById(l.getId()).getState());

        // check if the law proposal was accepted
        assertEquals(FinishState.APPROVED, lawProposalService.getLawProposalById(l.getId()).getState());

    }

    @Test
    public void testCloseLawProposalWithDelegates1() {
        // create 5 citizens
        citizenService.registerCitizen("Pato", "Miguel", "umIDQualquer");
        citizenService.registerCitizen("Cavaco", "Sousa", "umIDQualquer1");
        citizenService.registerCitizen("Marcelo", "Rebelo", "umIDQualquer2");
        citizenService.registerCitizen("Correia", "Tomás", "umIDQualquer3");
        citizenService.registerCitizen("Santos", "Pedro", "umIDQualquer4");

        Theme theme = themeService.verifyAndCreateTheme(null, "Saude");

        // create 2 delegates
        Delegate delegate = delegateService.registerDelegate("Carlos", "Moedas", "1391497814");

        // map the citizens to the delegates
        citizenService.addThemeDelegate("umIDQualquer", "1391497814", "Saude");
        citizenService.addThemeDelegate("umIDQualquer1", "1391497814", "Saude");
        citizenService.addThemeDelegate("umIDQualquer2", "1391497814", "Saude");

        // check if the mapping was successful
        assertEquals(3, themeMapperRepository.findCitizensByDelegateAndTheme(delegate, theme).size());

        // Create a law proposal
        LawProposal l = lawProposalService.verifyAndCreateLawProposal("Aborto", "Descricao",
                LocalDate.now().minusDays(30), yesterday, theme.getName(), null);

        // citizen4 and 5 vote on the law proposal
        lawProposalService.voteOnLawProposal("umIDQualquer3", l.getId(), VoteType.APPROVE);
        lawProposalService.voteOnLawProposal("umIDQualquer4", l.getId(), VoteType.APPROVE);

        // check if there are 2 approve votes
        assertEquals(2, lawProposalService.getLawProposalById(l.getId()).getAproveVotes());
        assertEquals(0, lawProposalService.getLawProposalById(l.getId()).getDisaproveVotes());

        // check if there are any IN_PROGRESS law proposals
        assertEquals(0, lawProposalService.getActiveLawProposals().size());

        // delegate1 votes on the law proposal
        lawProposalService.voteOnLawProposal("1391497814", l.getId(), VoteType.DISAPPROVE);

        // check if there are 1 disaprove votes
        assertEquals(1, lawProposalService.getLawProposalById(l.getId()).getDisaproveVotes());

        // close the law proposal
        lawProposalService.checkLawProposals();

        // check if there are 4 disaprove votes
        assertEquals(4, lawProposalService.getLawProposalById(l.getId()).getDisaproveVotes());

        // check if the law proposal is closed
        assertNotEquals(FinishState.IN_PROGRESS, lawProposalService.getLawProposalById(l.getId()).getState());

        // check if the law proposal was rejected
        assertEquals(FinishState.REJECTED, lawProposalService.getLawProposalById(l.getId()).getState());
    }

    @Test
    public void testCloseLawProposalWithDelegates2() {
        // create 5 citizens
        citizenService.registerCitizen("Pato", "Miguel", "umIDQualquer");
        citizenService.registerCitizen("Cavaco", "Sousa", "umIDQualquer1");
        citizenService.registerCitizen("Marcelo", "Rebelo", "umIDQualquer2");
        citizenService.registerCitizen("Correia", "Tomás", "umIDQualquer3");
        citizenService.registerCitizen("Santos", "Pedro", "umIDQualquer4");

        Theme theme = themeService.verifyAndCreateTheme(null, "Saude");

        // create 2 delegates
        Delegate delegate = delegateService.registerDelegate("Carlos", "Moedas", "1391497814");

        // map the citizens to the delegates
        citizenService.addThemeDelegate("umIDQualquer", "1391497814", "Saude");

        // check if the mapping was successful
        assertEquals(1, themeMapperRepository.findCitizensByDelegateAndTheme(delegate, theme).size());

        // Create a law proposal
        LawProposal l = lawProposalService.verifyAndCreateLawProposal("Aborto", "Descricao",
                LocalDate.now().minusDays(30), yesterday, theme.getName(), null);

        // citizen4 and 5 vote on the law proposal
        lawProposalService.voteOnLawProposal("umIDQualquer1", l.getId(), VoteType.APPROVE);
        lawProposalService.voteOnLawProposal("umIDQualquer2", l.getId(), VoteType.APPROVE);
        lawProposalService.voteOnLawProposal("umIDQualquer3", l.getId(), VoteType.APPROVE);
        lawProposalService.voteOnLawProposal("umIDQualquer4", l.getId(), VoteType.APPROVE);

        // check if there are 2 approve votes
        assertEquals(4, lawProposalService.getLawProposalById(l.getId()).getAproveVotes());
        assertEquals(0, lawProposalService.getLawProposalById(l.getId()).getDisaproveVotes());

        // check if there are any IN_PROGRESS law proposals
        assertEquals(0, lawProposalService.getActiveLawProposals().size());

        // delegate1 votes on the law proposal
        lawProposalService.voteOnLawProposal("1391497814", l.getId(), VoteType.DISAPPROVE);

        // check if there are 1 disaprove votes
        assertEquals(1, lawProposalService.getLawProposalById(l.getId()).getDisaproveVotes());

        // close the law proposal
        lawProposalService.checkLawProposals();

        // check if there are 4 disaprove votes
        assertEquals(2, lawProposalService.getLawProposalById(l.getId()).getDisaproveVotes());

        // check if the law proposal is closed
        assertNotEquals(FinishState.IN_PROGRESS, lawProposalService.getLawProposalById(l.getId()).getState());

        // check if the law proposal was rejected
        assertEquals(FinishState.APPROVED, lawProposalService.getLawProposalById(l.getId()).getState());
    }

    @Test
    public void testCloseLawProposalStillTime() {

        // create 3 citizens
        citizenService.registerCitizen("Pato", "Miguel", "umIDQualquer");

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        // Create a law proposal
        LawProposal l = lawProposalService.verifyAndCreateLawProposal("Titulo", "Descricao",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(30), theme.getName(), null);

        // check if the law proposal was created
        assertEquals(1, lawProposalService.getAllProposals().size());
        assertEquals(l.getId(), lawProposalService.getLawProposalById(l.getId()).getId());

        // vote on the law proposal
        lawProposalService.voteOnLawProposal("umIDQualquer", l.getId(), VoteType.APPROVE);

        assertEquals(1, lawProposalService.getLawProposalById(l.getId()).getVotes().size());
        assertEquals(1, lawProposalService.getLawProposalById(l.getId()).getAproveVotes());
        assertEquals(0, lawProposalService.getLawProposalById(l.getId()).getDisaproveVotes());

        // check if there are any IN_PROGRESS law proposals
        assertEquals(1, lawProposalService.getActiveLawProposals().size());

        // close the law proposal
        lawProposalService.checkLawProposals();

        // check if the law proposal is closed
        assertEquals(FinishState.IN_PROGRESS, lawProposalService.getLawProposalById(l.getId()).getState());

        // check if the law proposal was accepted
        assertNotEquals(FinishState.APPROVED, lawProposalService.getLawProposalById(l.getId()).getState());

    }

}
