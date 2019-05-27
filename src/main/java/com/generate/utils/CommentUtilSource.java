package com.generate.utils;

import javafx.scene.control.Alert;

public class CommentUtilSource {

    public static void alertMessage(String title, String message, Alert.AlertType alertType){
        Alert information = new Alert(alertType,message);
        information.setTitle(title);
        information.showAndWait();
    }
}
