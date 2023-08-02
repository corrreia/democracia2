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

import pt.ul.fc.css.democracia2.entities.LawProposal;

@Repository
public interface LawProposalRepository extends JpaRepository<LawProposal, Long> {

    /*
     * Finds a law proposal end date greater than a given date
     * 
     * @param LocalDate the law proposal's end date
     */
    @Query("SELECT lp FROM LawProposal lp WHERE lp.endDate > ?1")
    List<LawProposal> findByEndDateGreaterThan(LocalDate now);

    /*
     * Finds a law proposal by a given state that is still in progress
     * 
     * @param now the law proposal's end date
     */
    @Query("SELECT lp FROM LawProposal lp WHERE lp.endDate < :now AND lp.state = 'IN_PROGRESS'")
    List<LawProposal> findAllSurpassedInProgress(LocalDate now);

}
