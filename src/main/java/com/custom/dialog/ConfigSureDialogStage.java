package com.custom.dialog;

import com.controller.DialogController;
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

    private DialogController controller;

    public ConfigSureDialogStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(ConfigSureDialogStage.class.getClassLoader().getResource("fxml/ConfigSureDialog.fxml")));
        Parent parent = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
    }

    public void showAndWait(){
        stage.showAndWait();
    }

    public DialogController getController() {
        return controller;
    }
}
