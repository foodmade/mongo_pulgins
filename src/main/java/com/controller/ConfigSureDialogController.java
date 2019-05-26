package com.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigSureDialogController implements Initializable {
    @FXML
    public TextField outField;
    @FXML
    public JFXButton quitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    String getConfigName() {
        return outField.getText();
    }

    public void sureConfig(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }

    public void exitDialog(ActionEvent actionEvent) {
        closeStage(actionEvent);
        outField.clear();
    }
}
