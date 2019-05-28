package com.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginGui extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadXml(primaryStage);
        showAndWait();
    }

    private static void loadXml(Stage primaryStage) throws IOException {
        if(primaryStage == null){
            primaryStage=new Stage();
        }
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ConfigGui.class.getClassLoader().getResource("fxml/dialog/LoginDialog.fxml")));
        Parent root = loader.load();
        loader.getController();
        primaryStage.setScene(new Scene(root));
        LoginGui.primaryStage = primaryStage;
    }


    public static void closeWindow(){
        if(LoginGui.primaryStage != null){
            Platform.runLater(()-> LoginGui.primaryStage.close());
        }
    }

    public static void showAndWait() throws IOException {
        if(LoginGui.primaryStage == null){
            loadXml(null);
        }
        LoginGui.primaryStage.showAndWait();
    }
}
