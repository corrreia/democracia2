/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.repositories.ThemeRepository;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    /*
     * Creates a Theme and saves it to the database
     * 
     * @param parent the parent theme
     * 
     * @param name the name of the theme
     */
    public Theme verifyAndCreateTheme(Theme parent, String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty");

        Theme theme = new Theme(parent, name);
        themeRepository.save(theme);
        return theme;
    }

    /*
     * Returns the theme with the given name
     * 
     * @param name the name of the theme
     * 
     * @return the theme with the given name
     */
    public Theme getThemeByName(String name) {

        if (name == null)
            throw new IllegalArgumentException("Name cannot be null");

        Theme theme = themeRepository.findThemeByName(name);

        if (theme == null)
            throw new IllegalArgumentException("Theme not found");

        return theme;
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

}
