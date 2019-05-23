package com.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class ConfigGui extends Application {

    public static void main(String[] args) {
        launch(ConfigGui.class);
    }
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ConfigGui.class.getClassLoader().getResource("fxml/Config.fxml")));
        Parent root = loader.load();
        primaryStage.setTitle("Auth");
        primaryStage.setScene(new Scene(root));
        ConfigGui.primaryStage = primaryStage;
        showWindow();
    }

    public static void hideWindow(){
        if(ConfigGui.primaryStage != null){
            Platform.runLater(()-> ConfigGui.primaryStage.hide());
        }
    }


    public static void showWindow(){
        if(ConfigGui.primaryStage != null){
            Platform.runLater(()-> ConfigGui.primaryStage.show());
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
