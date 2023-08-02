/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.entities;

import java.util.Set;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import pt.ul.fc.css.democracia2.dto.DelegateDTO;

/*
 * This class represents a delegate
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Delegate extends Citizen {

    @OneToMany(mappedBy = "delegate")
    private Set<LawProject> lawProjects;

    @OneToMany(mappedBy = "delegate", fetch = FetchType.EAGER)
    private Set<ThemeMapper> citizenThemeMappers;

    public Delegate() {
    }

    public Delegate(@NonNull String name, @NonNull String surname, @NonNull String governmentId) {
        super(name, surname, governmentId);
    }

    public DelegateDTO dtofy() {
        DelegateDTO dto = new DelegateDTO();
        dto.setId(getId());
        dto.setName(getName());
        dto.setSurname(getSurname());
        dto.setGovernmentId(getGovernmentId());
        return dto;
    }

    public String getFullName() {
        return getName() + " " + getSurname();
    }

    public Set<LawProject> getLawProjects() {
        return lawProjects;
    }

    public void setLawProjects(Set<LawProject> lawProjects) {
        this.lawProjects = lawProjects;
    }

    public Set<ThemeMapper> getCitizenThemeMappers() {
        return citizenThemeMappers;
    }

    public void setCitizenThemeMappers(Set<ThemeMapper> themeMappers) {
        this.citizenThemeMappers = themeMappers;
    }

    @Override
    public String toString() {
        return "Delegate [id = " + getId() + ", name = " + getName() + ", surname = " + getSurname()
                + ", governmentId = " + getGovernmentId() + "]";
    }

}