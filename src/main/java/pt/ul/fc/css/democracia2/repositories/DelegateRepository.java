/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.democracia2.entities.Delegate;

@Repository
public interface DelegateRepository extends JpaRepository<Delegate, Long> {

    /*
     * Finds a delegate by its governmentId
     * 
     * @param governmentId the delegate's governmentId
     */
    @Query("SELECT d FROM Delegate d WHERE d.governmentId = ?1")
    Delegate findByGovernmentId(String governmentId);
}
