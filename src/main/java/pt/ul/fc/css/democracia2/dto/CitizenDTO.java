package pt.ul.fc.css.democracia2.dto;

import org.springframework.stereotype.Component;

@Component
public class CitizenDTO {
    private Long id;
    private String name;
    private String surname;
    private String governmentId;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getGovernmentId() {
        return governmentId;
    }
    public void setGovernmentId(String governmentId) {
        this.governmentId = governmentId;
    }

    
}