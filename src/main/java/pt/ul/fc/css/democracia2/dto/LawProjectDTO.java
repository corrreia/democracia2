package pt.ul.fc.css.democracia2.dto;

import java.time.LocalDate;

import pt.ul.fc.css.democracia2.enums.FinishState;

import org.springframework.stereotype.Component;

@Component
public class LawProjectDTO {

    private Long id;
    private String title;
    private String description;
    private byte[] projectContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private String theme;
    private String delegate;
    private FinishState finishState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(byte[] projectContent) {
        this.projectContent = projectContent;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDelegate() {
        return delegate;
    }

    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }

    public FinishState getFinishState() {
        return finishState;
    }

    public void setFinishState(FinishState finishState) {
        this.finishState = finishState;
    }

}
