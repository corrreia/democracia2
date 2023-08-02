/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.services;

import org.springframework.stereotype.Service;

import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.entities.ThemeMapper;
import pt.ul.fc.css.democracia2.repositories.ThemeMapperRepository;

@Service
public class ThemeMapperService {

    private final ThemeMapperRepository themeMapperRepository;

    public ThemeMapperService(ThemeMapperRepository themeMapperRepository) {
        this.themeMapperRepository = themeMapperRepository;
    }

    /*
     * Creates a ThemeMapper and saves it to the database
     * 
     * @param citizen the citizen that will be mapped to the theme
     * 
     * @param delegate the delegate that will be mapped to the theme
     * 
     * @param theme the theme that will be mapped to the citizen and delegate
     */
    public ThemeMapper newThemeMapper(Citizen citizen, Delegate delegate, Theme theme) {

        if (theme == null)
            throw new IllegalArgumentException("Theme cannot be null");

        if (delegate == null)
            throw new IllegalArgumentException("Delegate cannot be null");

        if (citizen == null)
            throw new IllegalArgumentException("Citizen cannot be null");

        ThemeMapper themeMapper = new ThemeMapper(delegate, theme, citizen);

        themeMapperRepository.save(themeMapper);

        return themeMapper;
    }

}
