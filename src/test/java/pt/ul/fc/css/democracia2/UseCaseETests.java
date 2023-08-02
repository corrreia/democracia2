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

import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.LawProject;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.services.DelegateService;
import pt.ul.fc.css.democracia2.services.LawProjectService;
import pt.ul.fc.css.democracia2.services.ThemeService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UseCaseETests {

    @Autowired
    private LawProjectService lawProjectService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private DelegateService delegateService;

    @Test
    public void testCreateLawProject() {

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now(),
                LocalDate.now().plusDays(20), theme, delegate);

        List<LawProject> lawProjects = lawProjectService.findAllActive();

        assertEquals(1, lawProjects.size());
        assertEquals("title", lawProjects.get(0).getTitle());
        assertEquals("desc", lawProjects.get(0).getDescription());
        assertEquals(LocalDate.now(), lawProjects.get(0).getStartDate());
        assertEquals(LocalDate.now().plusDays(20), lawProjects.get(0).getEndDate());
        assertEquals(theme, lawProjects.get(0).getTheme());
        assertEquals(delegate, lawProjects.get(0).getDelegate());
    }

    @Test
    public void testCreateLawProjectWithNullDelegate() {

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        assertThrows(IllegalArgumentException.class, () -> {
            lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now(),
                    LocalDate.now().plusDays(20), theme, null);
        });
    }

    @Test
    public void testCreateLawProjectWithNullTheme() {

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        assertThrows(IllegalArgumentException.class, () -> {
            lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now(),
                    LocalDate.now().plusDays(20), null, delegate);
        });
    }

    @Test
    public void testCreateLawProjectWithNullTitle() {

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        assertThrows(IllegalArgumentException.class, () -> {
            lawProjectService.verifyAndCreateLawProject(null, "desc",  LocalDate.now(),
                    LocalDate.now().plusDays(20), theme, delegate);
        });
    }

    @Test
    public void testCreateLawProjectWithEmptyTitle() {

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        assertThrows(IllegalArgumentException.class, () -> {
            lawProjectService.verifyAndCreateLawProject("", "desc",  LocalDate.now(),
                    LocalDate.now().plusDays(20), theme, delegate);
        });
    }

    @Test
    public void testCreateTwoLawProjects() {

        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        Delegate delegate = delegateService.registerDelegate("name", "surname", "1234567890");

        lawProjectService.verifyAndCreateLawProject("title", "desc",  LocalDate.now(),
                LocalDate.now().plusDays(20), theme, delegate);

        lawProjectService.verifyAndCreateLawProject("title2", "desc2",  LocalDate.now(),
                LocalDate.now().plusDays(20), theme, delegate);

        List<LawProject> lawProjects = lawProjectService.findAllActive();

        assertEquals(2, lawProjects.size());
        assertEquals("title", lawProjects.get(0).getTitle());
        assertEquals("desc", lawProjects.get(0).getDescription());
        assertEquals(LocalDate.now(), lawProjects.get(0).getStartDate());
        assertEquals(LocalDate.now().plusDays(20), lawProjects.get(0).getEndDate());
        assertEquals(theme, lawProjects.get(0).getTheme());
        assertEquals(delegate, lawProjects.get(0).getDelegate());

        assertEquals("title2", lawProjects.get(1).getTitle());
        assertEquals("desc2", lawProjects.get(1).getDescription());
        assertEquals(LocalDate.now(), lawProjects.get(1).getStartDate());
        assertEquals(LocalDate.now().plusDays(20), lawProjects.get(1).getEndDate());
        assertEquals(theme, lawProjects.get(1).getTheme());
        assertEquals(delegate, lawProjects.get(1).getDelegate());
    }

}
