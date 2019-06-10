package com.custom.cell;

import com.abs.ConfigNode;
import com.controller.ConfigViewDialogController;
import com.generate.common.deploy.IConfig;
import com.generate.common.deploy.KeepConfigControl;
import com.generate.utils.SystemPathUtil;
import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class TableViewButtonCell extends TableCell<ConfigNode,Boolean> {

    private static final String boxStyleCss;
    private ConfigViewDialogController configViewDialogController;
    static {
        boxStyleCss =
                "-fx-alignment: CENTER;\n";
    }

    private HBox hBox;
    private JFXButton delButton;
    private JFXButton useButton;

    public TableViewButtonCell(ConfigViewDialogController configViewDialogController) {
        this.hBox = new HBox(8);

        this.delButton = new JFXButton("删除");
        this.useButton = new JFXButton("应用");

        this.delButton.setId("delButton");
        this.useButton.setId("useButton");

        //设置鼠标形状
        this.delButton.setCursor(Cursor.HAND);
        this.useButton.setCursor(Cursor.HAND);

        this.hBox.setStyle(boxStyleCss);
        this.hBox.getStylesheets().add(SystemPathUtil.getProjectPath() + "/resources/static/css/dialog.css");

        this.hBox.getChildren().addAll(useButton,delButton);

        addOnAction();

        this.configViewDialogController = configViewDialogController;
    }

    private void addOnAction(){
        this.delButton.setOnAction(event -> {
            configViewDialogController.getConfigTableView().getSelectionModel().select(getTableRow().getIndex());
            String configName = ((ConfigNode)configViewDialogController.getConfigTableView().getSelectionModel().getSelectedItem()).getConfigName();

            IConfig iConfig = new KeepConfigControl();
            ((KeepConfigControl) iConfig).setConfig(configName);
            try {
                iConfig.removeConfig();
                //删除完毕后,刷新表格数据
                configViewDialogController.refreshConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.useButton.setOnAction(event -> System.out.println("点击应用按钮"));
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty){
            setGraphic(hBox);
        }
    }
}
