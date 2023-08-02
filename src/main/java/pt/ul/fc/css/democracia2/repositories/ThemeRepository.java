/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.democracia2.entities.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    /*
     * Finds a theme by its name
     * 
     * @param name the theme's name
     */
    @Query("SELECT t FROM Theme t WHERE t.name = ?1")
    Theme findThemeByName(String name);

}
