/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.repositories.ThemeRepository;
import pt.ul.fc.css.democracia2.services.ThemeService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ThemeTest {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeRepository themeRepository;
    
    @Test
    public void testTheme() {
        Theme theme = themeService.verifyAndCreateTheme(null, "Fixe");
        Theme theme2 = themeService.verifyAndCreateTheme(null, "Top");

        assertEquals(theme, themeRepository.findThemeByName("Fixe"));
        assertEquals(theme2, themeRepository.findThemeByName("Top"));
        assertEquals(2, themeRepository.findAll().size());

        themeRepository.delete(theme2);

        assertEquals(1, themeRepository.findAll().size());

        themeService.verifyAndCreateTheme(null, "Fixe");

        assertEquals(1, themeRepository.findAll().size());

    }
}
