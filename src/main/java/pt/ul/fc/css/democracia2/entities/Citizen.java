/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pt.ul.fc.css.democracia2.dto.CitizenDTO;
import jakarta.persistence.JoinColumn;

/*
 * This class represents a citizen
 */
@Entity
@Table(name = "citizens")
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // temporary since we will be using Autenticação Movel on fase2
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String governmentId;

    @OneToMany(mappedBy = "citizen", fetch = FetchType.EAGER)
    private Set<ThemeMapper> themeMappers;

    @ManyToMany()
    @JoinTable(name = "citizen_law_projects", joinColumns = @JoinColumn(name = "citizen_id"), inverseJoinColumns = @JoinColumn(name = "law_project_id"))
    private Set<LawProject> signedProjects;

    @OneToMany(mappedBy = "citizen" , fetch = FetchType.EAGER)
    private Set<Vote> votes;

    public Citizen() { // required by JPA
    }

    public Citizen(@NonNull String name, @NonNull String surname, @NonNull String governmentId) {
        this.name = name;
        this.surname = surname;
        this.governmentId = governmentId;
        this.votes = new HashSet<>();
    }

    public CitizenDTO dtofy() {
        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setId(this.id);
        citizenDTO.setName(this.name);
        citizenDTO.setSurname(this.surname);
        citizenDTO.setGovernmentId(this.governmentId);
        return citizenDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(@NonNull String surname) {
        this.surname = surname;
    }

    public String getGovernmentId() {
        return governmentId;
    }

    public void setGovernmentId(@NonNull String governmentId) {
        this.governmentId = governmentId;
    }

    public Set<LawProject> getSignedProjects() {
        return signedProjects;
    }

    public void setSignedProjects(Set<LawProject> signedProjects) {
        this.signedProjects = signedProjects;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<ThemeMapper> getThemeMappers() {
        return themeMappers;
    }

    public void setThemeMappers(Set<ThemeMapper> themeMappers) {
        this.themeMappers = themeMappers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((governmentId == null) ? 0 : governmentId.hashCode());
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
        Citizen other = (Citizen) obj;
        if (!name.equals(other.name))
            return false;
        if (!surname.equals(other.surname))
            return false;
        if (!governmentId.equals(other.governmentId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Citizen [id=" + id + ", name=" + name + ", surname=" + surname + ", governmentId=" + governmentId + "]";
    }

}
