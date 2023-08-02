package pt.ul.fc.css.democracia2.dto;

import org.springframework.stereotype.Component;

@Component
public class ThemeDTO {

    private ThemeDTO parentTheme;
    private String name;

    public ThemeDTO getParentTheme() {
        return parentTheme;
    }

    public void setParentTheme(ThemeDTO parentTheme) {
        this.parentTheme = parentTheme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
