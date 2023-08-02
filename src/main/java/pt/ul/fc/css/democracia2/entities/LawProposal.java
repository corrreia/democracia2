/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import pt.ul.fc.css.democracia2.dto.LawProposalDTO;
import pt.ul.fc.css.democracia2.enums.FinishState;

/*
 * This class represents a law proposal
 */
@Entity
@Table(name = "law_proposals")
public class LawProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @NonNull
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @NonNull
    @ManyToOne
    private Theme theme;

    @OneToOne
    private LawProject lawProject;

    @OneToMany(mappedBy = "lawProposal" , fetch = FetchType.EAGER)
    private Set<Vote> votes;

    @NonNull
    private Integer aproveVotes;

    @NonNull
    private Integer disaproveVotes;

    @NonNull
    @Enumerated(EnumType.STRING)
    private FinishState state;

    public LawProposal() {
    }

    public LawProposal(
            @NonNull String title,
            @NonNull String description,
            @NonNull LocalDate startDate, @NonNull LocalDate endDate, @NonNull Theme theme,
            LawProject lawProject) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.theme = theme;
        this.lawProject = lawProject;
        this.aproveVotes = 0;
        this.disaproveVotes = 0;
        this.state = FinishState.IN_PROGRESS;
        this.votes = new HashSet<>();
    }

    public LawProposalDTO dtofy() {
        LawProposalDTO dto = new LawProposalDTO();
        dto.setId(this.id);
        dto.setTitle(this.title);
        dto.setDescription(this.description);
        dto.setStartDate(this.startDate);
        dto.setEndDate(this.endDate);
        dto.setTheme(this.theme.getName());
        // dto.setLawProject(this.lawProject == null ? null : this.lawProject.getId());
        dto.setAproveVotes(this.aproveVotes);
        dto.setDisaproveVotes(this.disaproveVotes);
        dto.setState(this.state);
        return dto;
    }

    public void addVote(Vote vote) {
        if (!this.votes.add(vote)) {
            throw new IllegalArgumentException("Vote already exists");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NonNull LocalDate endDate) {
        this.endDate = endDate;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(@NonNull Theme theme) {
        this.theme = theme;
    }

    public LawProject getLawProject() {
        return lawProject;
    }

    public void setLawProject(@NonNull LawProject lawProject) {
        this.lawProject = lawProject;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public void setAproveVotes(@NonNull Integer aproveVotes) {
        this.aproveVotes = aproveVotes;
    }

    public Integer getAproveVotes() {
        return aproveVotes;
    }

    public void setDisaproveVotes(@NonNull Integer disaproveVotes) {
        this.disaproveVotes = disaproveVotes;
    }

    public Integer getDisaproveVotes() {
        return disaproveVotes;
    }

    public FinishState getState() {
        return state;
    }

    public void setState(@NonNull FinishState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((theme == null) ? 0 : theme.hashCode());
        result = prime * result + ((lawProject == null) ? 0 : lawProject.hashCode());
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
        LawProposal other = (LawProposal) obj;
        if (!title.equals(other.title))
            return false;
        if (!description.equals(other.description))
            return false;
        if (!startDate.equals(other.startDate))
            return false;
        if (!endDate.equals(other.endDate))
            return false;
        if (!theme.equals(other.theme))
            return false;
        if (!Objects.equals(lawProject, other.lawProject))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LawProposal [title=" + title + ", description=" + description + ", startDate=" + startDate
                + ", endDate=" + endDate + ", theme="
                + theme + ", aproveVotes=" + aproveVotes + ", disaproveVotes=" + disaproveVotes + "]";
    }

}