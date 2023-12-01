package com.example.workslink;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TreeView Example");

        // Creating the root item
        TreeItem<String> rootItem = new TreeItem<>("Root");

        // Creating child items
        TreeItem<String> child1 = new TreeItem<>("Child 1");
        TreeItem<String> child2 = new TreeItem<>("Child 2");

        // Adding child items to the root
        rootItem.getChildren().addAll(child1, child2);

        // Creating the tree view
        TreeView<String> treeView = new TreeView<>(rootItem);

        // Creating the scene
        Scene scene = new Scene(treeView, 300, 200);

        // Setting the scene to the stage
        primaryStage.setScene(scene);

        // Showing the stage
        primaryStage.show();
    }
}
