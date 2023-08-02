/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.LawProject;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.enums.FinishState;
import pt.ul.fc.css.democracia2.repositories.LawProjectRepository;
import pt.ul.fc.css.democracia2.services.DelegateService;
import pt.ul.fc.css.democracia2.services.LawProjectService;
import pt.ul.fc.css.democracia2.services.ThemeService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UseCaseFTests {


    @Autowired
    private LawProjectService lawProjectService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private DelegateService delegateService;

    @Autowired
    private LawProjectRepository lawProjectRepository;

    @Test
    public void testOneExpiredProject() {
        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(2), theme, delegate);

        // call the function that runs in a scheduled task
        List<LawProject> expiredLawProjects = lawProjectRepository.findAllSurpassedInProgress(
                LocalDate.now());
        assertEquals(1, expiredLawProjects.size());

        lawProjectService.closeExpiredLawProjects();

        List<LawProject> projects = lawProjectRepository.findAll();

        assertEquals(1, projects.size());
        assertEquals(FinishState.REJECTED, projects.get(0).getState());
    }

    @Test
    public void testNoExpiredProjects() {
        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().plusDays(2), theme, delegate);

        // call the function that runs in a scheduled task
        List<LawProject> expiredLawProjects = lawProjectRepository.findAllSurpassedInProgress(
                LocalDate.now());
        assertEquals(0, expiredLawProjects.size());

        lawProjectService.closeExpiredLawProjects();

        List<LawProject> projects = lawProjectRepository.findAll();

        assertEquals(1, projects.size());
        assertEquals(FinishState.IN_PROGRESS, projects.get(0).getState());
    }

    @Test
    public void testNoInProgressProjects() {
        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(2), theme, delegate);

        // call the function that runs in a scheduled task
        List<LawProject> expiredLawProjects = lawProjectRepository.findAllSurpassedInProgress(
                LocalDate.now());
        assertEquals(1, expiredLawProjects.size());

        lawProjectService.closeExpiredLawProjects();

        List<LawProject> projects = lawProjectRepository.findAll();

        assertEquals(1, projects.size());
        assertEquals(FinishState.REJECTED, projects.get(0).getState());
    }

}
