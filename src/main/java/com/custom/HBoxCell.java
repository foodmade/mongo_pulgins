package com.custom;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class HBoxCell extends ListCell<String> {

    private HBox hbox = new HBox();
    private Label label = new Label();
    private Pane pane = new Pane();
    private String lastItem;

    public HBoxCell() {
        super();
        ImageView imageView = new ImageView("/img/table4.png");
        imageView.setFitHeight(15);
        imageView.setFitWidth(19);
        Button button = new Button("",imageView);
        button.setOpaqueInsets(new Insets(0));
        button.setBackground(new Background(new BackgroundFill(new Color(0,0,0,0),null,null)));

        label.setWrapText(true);

        hbox.getChildren().addAll(button,label, pane);
        HBox.setHgrow(pane, Priority.ALWAYS);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            label.setText(item!=null ? item : "<null>");
            setGraphic(hbox);
        }
    }
}
