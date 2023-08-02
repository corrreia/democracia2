package com.example.democracia2_desktop.control;

import com.example.democracia2_desktop.model.LawProposal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LawProposalController {
    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label delegateLabel;

    @FXML
    private Label voteCountLabel;

    @FXML
    private Button favoravelButton;

    @FXML
    private Button naoFavoravelButton;

    private LawProposal lawProposal;

    public void setLawProposal(LawProposal lawProposal) {
        this.lawProposal = lawProposal;
        updateView();
    }

    private void updateView() {
        titleLabel.setText(lawProposal.getTitle());
        descriptionLabel.setText(lawProposal.getDescription());
        delegateLabel.setText(lawProposal.getDelegate());
        voteCountLabel.setText(String.valueOf(lawProposal.getVoteCount()));
    }

    @FXML
    private void addFavoravelVote(ActionEvent event) {
        lawProposal.addVoteCount(1);
        favoravelButton.setDisable(true);
        naoFavoravelButton.setDisable(true);
        updateView();
    }

    @FXML
    private void addNaoFavoravelVote(ActionEvent event) {
        lawProposal.addVoteCount(1);
        favoravelButton.setDisable(true);
        naoFavoravelButton.setDisable(true);
        updateView();
    }
}
