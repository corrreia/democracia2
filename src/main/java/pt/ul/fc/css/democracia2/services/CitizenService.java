/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */

package pt.ul.fc.css.democracia2.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.Theme;
import pt.ul.fc.css.democracia2.entities.ThemeMapper;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.ThemeMapperRepository;
import pt.ul.fc.css.democracia2.repositories.ThemeRepository;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;

    private final ThemeRepository themeRepository;

    private final ThemeMapperRepository themeMapperRepository;

    CitizenService(CitizenRepository citizenRepository,
            ThemeRepository themeRepository, ThemeMapperRepository themeMapperRepository) {
        this.citizenRepository = citizenRepository;
        this.themeRepository = themeRepository;
        this.themeMapperRepository = themeMapperRepository;
    }

    /*
     * Creates a Citizen and saves it to the database
     * 
     * @param surname the surname of the citizen
     * 
     * @param name the name of the citizen
     * 
     * @param governmentId the government ID of the citizen (must be unique)
     * 
     * @return the citizen
     */
    public Citizen registerCitizen(String surname, String name, String governmentId) {
        if (citizenRepository.findByGovernmentId(governmentId) != null)
            throw new IllegalArgumentException("Citizen already registered");

        if (surname == null || surname.isEmpty())
            throw new IllegalArgumentException("Surname cannot be empty");

        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");

        if (governmentId == null || governmentId.isEmpty())
            throw new IllegalArgumentException("Government ID cannot be empty");

        Citizen citizen = new Citizen(surname, name, governmentId);
        citizenRepository.save(citizen);
        return citizen;
    }

    /*
     * Finds a citizen by its government ID
     * 
     * @param governmentId the government ID of the citizen
     * 
     * @return the citizen
     */
    public Citizen findCitizenByGovernmentId(String governmentId) {
        Citizen citizen = citizenRepository.findByGovernmentId(governmentId);
        if (citizen == null)
            throw new IllegalArgumentException("Citizen not found");
        return citizen;
    }

    /*
     * Adds a theme-delegate relationship to the citizen and saves it to the
     * database
     * 
     * @param citizenGovId the government ID of the citizen
     * 
     * @param delegateGovId the government ID of the delegate
     * 
     * @param themeName the name of the theme
     * 
     * @return the citizen
     */
    public Citizen addThemeDelegate(String citizenGovId, String delegateGovId, String themeName) {

        if (citizenGovId.equals(delegateGovId))
            throw new IllegalArgumentException("Citizen and delegate cannot be the same");

        if (citizenGovId == null || citizenGovId.isEmpty())
            throw new IllegalArgumentException("Citizen government ID cannot be empty");

        if (delegateGovId == null || delegateGovId.isEmpty())
            throw new IllegalArgumentException("Delegate government ID cannot be empty");

        if (themeName == null || themeName.isEmpty())
            throw new IllegalArgumentException("Theme name cannot be empty");

        Citizen citizen = findCitizenByGovernmentId(citizenGovId);

        Delegate delegate = (Delegate) findCitizenByGovernmentId(delegateGovId);

        if (delegate.getClass() != Delegate.class)
            throw new IllegalArgumentException("Government ID does not belong to a delegate");

        Theme theme = themeRepository.findThemeByName(themeName);

        if (theme == null)
            throw new IllegalArgumentException("Theme not found");

        ThemeMapper themeMapper = new ThemeMapper(delegate, theme, citizen);
        themeMapperRepository.save(themeMapper);

        Set<ThemeMapper> themeMappers = citizen.getThemeMappers();
        if (!themeMappers.add(themeMapper))
            throw new IllegalArgumentException("Citizen already has this theme mapper");
        citizen.setThemeMappers(themeMappers);
        citizenRepository.save(citizen);
        return citizen;
    }
}
