/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * This class represents a theme
 * it is used to map a citizen to a theme and to a delegate
 */
@Entity
@Table(name = "theme_delegate")
public class ThemeMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @ManyToOne
    private Delegate delegate;

    @NonNull
    @ManyToOne
    private Theme theme;

    @ManyToOne
    private Citizen citizen;

    public ThemeMapper() {
    }

    public ThemeMapper(@NonNull Delegate delegate, @NonNull Theme theme, @NonNull Citizen citizen) {
        this.delegate = delegate;
        this.theme = theme;
        this.citizen = citizen;
    }

    public Long getId() {
        return id;
    }

    public Delegate getDelegate() {
        return delegate;
    }

    public Theme getTheme() {
        return theme;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((delegate == null) ? 0 : delegate.hashCode());
        result = prime * result + ((theme == null) ? 0 : theme.hashCode());
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
        ThemeMapper other = (ThemeMapper) obj;
        if (delegate == null) {
            if (other.delegate != null)
                return false;
        } else if (!delegate.equals(other.delegate))
            return false;
        if (theme == null) {
            if (other.theme != null)
                return false;
        } else if (!theme.equals(other.theme))
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
        return "ThemeMapper [id=" + id + ", delegate=" + delegate + ", theme=" + theme + ", citizen=" + citizen + "]";
    }

}
