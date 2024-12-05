package com.test;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ToolbarManager {
    public MenuBar createToolbar(Stage stage, MainMenu mainMenu) {
        MenuBar menuBar = new MenuBar();

        // Create a "Navigation" menu
        Menu navigationMenu = new Menu("Navigation");

        // Add a "Main Menu" item to the "Navigation" menu
        MenuItem mainMenuItem = new MenuItem("Main Menu");
        mainMenuItem.setOnAction(e -> stage.getScene().setRoot(mainMenu.launchMainMenu(stage)));

        // Add the MenuItem to the Menu
        navigationMenu.getItems().add(mainMenuItem);

        // Add the Menu to the MenuBar
        menuBar.getMenus().add(navigationMenu);

        return menuBar;
    }
}
