package com.gui;

import com.controller.MainController;
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

public class MainGui implements Initializable {

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

    public static void openWindow(ActionEvent event,String dataName) throws IOException {
        Stage primaryStage=new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ConfigGui.class.getClassLoader().getResource("fxml/Main.fxml")));
        Parent root = loader.load();
        MainController controller = loader.getController();
        primaryStage.setTitle("Auth");
        primaryStage.setScene(new Scene(root));
        controller.initDataSourceInfo(dataName);
        MainGui.primaryStage = primaryStage;
        showWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
