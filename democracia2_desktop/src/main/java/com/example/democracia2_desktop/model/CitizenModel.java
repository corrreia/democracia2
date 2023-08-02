package com.example.democracia2_desktop.model;

public class CitizenModel {
    private String governmentId;
    private static CitizenModel instance;

    private CitizenModel() {
    }

    public static CitizenModel getInstance() {
        if (instance == null) {
            instance = new CitizenModel();
        }
        return instance;
    }

    public String getGovernmentId() {
        return governmentId;
    }

    public void setGovernmentId(String governmentId) {
        this.governmentId = governmentId;
    }
}
