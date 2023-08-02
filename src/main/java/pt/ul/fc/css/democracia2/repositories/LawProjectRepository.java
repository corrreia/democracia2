/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.democracia2.entities.LawProject;
import pt.ul.fc.css.democracia2.enums.FinishState;

@Repository
public interface LawProjectRepository extends JpaRepository<LawProject, Long> {

    /*
     * Finds a law project by its state
     * 
     * @param inProgress the law project's state
     */
    @Query("SELECT lp FROM LawProject lp WHERE lp.finishState = :inProgress")
    List<LawProject> findAllByFinishState(FinishState inProgress);

    /*
     * Finds a law project by its state and its end date
     * 
     * @param LocalDate the law project's end date
     */
    @Query("SELECT lp FROM LawProject lp WHERE lp.endDate < :now AND lp.finishState = 'IN_PROGRESS'")
    List<LawProject> findAllSurpassedInProgress(LocalDate now);
}
