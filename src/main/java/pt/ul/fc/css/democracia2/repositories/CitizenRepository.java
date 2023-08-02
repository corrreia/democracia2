/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.democracia2.entities.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    /*
     * Finds a citizen by its governmentId
     * 
     * @param governmentId the citizen's governmentId
     */
    @Query("SELECT c FROM Citizen c WHERE c.governmentId = ?1")
    Citizen findByGovernmentId(String governmentId);

}