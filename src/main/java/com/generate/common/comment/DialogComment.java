package com.generate.common.comment;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DialogComment {

    /**
     * 弹出带有详细文本的dialog
     * @param title                  dialog标题
     * @param headerText             头部信息
     * @param contentText            主要文本信息
     */
    public static void detailMessageDialog(String title, String headerText, String contentText, Exception ex){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label();

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();
        });


    }

    /**
     * 弹出带有输入框的dialog
     * @param title          标题
     * @param headerText     头信息
     * @param contentText    内容
     * @return  输入的值
     */
    public static String inputMessageDialog(String title, String headerText, String contentText){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    /**
     * 弹出带有下拉选择框的dialog
     * @param choices      下拉框数据集
     * @return             选中的列 值
     */
    public static String choiceDialog(List<String> choices,String title,String headerText,String contentText){
        if(choices == null){
            choices = new ArrayList<>();
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
