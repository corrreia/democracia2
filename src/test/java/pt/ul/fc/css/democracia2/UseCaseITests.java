/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.ThemeMapperRepository;
import pt.ul.fc.css.democracia2.repositories.ThemeRepository;
import pt.ul.fc.css.democracia2.services.CitizenService;
import pt.ul.fc.css.democracia2.entities.Theme;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UseCaseITests {

    @Autowired
    private DelegateRepository delegateRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private ThemeMapperRepository themeMapperRepository;

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testTwoWayDataBinding() {
        Delegate delegate = new Delegate("name", "surname", "1234567890");
        delegateRepository.save(delegate);

        Theme theme = new Theme(null, "theme");
        themeRepository.save(theme);

        Citizen citizen = new Citizen("name", "surname", "123456unique");
        citizenRepository.save(citizen);

        citizenService.addThemeDelegate(citizen.getGovernmentId(), delegate.getGovernmentId(), theme.getName());

        // count the number of theme mappers
        assertEquals(1, themeMapperRepository.count());

        Citizen citizen2 = citizenRepository.findByGovernmentId("123456unique");

        // check if the citizen has the theme mapper
        assertEquals(1, citizen2.getThemeMappers().size());

        // check if the theme has the theme mapper
        // assertEquals(1, themeRepository.findAll().get(0).getThemeMappers().size());

        // check if the delegate has the theme mapper
        assertEquals(1, delegateRepository.findAll().get(0).getCitizenThemeMappers().size());

    }

    @Test
    public void testDelegateOnCitizen() {
        Theme theme = new Theme(null, "theme");
        themeRepository.save(theme);

        Delegate delegate = new Delegate("name", "surname", "1234567890");
        delegateRepository.save(delegate);

        Citizen citizen = new Citizen("name", "surname", "123456unique");
        citizenRepository.save(citizen);

        assertThrows(ClassCastException.class, () -> {
            citizenService.addThemeDelegate("1234567890", "123456unique", "theme");
        });

    }

    @Test
    public void testDelegateGovIdEqualsCitizen() {
        Delegate delegate = new Delegate("name", "surname", "1234567890");
        delegateRepository.save(delegate);

        Theme theme = new Theme(null, "theme");
        themeRepository.save(theme);

        Citizen citizen = new Citizen("name", "surname", "1234567890");
        citizenRepository.save(citizen);

        assertThrows(IllegalArgumentException.class, () -> {
            citizenService.addThemeDelegate(citizen.getGovernmentId(), delegate.getGovernmentId(), theme.getName());
        });
    }
}
