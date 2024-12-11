package com.test;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ToolbarManager {

    public HBox createToolbar(Stage stage, BorderPane rootLayout, MainMenu mainMenu) {
        // Create an HBox for the toolbar
        HBox toolbar = new HBox();
        toolbar.setId("toolbar"); // Apply styling via CSS
        toolbar.setSpacing(10); // Add some spacing between elements
        toolbar.setPadding(new javafx.geometry.Insets(10)); // Add padding
        toolbar.setAlignment(javafx.geometry.Pos.CENTER_LEFT); // Align to the left

        // Create the Main Menu button
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setId("main-menu-button"); // Add a CSS ID for styling

        // Set the action for the Main Menu button
        mainMenuButton.setOnAction(e -> rootLayout.setCenter(mainMenu.launchMainMenu(stage)));

        // Add the button to the toolbar
        toolbar.getChildren().add(mainMenuButton);

        return toolbar;
    }
}
