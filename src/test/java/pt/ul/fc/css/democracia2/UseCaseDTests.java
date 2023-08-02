/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import pt.ul.fc.css.democracia2.entities.LawProposal;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.services.LawProposalService;
import pt.ul.fc.css.democracia2.services.ThemeService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UseCaseDTests {

    @Autowired
    private LawProposalService lawProposalService;

    @Autowired
    private ThemeService themeService;

    @Test
    public void testListProposalsTest() {

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        lawProposalService.verifyAndCreateLawProposal("title", "description", LocalDate.now(),
                LocalDate.now().plusDays(20), "theme", null);

        List<LawProposal> proposals = lawProposalService.getActiveLawProposals();

        // assert that the list contains only the active proposal
        assertEquals(1, proposals.size());
        assertEquals("title", proposals.get(0).getTitle());
        assertEquals("description", proposals.get(0).getDescription());
        assertEquals(theme, proposals.get(0).getTheme());
    }

    @Test
    public void testListProposalsTestWithInactiveProposal() {

        themeService.verifyAndCreateTheme(null, "theme");

        assertThrows(IllegalArgumentException.class,
                () -> lawProposalService.verifyAndCreateLawProposal("title", "description",
                        LocalDate.now(),
                        LocalDate.now().minusDays(20), "theme", null));

        List<LawProposal> proposals = lawProposalService.getActiveLawProposals();

        // assert that the list contains only the active proposal
        assertEquals(0, proposals.size());
    }
}
