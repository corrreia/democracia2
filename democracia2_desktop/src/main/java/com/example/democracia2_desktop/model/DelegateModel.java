package com.example.democracia2_desktop.model;

public class DelegateModel {
    private String governmentId;
    private static DelegateModel instance;

    private DelegateModel() {
    }

    public static DelegateModel getInstance() {
        if (instance == null) {
            instance = new DelegateModel();
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
