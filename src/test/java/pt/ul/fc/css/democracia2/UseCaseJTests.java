/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.LawProposal;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.enums.FinishState;
import pt.ul.fc.css.democracia2.enums.VoteType;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.LawProposalRepository;
import pt.ul.fc.css.democracia2.services.CitizenService;
import pt.ul.fc.css.democracia2.services.LawProposalService;
import pt.ul.fc.css.democracia2.services.ThemeService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UseCaseJTests {

    @Autowired
    private LawProposalService lawProposalService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private LawProposalRepository lawProposalRepository;

    @Autowired
    private DelegateRepository delegateRepository;

    @Test
    public void testVoteOnLawProposal() {

        // create 3 citizens
        citizenService.registerCitizen("Pato", "Miguel", "umIDQualquer");
        citizenService.registerCitizen("Cavaco", "Sousa", "umIDQualquer1");
        citizenService.registerCitizen("Marcelo", "Rebelo", "umIDQualquer2");

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        // Create a law proposal
        LawProposal l = lawProposalService.verifyAndCreateLawProposal("Titulo", "Descricao",
                LocalDate.now(), LocalDate.now().plusDays(20), theme.getName(), null);

        // check if the law proposal was created
        assertEquals(l.getId(), lawProposalService.getLawProposalById(l.getId()).getId());

        // vote on the law proposal
        lawProposalService.voteOnLawProposal("umIDQualquer", l.getId(), VoteType.APPROVE);
        lawProposalService.voteOnLawProposal("umIDQualquer1", l.getId(), VoteType.APPROVE);
        lawProposalService.voteOnLawProposal("umIDQualquer2", l.getId(), VoteType.DISAPPROVE);

        assertEquals(3, lawProposalService.getLawProposalById(l.getId()).getVotes().size());
        assertEquals(2, lawProposalService.getLawProposalById(l.getId()).getAproveVotes());
        assertEquals(1, lawProposalService.getLawProposalById(l.getId()).getDisaproveVotes());
    }

    @Test
    public void testVoteOnClosedProposal() {

        // create 3 citizens
        Citizen citizen1 = citizenService.registerCitizen("Pato", "Miguel", "umIDQualquer");
        Citizen citizen2 = citizenService.registerCitizen("Cavaco", "Sousa", "umIDQualquer1");
        Citizen citizen3 = citizenService.registerCitizen("Marcelo", "Rebelo", "umIDQualquer2");

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        // Create a law proposal
        LawProposal l = new LawProposal("Titulo", "Descricao", LocalDate.now().minusDays(20),
                LocalDate.now().plusDays(4), theme, null);

        l.setState(FinishState.APPROVED);

        lawProposalRepository.save(l);

        // check if the law proposal was created
        assertEquals(l.getId(), lawProposalService.getLawProposalById(l.getId()).getId());

        // vote on the law proposal
        assertThrows(IllegalArgumentException.class, () -> {
            lawProposalService.voteOnLawProposal(citizen1.getGovernmentId(), l.getId(), VoteType.APPROVE);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            lawProposalService.voteOnLawProposal(citizen2.getGovernmentId(), l.getId(), VoteType.APPROVE);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            lawProposalService.voteOnLawProposal(citizen3.getGovernmentId(), l.getId(), VoteType.DISAPPROVE);
        });
    }

    @Test
    public void testCitizenVotes2Times() {

        // create 3 citizens
        Citizen citizen = citizenService.registerCitizen("Pato", "Miguel", "umIDQualquer");

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        // Create a law proposal
        LawProposal l = lawProposalService.verifyAndCreateLawProposal("Titulo", "Descricao",
                LocalDate.now(), LocalDate.now().plusDays(20), theme.getName(), null);

        // check if the law proposal was created
        assertEquals(l.getId(), lawProposalService.getLawProposalById(l.getId()).getId());

        // vote on the law proposal 2 times

        lawProposalService.voteOnLawProposal(citizen.getGovernmentId(), l.getId(), VoteType.APPROVE);

        assertEquals(1, lawProposalService.getLawProposalById(l.getId()).getVotes().size());

        assertThrows(IllegalArgumentException.class, () -> {
            lawProposalService.voteOnLawProposal(citizen.getGovernmentId(), l.getId(), VoteType.APPROVE);
        });
    }

    @Test
    public void testDelegateVotes() {

        // create 3 citizens
        Delegate dele = new Delegate("Pato", "Miguel", "umIDQualquer");
        delegateRepository.save(dele);

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        // Create a law proposal
        LawProposal l = lawProposalService.verifyAndCreateLawProposal("Titulo", "Descricao",
                LocalDate.now(), LocalDate.now().plusDays(20), theme.getName(), null);

        // check if the law proposal was created
        assertEquals(l.getId(), lawProposalService.getLawProposalById(l.getId()).getId());

        // vote on the law proposal 2 times

        lawProposalService.voteOnLawProposal(dele.getGovernmentId(), l.getId(), VoteType.APPROVE);

        assertEquals(1, lawProposalService.getLawProposalById(l.getId()).getVotes().size());

        assertThrows(IllegalArgumentException.class, () -> {
            lawProposalService.voteOnLawProposal(dele.getGovernmentId(), l.getId(), VoteType.APPROVE);
        });

        LawProposal lawProp = lawProposalService.getLawProposalById(l.getId());

        assertEquals(1, lawProp.getVotes().size());

        // verify if the vote is from the delegate (Delegate.class

        assertEquals(1, lawProp.getVotes().stream().filter(v -> v.getCitizen().getClass().equals(Delegate.class))
                .collect(Collectors.toList()).size());

    }
}