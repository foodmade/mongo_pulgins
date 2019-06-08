package com.controller;

import com.abs.ConfigNode;
import com.generate.common.deploy.KeepConfigControl;
import com.generate.model.ConfigConfigNode;
import com.generate.utils.CommonUtils;
import com.generate.utils.DateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Date;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化绑定器
        bindCellValueFactory();

        //读取配置文件,加载配置
        try {
            HashMap<String,ConfigConfigNode> configConfigNodeHashMap =
                    (HashMap<String,ConfigConfigNode>)new KeepConfigControl().readAllConfigByMap();

            if(CommonUtils.isEmpty(configConfigNodeHashMap) || configConfigNodeHashMap.isEmpty()){
                return;
            }

            logger.info("【配置文件读取数据：】 {}", configConfigNodeHashMap.toString());

            initDataToView(configConfigNodeHashMap);
        } catch (Exception e) {
            logger.error("【配置中心读取失败】" + e.getMessage());
        }
    }

    private void bindCellValueFactory() {
        configNameColumn.setCellValueFactory(new PropertyValueFactory<>("configName"));
        seqColumn.setCellValueFactory(new PropertyValueFactory<>("seq"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("addTime"));
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
}
