package pt.ul.fc.css.democracia2.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.democracia2.enums.FinishState;

@Component
public class LawProposalDTO {

    private Long id;
    private String title;
    private String description;
    private byte[] projectContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private String theme;
    private Integer lawProject;
    private Integer aproveVotes;
    private Integer disaproveVotes;
    private FinishState state;

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

    public Integer getLawProject() {
        return lawProject;
    }

    public void setLawProject(Integer lawProject) {
        this.lawProject = lawProject;
    }

    public Integer getAproveVotes() {
        return aproveVotes;
    }

    public void setAproveVotes(Integer aproveVotes) {
        this.aproveVotes = aproveVotes;
    }

    public Integer getDisaproveVotes() {
        return disaproveVotes;
    }

    public void setDisaproveVotes(Integer disaproveVotes) {
        this.disaproveVotes = disaproveVotes;
    }

    public FinishState getState() {
        return state;
    }

    public void setState(FinishState state) {
        this.state = state;
    }

}
