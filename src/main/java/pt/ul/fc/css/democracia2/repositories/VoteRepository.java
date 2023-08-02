/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.democracia2.entities.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
