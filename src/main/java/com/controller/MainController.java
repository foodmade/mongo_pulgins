package com.controller;

import com.custom.HBoxCell;
import com.generate.dao.CreateJava;
import com.generate.dao.impl.CreateJavaImpl;
import com.generate.model.ValNode;
import com.generate.mongo.MongoExcludeTable;
import com.generate.source.DBCacheControl;
import com.generate.utils.CommonUtils;
import com.gui.AutoSizeApplication;
import com.gui.MainGui;
import com.intellij.openapi.ui.Messages;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    private static DB selectedDB;

    @FXML
    public ListView tableListView;
    @FXML
    public TextField fieldProjectPath;
    @FXML
    public TextField fieldTableName;
    @FXML
    public TextField fieldEntityName;
    @FXML
    public ImageView sourceConfigView;
    @FXML
    public JFXButton generateField;
    @FXML
    public TextField packageField;
    @FXML
    public TextField outFileField;
    @FXML
    public JFXCheckBox needAnnotationField;
    @FXML
    public JFXCheckBox dbRefField;
    @FXML
    public JFXCheckBox idClassField;

    public void initDataSourceInfo(String dataName) {

        DB db = DBCacheControl.getCacheDB(dataName);
        if(db == null){
            logger.warn("【从缓存获取到空的Mongo连接,请重启软件初始化】dataName: {}",dataName);
            return;
        }
        Set<String> collectionNameSet = db.getCollectionNames();
        if(collectionNameSet == null || collectionNameSet.isEmpty()){
            logger.warn("【当前数据库：{},未获取到表存在】",dataName);
            return;
        }
        selectedDB = db;
        //将表名称填充至tableListView
        fillTableAttr(collectionNameSet);
        //设置事件绑定
        tableItemListenerBind();
    }

    private void fillTableAttr(Set<String> collectionNameSet) {

        Set<String> tableNameSet = collectionNameSet
                .stream()
                .filter(tableName ->(!MongoExcludeTable.getTableNameSet().contains(tableName))).collect(Collectors.toSet());

        ObservableList<String> strList = FXCollections.observableArrayList(tableNameSet);

        tableListView.setItems(strList);

        tableListView.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new HBoxCell();
            }
        });
    }

    private void tableItemListenerBind() {
        fieldTableName.textProperty().bind(tableListView.getSelectionModel().selectedItemProperty());

        tableListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            String entityName = CommonUtils.entityJavaModelName(newValue.toString());
            fieldEntityName.setText(CommonUtils.getCapitalcaseChar(entityName));
        });
    }

    public void fileChooser(ActionEvent actionEvent) {
        DirectoryChooser fileChooser = new DirectoryChooser ();
        fileChooser.setTitle("选择目录");
        File directory = fileChooser.showDialog(MainGui.getMainWindowStage());
        fieldProjectPath.setText(directory.getPath());
    }

    public void clickImage(MouseEvent mouseEvent) {
        AutoSizeApplication.showWindow();
    }

    public void generateJava(ActionEvent actionEvent) {
        //开始生成java代码
        //获取Java实体类名
        String entityName = fieldEntityName.getText();
        //获取项目所在目录
        String path = fieldProjectPath.getText();
        //获取包路径
        String packagePath = packageField.getText();
        //获取存放目录
        String outFilePath = outFileField.getText();
        String tableName = fieldTableName.getText();

        if(path == null || path.equals("")){
            alertMessage("提示","项目目录不能为空", Alert.AlertType.ERROR);
            return;
        }

        if(packagePath == null || "".equals(packagePath)){
            alertMessage("提示","包路径不能为空", Alert.AlertType.ERROR);
            return;
        }

        if(outFilePath == null || "".equals(outFilePath)){
            alertMessage("提示","存放目录不能为空", Alert.AlertType.ERROR);
            return;
        }

        //读取选择表中的字段信息
        List<ValNode> attrList = readSelectedTableFieldInfo(tableName);
        CreateJava createJava = new CreateJavaImpl();
        createJava.createEntity(entityName,attrList,path + "/" + outFilePath,packagePath);
        alertMessage("生成完毕","Success",Alert.AlertType.INFORMATION);
    }

    private List<ValNode> readSelectedTableFieldInfo(String tableName) {

        if(selectedDB == null){
            alertMessage("Mongo连接为空,请重新打开软件初始化","提示", Alert.AlertType.ERROR);
            return null;
        }
        DBCollection collection = selectedDB.getCollection(tableName);

        DBObject modelDBObject =  collection.findOne();
        if(modelDBObject == null){
            alertMessage("获取到的表中无数据,无法读取字段名称,请自行初始化数据库数据","提示", Alert.AlertType.WARNING);
            return null;
        }
        List<ValNode> nodeList = new ArrayList<>();
        modelDBObject.keySet().forEach(field ->{
            if("_id".equals(field)) {
                return;
            }

            ValNode node = new ValNode();
            node.setAttrName(field);
            node.setFieldType(modelDBObject.get(field).getClass().getName().split("\\.")[2]);
            node.setNeedAnnotation(needAnnotationField.isSelected());
            nodeList.add(node);
        });
        return nodeList;
    }

    public static void alertMessage(String title, String message, Alert.AlertType alertType){
        Alert information = new Alert(alertType,message);
        information.setTitle(title);
        information.showAndWait();
    }
}
