package com.example.democracia2_desktop.control;

import com.example.democracia2_desktop.model.CitizenModel;
import com.example.democracia2_desktop.model.DelegateModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginController {

    @FXML
    private TextField govermentIdTextField;

    @FXML
    private RadioButton citizenRadioButton;

    @FXML
    private RadioButton delegateRadioButton;

    private ToggleGroup userTypeToggleGroup;

    private String governmentId;

    @FXML
    private void initialize() {
        userTypeToggleGroup = new ToggleGroup();
        citizenRadioButton.setToggleGroup(userTypeToggleGroup);
        delegateRadioButton.setToggleGroup(userTypeToggleGroup);
    }

    @FXML
    private void goToMainController(ActionEvent event) throws IOException {
        String governmentId = govermentIdTextField.getText();
        boolean isCitizen = citizenRadioButton.isSelected();
        boolean isDelegate = delegateRadioButton.isSelected();

        // Validate login and navigate to the menu page
        if (validateLogin(governmentId, isCitizen, isDelegate)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/democracia2_desktop/mainApp.fxml"));
            Parent root = loader.load();
            MainController controller = loader.getController();
            controller.setGovernmentId(governmentId); // Pass the governmentId to MainController

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menu");
            stage.show();
        } else {
            // Invalid login, show error message or take appropriate action
            System.out.println("Invalid login! Please review your credentials.");
        }
    }

    private boolean validateLogin(String governmentId, boolean isCitizen, boolean isDelegate) {
        URL url;
        int responseCode = 0;
        try {
            url = new URL("http://localhost:8080/api/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes("{\"citizenGov\":\"" + governmentId + "\", \"type\": \""
                    + (isCitizen ? "citizen" : "delegate") + "\" }");
            outputStream.flush();
            outputStream.close();
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return responseCode == 200;
    }

    public String getGovernmentId() {
        return governmentId;
    }

}
