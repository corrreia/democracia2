/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import pt.ul.fc.css.democracia2.repositories.LawProjectRepository;
import pt.ul.fc.css.democracia2.services.DelegateService;
import pt.ul.fc.css.democracia2.services.LawProjectService;
import pt.ul.fc.css.democracia2.services.ThemeService;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.LawProject;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.enums.FinishState;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UseCaseGTests {

    @Autowired
    private LawProjectService lawProjectService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private LawProjectRepository lawProjectRepository;

    @Autowired
    private DelegateService delegateService;

    @Test
    public void testListActiveLawProjects() {

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().plusDays(2), theme, delegate);

        List<LawProject> activeLawProjects = lawProjectService.findAllActive();

        assertEquals(1, activeLawProjects.size());
    }

    @Test
    public void testListExpiredLawProjects() {

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        LawProject lawProject = new LawProject("null", "null",  LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(2), theme, delegate);

        lawProject.setState(FinishState.REJECTED);

        lawProjectRepository.save(lawProject);

        List<LawProject> activeLawProjects = lawProjectService.findAllActive();

        assertEquals(0, activeLawProjects.size());
    }

    @Test
    public void testFourExpiredProjects() {
        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(2), theme, delegate);

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(2), theme, delegate);

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(2), theme, delegate);

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(2), theme, delegate);

        // call the function that runs in a scheduled task
        List<LawProject> expiredLawProjects = lawProjectRepository.findAllSurpassedInProgress(
                LocalDate.now());
        assertEquals(4, expiredLawProjects.size());

        lawProjectService.closeExpiredLawProjects();

        List<LawProject> projects = lawProjectRepository.findAll();

        assertEquals(4, projects.size());
    }

    @Test
    public void testTwoExpiredOneValid() {
        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(2), theme, delegate);

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(2), theme, delegate);

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now().minusDays(20),
                LocalDate.now().plusDays(2), theme, delegate);

        // call the function that runs in a scheduled task
        List<LawProject> expiredLawProjects = lawProjectRepository.findAllSurpassedInProgress(
                LocalDate.now());
        assertEquals(2, expiredLawProjects.size());

        lawProjectService.closeExpiredLawProjects();

        List<LawProject> projects = lawProjectRepository.findAll();

        assertEquals(3, projects.size());
    }

}
