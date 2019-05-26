package com.controller;

import com.custom.dialog.ConfigSureDialogStage;
import com.custom.HBoxCell;
import com.generate.common.deploy.IConfig;
import com.generate.common.deploy.KeepConfigControl;
import com.generate.common.exception.ParamsInvalidException;
import com.generate.common.create.ICreateJava;
import com.abs.Node;
import com.generate.common.create.CreateJavaImpl;
import com.generate.model.ConfigNode;
import com.generate.model.ValNode;
import com.generate.mongo.MongoExcludeTable;
import com.generate.utils.Assert;
import com.generate.utils.CommentUtilSource;
import com.generate.utils.CommonUtils;
import com.generate.utils.Const;
import com.gui.ConfigGui;
import com.gui.MainGui;
import com.jfoenix.controls.JFXButton;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainController implements Initializable {

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
    public CheckBox needAnnotationField;
    @FXML
    public CheckBox dbRefField;
    @FXML
    public CheckBox idClassField;
    @FXML
    public Button folderField;
    @FXML
    public TextField daoPackageField;
    @FXML
    public TextField daoOutFileField;
    @FXML
    public CheckBox daoCheckField;
    @FXML
    public ImageView configView;
    @FXML
    public JFXButton saveConfigField;
    @FXML
    public JFXButton clearMainConfigField;

    TableColumn column;

    public void initDataSourceInfo(DB db) {

        if(db == null){
            CommentUtilSource.alertMessage(Const._ERROR,"Mongo连接获取失败,请重启软件", Alert.AlertType.ERROR);
            return;
        }
        Set<String> collectionNameSet = db.getCollectionNames();
        if(collectionNameSet == null || collectionNameSet.isEmpty()){
            logger.warn("【当前数据库：{},未获取到表存在】",db.getName());
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
        if(directory != null)
            fieldProjectPath.setText(directory.getPath());
    }

    public void clickDataSourceConfigImage(MouseEvent mouseEvent) {
        ConfigGui.showWindow();
    }

    public void clickConfigImage(MouseEvent mouseEvent) {

    }

    public void generateJava(ActionEvent actionEvent) {
        //开始生成jEntity代码
        try {
            createEntity();
            if(daoCheckField.isSelected()){
                //开始生成Dao代码
                createUtil();
            }
            CommentUtilSource.alertMessage(Const._TIPS,Const._SUCCESS,Alert.AlertType.INFORMATION);
        }catch (ParamsInvalidException e){
            CommentUtilSource.alertMessage(Const._TIPS,e.getErr_info(), Alert.AlertType.ERROR);
        }catch (Exception e) {
            CommentUtilSource.alertMessage(Const._TIPS,Const._SYSTEM_ERR, Alert.AlertType.ERROR);
        }
    }

    private void createUtil() throws ParamsInvalidException,Exception{
        String path = Assert.isNotNull(fieldProjectPath.getText(),"项目所在目录不能为空", ParamsInvalidException.class);
        String daoPackagePath = Assert.isNotNull(daoPackageField.getText(),"Java工具类包路径不能为空", ParamsInvalidException.class);
        String daoOutPath = Assert.isNotNull(daoOutFileField.getText(),"Java工具类包存放目录不能为空", ParamsInvalidException.class);
        ICreateJava ICreateJava = new CreateJavaImpl();
        ICreateJava.createDao("MongoUtilDao",path + "/" + daoOutPath,daoPackagePath);
    }

    private void createEntity() throws ParamsInvalidException,Exception{

        String entityName = Assert.isNotNull(fieldEntityName.getText(),"Java实体类名不能为空", ParamsInvalidException.class);
        String path = Assert.isNotNull(fieldProjectPath.getText(),"项目所在目录不能为空", ParamsInvalidException.class);
        String packagePath = Assert.isNotNull( packageField.getText(),"包路径不能为空", ParamsInvalidException.class);
        String outFilePath = Assert.isNotNull( outFileField.getText(),"包存放目录不能为空", ParamsInvalidException.class);
        String tableName = Assert.isNotNull( fieldTableName.getText(),"Java实体类名称不能为空", ParamsInvalidException.class);
        //读取选择表中的字段信息
        List<ValNode> attrList = readSelectedTableFieldInfo(tableName);
        if(attrList == null){
            return;
        }
        ICreateJava ICreateJava = new CreateJavaImpl();
        ICreateJava.createEntity(entityName,attrList,path + "/" + outFilePath,packagePath);
    }


    private List<ValNode> readSelectedTableFieldInfo(String tableName) {

        if(selectedDB == null){
            CommentUtilSource.alertMessage("提示","Mongo连接为空,请重新打开软件初始化", Alert.AlertType.ERROR);
            return null;
        }
        DBCollection collection = selectedDB.getCollection(tableName);

        DBObject modelDBObject;
        try {
            modelDBObject = collection.findOne();
        } catch (Exception e) {
            CommentUtilSource.alertMessage("提示", "尝试读取表信息失败,请重试" ,Alert.AlertType.WARNING);
            return null;
        }
        if(modelDBObject == null){
            CommentUtilSource.alertMessage("提示","获取到的表中无数据,无法读取字段名称,请自行初始化数据库数据", Alert.AlertType.WARNING);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCommentCss();

    }

    private void initCommentCss() {
        folderField.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FOLDER));
    }

    /**
     * 保存配置
     */
    public void saveConfig(ActionEvent actionEvent) {
        try {
            String configName = openConfigNameDialog();
            if(CommonUtils.isEmpty(configName)){
                return;
            }
            //获取当前输入参数
            Node node = fetchConfig();
            //写入配置文件
            IConfig config = new KeepConfigControl().setConfig(configName,node);
            config.addConfig();
        } catch (Exception e) {
            CommentUtilSource.alertMessage(Const._ERROR,"保存配置失败", Alert.AlertType.ERROR);
            return;
        }
        CommentUtilSource.alertMessage(Const._SUCCESS,"保存配置成功", Alert.AlertType.INFORMATION);
    }

    private Node fetchConfig() {
        ConfigNode node = new ConfigNode();
        node.setDaoOutFilePath(daoOutFileField.getText());
        node.setDaoPackagePath(daoPackageField.getText());
        node.setFieldProjectPath(fieldProjectPath.getText());
        node.setNeedAnnotation(needAnnotationField.isSelected());
        node.setNeedDbRef(dbRefField.isSelected());
        node.setNeedIClass(idClassField.isSelected());
        node.setOutFilePath(outFileField.getText());
        node.setPackagePath(packageField.getText());
        return node;
    }

    /**
     * 清空输入参数
     */
    public void clearConfig(ActionEvent actionEvent) throws IOException {
        fieldEntityName.clear();
        fieldProjectPath.clear();
        packageField.clear();
        daoPackageField.clear();
    }

    /**
     * 打开配置名称dialog
     * @return 配置名称
     */
    private String openConfigNameDialog() throws IOException {
        ConfigSureDialogStage configSureDialogStage = new ConfigSureDialogStage();
        ConfigSureDialogController controller = configSureDialogStage.getController();
        configSureDialogStage.showAndWait();
        return controller.getConfigName();
    }
}
