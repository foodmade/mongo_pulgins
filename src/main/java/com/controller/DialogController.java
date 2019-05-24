package com.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable {
    @FXML
    public TextField outField;

    private String configName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void fetchConfigName(ActionEvent event){
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public String getConfigName() {
        return outField.getText();
    }
}
