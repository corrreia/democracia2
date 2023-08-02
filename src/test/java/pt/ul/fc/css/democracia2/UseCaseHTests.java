/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import jakarta.transaction.Transactional;
import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.LawProject;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.ThemeRepository;
import pt.ul.fc.css.democracia2.services.CitizenService;
import pt.ul.fc.css.democracia2.services.LawProjectService;
import pt.ul.fc.css.democracia2.services.ThemeService;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UseCaseHTests {

    @Autowired
    private LawProjectService lawProjectService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private DelegateRepository delegateRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Test
    public void supportALawProject() {
        // create 3 citizens
        Citizen citizen1 = citizenService.registerCitizen("Pato", "Miguel",
                "umIDQualquer");
        Citizen citizen2 = citizenService.registerCitizen("Cavaco", "Sousa",
                "umIDQualquer1");
        Citizen citizen3 = citizenService.registerCitizen("Marcelo", "Rebelo",
                "umIDQualquer2");


        Theme theme = themeService.verifyAndCreateTheme(null, "theme");

        assertEquals(1, themeRepository.findAll().size());

        Delegate delegate = new Delegate("name", "surname",
        "1234567890");
        delegateRepository.save(delegate);

        LawProject l = lawProjectService.verifyAndCreateLawProject("title", "desc", 
                LocalDate.now().minusDays(20),
                LocalDate.now().plusDays(2), theme, delegate);

        // sign the law project
        lawProjectService.signLawProject(l.getId(), citizen1.getGovernmentId());
        lawProjectService.signLawProject(l.getId(), citizen2.getGovernmentId());
        lawProjectService.signLawProject(l.getId(), citizen3.getGovernmentId());

        // check number of signatures
        assert (lawProjectService.findLawProjectById(l.getId()).getSigners().size() == 3);
    }

}
