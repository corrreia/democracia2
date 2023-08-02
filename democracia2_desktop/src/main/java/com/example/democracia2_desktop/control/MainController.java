package com.example.democracia2_desktop.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Label governmentIdLabel;

    private String governmentId;

    public void setGovernmentId(String governmentId) {
        this.governmentId = governmentId;
        governmentIdLabel.setText("Hello, id: " + governmentId);
    }

    public void goToLawProposalsList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/democracia2_desktop/lawproposalslist.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToLawProjectsList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/democracia2_desktop/lawprojectslist.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exit() {
        menuBar.getScene().getWindow().hide();
    }
}
