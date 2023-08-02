package com.example.democracia2_desktop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class LawProposal {
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty delegate = new SimpleStringProperty();
    private int voteCount = 0;
    private int favoravelCount = 0;
    private int naoFavoravelCount = 0;

    public LawProposal(String title, String description, String delegate) {
        setTitle(title);
        setDescription(description);
        setDelegate(delegate);
    }

    public final StringProperty titleProperty() {
        return this.title;
    }

    public final String getTitle() {
        return this.titleProperty().get();
    }

    public final void setTitle(final String title) {
        this.titleProperty().set(title);
    }

    public final StringProperty descriptionProperty() {
        return this.description;
    }

    public final String getDescription() {
        return this.descriptionProperty().get();
    }

    public final void setDescription(final String description) {
        this.descriptionProperty().set(description);
    }

    public final StringProperty delegateProperty() {
        return this.delegate;
    }

    public final String getDelegate() {
        return this.delegateProperty().get();
    }

    public final void setDelegate(final String delegate) {
        this.delegateProperty().set(delegate);
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void addVoteCount(int voteCount){
        this.voteCount += 1;
    }

    public int getFavoravelCount() {
        return favoravelCount;
    }


    public void addFavoravel(int favoravelCount){
        this.favoravelCount += 1;
    }
    public int getNaoFavoravelCount() {
        return naoFavoravelCount;
    }
    public void addNaoFavoravel(int naoFavoravelCount){
        this.naoFavoravelCount += 1;
    }

    @Override
    public String toString() {
        return getTitle();
    }


}
