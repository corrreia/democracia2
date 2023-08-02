package com.example.democracia2_desktop.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class LawProject {
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private  int signCount = 1;
    private final StringProperty theme = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> expirationDate = new SimpleObjectProperty<>();

    public LawProject(String title, String description, String theme, LocalDate expirationDate) {
        setTitle(title);
        setDescription(description);
        setTheme(theme);
        setExpirationDate(expirationDate);
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

    public final int signCountProperty() {
        return this.signCount;
    }

    public final int getSignCount() {
        return this.signCount;
    }

    public final void setSignCount( int signCount) {
        this.signCount = (signCount);
    }

    public final StringProperty themeProperty() {
        return this.theme;
    }

    public final String getTheme() {
        return this.themeProperty().get();
    }

    public final void setTheme(final String theme) {
        this.themeProperty().set(theme);
    }

    public final ObjectProperty<LocalDate> expirationDateProperty() {
        return this.expirationDate;
    }

    public final LocalDate getExpirationDate() {
        return this.expirationDateProperty().get();
    }

    public final void setExpirationDate(final LocalDate expirationDate) {
        this.expirationDateProperty().set(expirationDate);
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public void addSignCount() {
        this.signCount += 1;
    }
}
