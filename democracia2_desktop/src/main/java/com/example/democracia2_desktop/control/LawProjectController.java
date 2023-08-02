package com.example.democracia2_desktop.control;

import com.example.democracia2_desktop.model.DataModel;
import com.example.democracia2_desktop.model.LawProject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LawProjectController {
    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label signCountLabel;

    @FXML
    private Label themeLabel;

    @FXML
    private Label expirationDateLabel;

    @FXML
    private Button addSignButton;

    private LawProject lawProject;

    public void setLawProject(LawProject lawProject) {
        this.lawProject = lawProject;
        updateView();
    }

    private void updateView() {
        titleLabel.setText(lawProject.getTitle());
        descriptionLabel.setText(lawProject.getDescription());
        signCountLabel.setText(String.valueOf(lawProject.getSignCount()));
        themeLabel.setText(lawProject.getTheme());
        expirationDateLabel.setText(lawProject.getExpirationDate().toString());
    }

    @FXML
    private void addSign(ActionEvent event) {
        lawProject.addSignCount();
        updateView();
        addSignButton.setDisable(true);

        // Remove the LawProject from the LawProjectList
        DataModel.getInstance().getLawProjects().remove(lawProject);

    }

}
