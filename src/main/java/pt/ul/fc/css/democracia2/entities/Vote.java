/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pt.ul.fc.css.democracia2.enums.VoteType;

/*
 * This class represents a vote
 */
@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @ManyToOne
    private LawProposal lawProposal;

    @NonNull
    @ManyToOne
    private Citizen citizen;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    public Vote() {
    }

    public Vote(@NonNull LawProposal lawProposal, @NonNull Citizen citizen, VoteType voteType) {
        this.lawProposal = lawProposal;
        this.citizen = citizen;
        this.voteType = voteType;
    }

    public Vote(@NonNull LawProposal lawProposal, @NonNull Citizen citizen) {
        this(lawProposal, citizen, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LawProposal getLawProposal() {
        return lawProposal;
    }

    public void setLawProposal(LawProposal lawProposal) {
        this.lawProposal = lawProposal;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lawProposal == null) ? 0 : lawProposal.hashCode());
        result = prime * result + ((citizen == null) ? 0 : citizen.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vote other = (Vote) obj;
        if (lawProposal == null) {
            if (other.lawProposal != null)
                return false;
        } else if (!lawProposal.equals(other.lawProposal))
            return false;
        if (citizen == null) {
            if (other.citizen != null)
                return false;
        } else if (!citizen.equals(other.citizen))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vote [id=" + id + ", lawProposal=" + lawProposal + ", citizen=" + citizen + ", voteType=" + voteType
                + "]";
    }

}
