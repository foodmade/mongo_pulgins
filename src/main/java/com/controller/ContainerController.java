package com.controller;

import com.generate.listener.MsgListener;
import com.generate.model.MsgNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContainerController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(ContainerController.class);

    @FXML
    public StackPane containerPane;
    @FXML
    public AnchorPane configGui;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane pane = null;
        try{
            pane = FXMLLoader.load(Objects.requireNonNull(ContainerController.class.getClassLoader().getResource("fxml/Config.fxml")));
        }catch (IOException e){
            logger.error("Switch main UI failed",e);
        }
        containerPane.getChildren().addAll(pane);
    }
}
