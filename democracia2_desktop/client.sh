#!/bin/bash

# Set the JavaFX SDK path and main class
export MODULE_PATH="C:\Java\javafx-sdk-20.0.1\lib"
export MAIN_CLASS="com.example.democracia2_desktop.MainApp"

# Run the JavaFX application
java --module-path $MODULE_PATH --add-modules javafx.controls,javafx.fxml -cp . $MAIN_CLASS
