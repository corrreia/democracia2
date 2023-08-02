module com.example.democracia2_desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;


    opens com.example.democracia2_desktop to javafx.fxml;
    exports com.example.democracia2_desktop;
    exports com.example.democracia2_desktop.control;
    opens com.example.democracia2_desktop.control to javafx.fxml;
}