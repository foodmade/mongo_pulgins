package com.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
        loadXml(primaryStage);
        showAndWait();
    }

    private static void loadXml(Stage primaryStage) throws IOException {
        if(primaryStage == null){
            primaryStage=new Stage();
        }
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ConfigGui.class.getClassLoader().getResource("fxml/Config.fxml")));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        ConfigGui.primaryStage = primaryStage;
    }


    public static void closeWindow(){
        if(ConfigGui.primaryStage != null){
            Platform.runLater(()-> ConfigGui.primaryStage.close());
        }
    }

    public static void showAndWait() throws IOException {
        if(ConfigGui.primaryStage == null){
            loadXml(null);
        }
        ConfigGui.primaryStage.showAndWait();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
