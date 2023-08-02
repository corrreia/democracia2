/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.entities;

import java.util.Set;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
 * This class represents a theme
 */
@Entity
@Table(name = "themes")
public class Theme {

    @ManyToOne
    private Theme parentTheme;

    @Id
    private String name;

    @OneToMany(mappedBy = "parentTheme")
    private Set<Theme> subThemes;

    @OneToMany(mappedBy = "theme")
    private Set<LawProposal> lawProposals;

    @OneToMany(mappedBy = "theme")
    private Set<LawProject> lawProjects;

    @OneToMany(mappedBy = "theme")//, fetch = FetchType.EAGER)
    private Set<ThemeMapper> themeMappers;

    public Theme() {
    }

    public Theme(Theme parentTheme, @NonNull String name) {
        this.parentTheme = parentTheme;
        this.name = name;
    }

    public Theme getParentTheme() {
        return parentTheme;
    }

    public void setParentTheme(Theme parentTheme) {
        this.parentTheme = parentTheme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty");
        this.name = name;
    }

    public Set<Theme> getSubThemes() {
        return subThemes;
    }

    public void setSubThemes(Set<Theme> subThemes) {
        this.subThemes = subThemes;
    }

    public Set<LawProposal> getLawProposals() {
        return lawProposals;
    }

    public void setLawProposals(Set<LawProposal> lawProposals) {
        this.lawProposals = lawProposals;
    }

    public Set<LawProject> getLawProjects() {
        return lawProjects;
    }

    public void setLawProjects(Set<LawProject> lawProjects) {
        this.lawProjects = lawProjects;
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
        result = prime * result + ((parentTheme == null) ? 0 : parentTheme.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Theme other = (Theme) obj;
        if (parentTheme == null) {
            if (other.parentTheme != null)
                return false;
        } else if (!parentTheme.equals(other.parentTheme))
            return false;
        if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Theme [parentTheme=" + parentTheme + ", name=" + name + "]";
    }

}
