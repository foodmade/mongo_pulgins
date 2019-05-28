package com.controller;

import com.generate.utils.CommonUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigSureDialogController implements Initializable {
    @FXML
    public JFXTextField outField;
    @FXML
    public JFXButton sureButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        outField.getValidators().add(validator);
        validator.setMessage("配置名称不能存在特殊字符或者为空");
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
        //验证配置名称是否存在特殊字符
        if(CommonUtils.isSpecialChar(getConfigName())){
            outField.clear();
            outField.validate();
        }else{
            closeStage(actionEvent);
        }
    }

    public void exitDialog(ActionEvent actionEvent) {
        closeStage(actionEvent);
        outField.clear();
    }
}
