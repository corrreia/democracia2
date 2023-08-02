package com.example.democracia2_desktop.control;

import com.example.democracia2_desktop.model.DataModel;
import com.example.democracia2_desktop.model.LawProject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class LawProjectsListController {

    @FXML
    private ListView<LawProject> lawProjectsList;

    public void initialize() {
        // Create a mock data model and load mock law projects
        DataModel dataModel = new DataModel();
        dataModel.loadMockLawProjects();

        lawProjectsList.setItems(dataModel.getLawProjects());

        // Set a custom cell factory to display buttons for each law project
        lawProjectsList.setCellFactory(param -> new LawProjectCell());
    }

    @FXML
    private void returnPage(ActionEvent event) {
        try {
            // Load the MainApp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/democracia2_desktop/mainApp.fxml"));
            Parent root = loader.load();

            // Get the controller of the MainApp.fxml file
            MainController mainController = loader.getController();

            // Create a new scene with the MainApp.fxml content
            Scene scene = new Scene(root);

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGovernmentId(String governmentId) {
    }


    // Displays law projects with a details button
    private class LawProjectCell extends ListCell<LawProject> {
        private final Button detailsButton;

        public LawProjectCell() {
            detailsButton = new Button("Details");

            // Configure button action
            detailsButton.setOnAction(event -> {
                LawProject lawProject = getItem();
                if (lawProject != null) {
                    openLawProjectDetails(lawProject);
                    detailsButton.setDisable(true); //to disable after click chage to true
                }
            });
        }

        @Override
        protected void updateItem(LawProject item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(detailsButton);
                setText(item.getTitle());
            }
        }
    }

    private void openLawProjectDetails(LawProject lawProject) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/democracia2_desktop/lawproject.fxml"));
            Parent root = loader.load();

            // Get the controller of the LawProjectDetails.fxml file
            LawProjectController detailsController = loader.getController();

            // Pass the selected law project to the details controller
            detailsController.setLawProject(lawProject);

            // Create a new scene and show it
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
