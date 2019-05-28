package com.custom.dialog;
import com.controller.ConfigSureDialogController;
import com.controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginDialogStage {

    private Stage stage;

    private LoginController controller;

    public LoginDialogStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(ConfigSureDialogStage.class.getClassLoader().getResource("fxml/dialog/LoginDialog.fxml")));
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

    public LoginController getController() {
        return controller;
    }
}
