package com.custom.dialog;

import com.controller.ConfigSureDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * 自定义dialog组件
 */
public class ConfigSureDialogStage {

    private Stage stage;

    private ConfigSureDialogController controller;

    public ConfigSureDialogStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(ConfigSureDialogStage.class.getClassLoader().getResource("fxml/dialog/ConfigSureDialog.fxml")));
        Parent parent = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UNDECORATED);  消除窗口边框
        stage.setScene(scene);
    }

    public void showAndWait(){
        stage.showAndWait();
    }

    public ConfigSureDialogController getController() {
        return controller;
    }
}
