package com.example.democracia2_desktop.model;

import java.io.File;
import java.time.LocalDate;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {

    private static DataModel instance;

    private final ObservableList<LawProposal> lawProposals = FXCollections.observableArrayList(
            lawProposal -> new Observable[] {
                    lawProposal.titleProperty(),
                    lawProposal.descriptionProperty(),
                    lawProposal.delegateProperty()
            }
    );

    private final ObservableList<LawProject> lawProjects = FXCollections.observableArrayList(
            lawProject -> new Observable[] {
                    lawProject.titleProperty(),
                    lawProject.descriptionProperty(),
                    lawProject.themeProperty(),
                    lawProject.expirationDateProperty()
            }
    );

    private final ObjectProperty<LawProposal> currentLawProposal = new SimpleObjectProperty<>(null);

    public DataModel() {
    }

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    public ObservableList<LawProposal> getLawProposals() {
        return lawProposals;
    }

    public ObservableList<LawProject> getLawProjects() {
        return lawProjects;
    }

    public ObjectProperty<LawProposal> currentLawProposalProperty() {
        return currentLawProposal;
    }

    public LawProposal getCurrentLawProposal() {
        return currentLawProposalProperty().get();
    }

    public void setCurrentLawProposal(LawProposal lawProposal) {
        currentLawProposalProperty().set(lawProposal);
    }

    public void loadData(File file) {
        // TODO
        // For now, we'll add some mock law proposals and law projects
        //lawProposalList.setAll(
               // new LawProposal("Reforma aos 30", "Proposta para estudantes de LEI se reformarem aos 30", "Ackerman");
       // );
        loadMockLawProposals();
        loadMockLawProjects();
    }

    public void saveData(File file) {
        // TODO
    }

    public void loadMockLawProposals() {
        LawProposal proposal1 = new LawProposal("Reforma aos 30", "Proposta para estudantes de LEI se reformarem aos 30", "Ackerman");
        LawProposal proposal2 = new LawProposal("Seguro de Saude", "Seguro de saude 4free", "Zé Manel");
        LawProposal proposal3 = new LawProposal("Republicas de Estudantes", "Criação de Estatuto para Repúblicas de estudantes", "João Vieira");

        lawProposals.addAll(proposal1, proposal2, proposal3);
    }

    public void loadMockLawProjects() {
        LawProject project1 = new LawProject("Projeto X", "Assinem sff", "Saude", LocalDate.of(2023, 6, 30));
        LawProject project2 = new LawProject("Projeto 125€", "125€ para todos os que recebem mal", "Finanças", LocalDate.of(2023, 7, 15));
        LawProject project3 = new LawProject("Projeto semana de 4 dias", "A semana de 4 dias de trabalho está a chegar a Portugal.", "Trabalho", LocalDate.of(2023, 8, 22));

        lawProjects.addAll(project1, project2, project3);
    }

}
