package pt.ul.fc.css.democracia2.dto;

import org.springframework.stereotype.Component;

@Component
public class ThemeMapperDTO {

    private Long id;
    private DelegateDTO delegate;
    private ThemeDTO theme;
    private CitizenDTO citizen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DelegateDTO getDelegate() {
        return delegate;
    }

    public void setDelegate(DelegateDTO delegate) {
        this.delegate = delegate;
    }

    public ThemeDTO getTheme() {
        return theme;
    }

    public void setTheme(ThemeDTO theme) {
        this.theme = theme;
    }

    public CitizenDTO getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenDTO citizen) {
        this.citizen = citizen;
    }

}
