package com.test;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class ToolbarManager {

    public MenuBar createToolbar(Stage stage, BorderPane rootLayout, MainMenu mainMenu) {
        MenuBar menuBar = new MenuBar();

        Menu navigationMenu = new Menu("Navigation");

        MenuItem mainMenuItem = new MenuItem("Main Menu");
        mainMenuItem.setOnAction(e -> {
            // Set only the center content to the MainMenu
            rootLayout.setCenter(mainMenu.launchMainMenu(stage));
        });

        navigationMenu.getItems().add(mainMenuItem);
        menuBar.getMenus().add(navigationMenu);

        return menuBar;
    }
}
