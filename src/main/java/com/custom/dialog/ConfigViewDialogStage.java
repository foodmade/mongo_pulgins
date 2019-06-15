package com.custom.dialog;

import com.controller.ConfigViewDialogController;
import com.custom.evnet.EventSource;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ConfigViewDialogStage {

    private Stage stage;

    private ConfigViewDialogController controller;

    public void setEventSource(EventSource eventSource) {
        controller.setEventSource(eventSource);
    }

    public ConfigViewDialogStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(ConfigSureDialogStage.class.getClassLoader().getResource("fxml/dialog/ConfigViewDialog.fxml")));
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

    public ConfigViewDialogController getController() {
        return controller;
    }
}
