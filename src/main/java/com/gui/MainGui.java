package com.gui;

import com.controller.MainController;
import com.mongodb.DB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainGui extends Application {

    private static Stage primaryStage;

    public static void hideWindow(){
        Platform.runLater(()-> MainGui.primaryStage.hide());
    }

    public static void showWindow(){
        Platform.runLater(()-> MainGui.primaryStage.show());
    }

    public static Stage getMainWindowStage(){
        return MainGui.primaryStage;
    }

/*    public static void openWindow(ActionEvent event, DB db) throws IOException {
        Stage primaryStage=new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(MainGui.class.getClassLoader().getResource("fxml/Main.fxml")));
        Parent root = loader.load();
        MainController controller = loader.getController();
        primaryStage.setScene(new Scene(root));
        controller.initDataSourceInfo(db);
        MainGui.primaryStage = primaryStage;
        showWindow();
    }*/


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(MainGui.class.getClassLoader().getResource("fxml/Main.fxml")));
        Parent root = loader.load();
        MainController controller = loader.getController();
        primaryStage.setScene(new Scene(root));
        MainGui.primaryStage = primaryStage;
        controller.refreshDataSource();
        showWindow();
    }
}
