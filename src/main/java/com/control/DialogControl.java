package com.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 自定义dialog组件
 */
public class DialogControl extends Pane {

    @FXML
    TextField outField;

    public DialogControl() {
        Stage primaryStage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DialogControl.class.getClassLoader().getResource("fxml/Dialog.fxml"));
        fxmlLoader.setController(this);

        try {
            Parent root = fxmlLoader.load();
            primaryStage.setTitle("Auth");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String submit(ActionEvent event){
        System.out.println(outField.getText());
        return outField.getText();
    }
}
