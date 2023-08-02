/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.democracia2.entities.Theme;

import pt.ul.fc.css.democracia2.entities.Citizen;
import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.entities.ThemeMapper;

@Repository
public interface ThemeMapperRepository extends JpaRepository<ThemeMapper, Long> {
    // ThemeMapperRepository is a JPA repository that allows us to perform CRUD
    // operations on the ThemeMapper entity

    /*
     * Finds a themeMapper by its citizen and theme
     * 
     * @param citizen the themeMapper's citizen
     * 
     * @param theme the themeMapper's theme
     */
    @Query("SELECT tm.citizen FROM ThemeMapper tm WHERE tm.delegate = :delegate AND tm.theme = :theme")
    List<Citizen> findCitizensByDelegateAndTheme(Delegate delegate, Theme theme);
}
