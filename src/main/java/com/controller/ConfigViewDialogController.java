package com.controller;

import com.abs.ConfigNode;
import com.custom.cell.TableViewButtonCell;
import com.generate.common.deploy.KeepConfigControl;
import com.generate.model.ConfigConfigNode;
import com.generate.utils.CommonUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigViewDialogController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(ConfigViewDialogController.class);

    @FXML
    public TableView configTableView;
    @FXML
    public TableColumn seqColumn;
    @FXML
    public TableColumn configNameColumn;
    @FXML
    public TableColumn timeColumn;
    @FXML
    public TableColumn operateColumn;
    @FXML
    public TableColumn remarksColumn;

    private static void handle(Event t) {
        CellEditEvent event = (CellEditEvent)t;
        ConfigConfigNode node = (ConfigConfigNode) event.getTableView().getItems().get(event.getTablePosition().getRow());
        node.setConfigName(event.getNewValue()+"");
    }

    public TableView getConfigTableView() {
        return configTableView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化绑定器
        bindCellValueFactory();
        //加载配置数据
        refreshConfig();
    }

    //刷新配置文件至表格
    public void refreshConfig(){
        try {
            this.configTableView.setItems(FXCollections.observableArrayList());
            HashMap<String,ConfigConfigNode> configConfigNodeHashMap =
                    (HashMap<String,ConfigConfigNode>)new KeepConfigControl().readAllConfigByMap();

            if(CommonUtils.isEmpty(configConfigNodeHashMap) || configConfigNodeHashMap.isEmpty()){
                return;
            }
            logger.info("【配置文件读取数据：】 {}", configConfigNodeHashMap.toString());
            initDataToView(configConfigNodeHashMap);
            this.configTableView.refresh();
        } catch (Exception e) {
            logger.error("【配置中心读取失败】" + e.getMessage());
        }
    }

    private void bindCellValueFactory() {
        configNameColumn.setCellValueFactory(new PropertyValueFactory<>("configName"));
        seqColumn.setCellValueFactory(new PropertyValueFactory<>("seq"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("addTime"));
        operateColumn.setCellFactory(param -> new TableViewButtonCell(this));

        configNameColumn.setCellFactory(TextFieldTableCell.<ConfigConfigNode>forTableColumn());
        configNameColumn.setOnEditCommit(event -> {
            System.out.println(event.getSource().getClass().getName());
        });

    }

    private void initDataToView(HashMap<String, ConfigConfigNode> configConfigNodeHashMap) {

        ObservableList<ConfigNode> configNodes = FXCollections.observableArrayList();

        AtomicReference<Integer> seq = new AtomicReference<>(0);

        configConfigNodeHashMap.keySet().forEach(name -> {
            ConfigNode node = new ConfigNode();
            node.setConfigName(name);
            node.setAddTime(configConfigNodeHashMap.get(name).getAddTime());
            node.setSeq(seq.get());
            configNodes.add(node);
            seq.getAndSet(seq.get() + 1);
        });

        configTableView.setItems(configNodes);
    }

    /**
     * 针对TableColumn双击编辑 处理器
     */
    private static class ColumnEditHandler {

        private static void configNameEditHandle(Event t) {
            CellEditEvent event = (CellEditEvent)t;
            /*((ConfigConfigNode) event.getTableView().getItems().get(
                    event.getTablePosition().getRow())
            ).setConfigName(event.getNewValue()+"");*/

            System.out.println(event.getNewValue());
        }
    }
}
