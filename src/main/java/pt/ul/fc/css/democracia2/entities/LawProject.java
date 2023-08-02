/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import pt.ul.fc.css.democracia2.dto.LawProjectDTO;
import pt.ul.fc.css.democracia2.enums.FinishState;

/*
 * This class represents a law project
 */
@Entity
@Table(name = "law_projects")
public class LawProject {
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

    @NonNull
    @ManyToOne
    private Delegate delegate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private FinishState finishState;

    @ManyToMany(mappedBy = "signedProjects")
    private Set<Citizen> signers;

    @OneToOne
    private LawProposal lawProposal;

    public LawProject() {
    }

    public LawProject(
            @NonNull String title,
            @NonNull String description,
            @NonNull LocalDate startDate,
            @NonNull LocalDate endDate,
            @NonNull Theme theme, @NonNull Delegate delegate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.theme = theme;
        this.delegate = delegate;
        this.finishState = FinishState.IN_PROGRESS;
        this.signers = new HashSet<Citizen>();
    }

    public LawProjectDTO dtofy() {
        LawProjectDTO dto = new LawProjectDTO();
        dto.setId(this.id);
        dto.setTitle(this.title);
        dto.setDescription(this.description);
        dto.setStartDate(this.startDate);
        dto.setEndDate(this.endDate);
        dto.setTheme(this.theme.getName());
        dto.setDelegate(this.delegate.getGovernmentId());
        dto.setFinishState(this.finishState);
        return dto;
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

    public Delegate getDelegate() {
        return delegate;
    }

    public void setDelegate(@NonNull Delegate delegate) {
        this.delegate = delegate;
    }

    public Set<Citizen> getSigners() {
        return signers;
    }

    public void setSigners(Set<Citizen> signers) {
        this.signers = signers;
    }

    public LawProposal getLawProposal() {
        return lawProposal;
    }

    public void setLawProposal(LawProposal lawProposal) {
        this.lawProposal = lawProposal;
    }

    public FinishState getState() {
        return finishState;
    }

    public void setState(FinishState finishState) {
        if (finishState != null) {
            this.finishState = finishState;
        }
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
        result = prime * result + ((delegate == null) ? 0 : delegate.hashCode());
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
        LawProject other = (LawProject) obj;
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
        if (!delegate.equals(other.delegate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LawProject [id=" + id + ", title=" + title + ", description=" + description + ", startDate=" + startDate
                + ", endDate=" + endDate + ", theme="
                + theme + "]";
    }
}
