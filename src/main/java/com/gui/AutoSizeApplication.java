package com.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.Objects;

public class AutoSizeApplication extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(AutoSizeApplication.class.getClassLoader().getResource("fxml/Container.fxml")));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Config");
        AutoSizeApplication.primaryStage = primaryStage;
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void hideWindow(){
        if(AutoSizeApplication.primaryStage != null){
            Platform.runLater(()-> AutoSizeApplication.primaryStage.hide());
        }
    }


    public static void showWindow(){
        if(AutoSizeApplication.primaryStage != null){
            Platform.runLater(()-> AutoSizeApplication.primaryStage.show());
        }
    }
}
