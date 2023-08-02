package com.example.democracia2_desktop.control;

import com.example.democracia2_desktop.model.DataModel;
import com.example.democracia2_desktop.model.LawProposal;
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

public class LawProposalsListController {

    @FXML
    private ListView<LawProposal> lawProposalsList;

    public void initialize() {
        // Create a mock data model and load mock law proposals
        DataModel dataModel = new DataModel();
        dataModel.loadMockLawProposals();

        lawProposalsList.setItems(dataModel.getLawProposals());

        // Set a custom cell factory to display buttons for each law proposal
        lawProposalsList.setCellFactory(param -> new LawProposalCell());
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

    // Displays law proposals with a details button
    private class LawProposalCell extends ListCell<LawProposal> {
        private final Button detailsButton;

        public LawProposalCell() {
            detailsButton = new Button("Details");

            // Configure button action
            detailsButton.setOnAction(event -> {
                LawProposal lawProposal = getItem();
                if (lawProposal != null) {
                    openLawProposalDetails(lawProposal);
                    detailsButton.setDisable(true);
                }
            });
        }

        @Override
        protected void updateItem(LawProposal item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(detailsButton);
                setText(item.getTitle());
            }
        }
    }

    private void openLawProposalDetails(LawProposal lawProposal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/democracia2_desktop/lawproposal.fxml"));
            Parent root = loader.load();

            // Get the controller of the LawProposalDetails.fxml file
            LawProposalController detailsController = loader.getController();

            // Pass the selected law proposal to the details controller
            detailsController.setLawProposal(lawProposal);

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
