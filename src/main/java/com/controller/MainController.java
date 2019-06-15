package com.controller;

import com.abs.ConfigNode;
import com.custom.dialog.ConfigSureDialogStage;
import com.custom.dialog.ConfigViewDialogStage;
import com.custom.dialog.LoginDialogStage;
import com.generate.common.comment.DialogComment;
import com.generate.common.deploy.IConfig;
import com.generate.common.deploy.KeepConfigControl;
import com.generate.common.deploy.MongoConfigControl;
import com.generate.common.exception.CommonException;
import com.generate.common.exception.ParamsInvalidException;
import com.generate.common.create.ICreateJava;
import com.generate.common.create.CreateJavaImpl;
import com.generate.model.BaseGenerateNode;
import com.generate.model.ConfigConfigNode;
import com.generate.model.MongoOptions;
import com.generate.model.ValNode;
import com.generate.mongo.MongoDBUtil;
import com.generate.utils.*;
import com.gui.ConfigGui;
import com.gui.MainGui;
import com.jfoenix.controls.JFXButton;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    private static DB selectedDB;

    @FXML
    public TreeView treeView;
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
        //设置事件绑定
        tableItemListenerBind();
    }

    /**
     * 给树添加节点
     */
    private void fillTreeViewItem(List<MongoOptions> configs) {
        TreeItem rootTreeItem = treeView.getRoot();
        rootTreeItem.getChildren().clear();
        configs.forEach(node ->{
            TreeItem<String> treeItem = new TreeItem<>();
            treeItem.setValue(node.getSaveName());
            ImageView dbImage = new ImageView("img/computer.png");
            dbImage.setFitHeight(16);
            dbImage.setFitWidth(16);
            dbImage.setUserData(node);
            treeItem.setGraphic(dbImage);
            rootTreeItem.getChildren().add(treeItem);
        });
    }

    private void tableItemListenerBind() {
        treeView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<TreeItem<String>>)
                (observableValue, oldItem, newItem) -> {
            if(newItem == null || !newItem.getChildren().isEmpty()){
                return;
            }
            fieldTableName.setText(newItem.getValue());
            String entityName = CommonUtils.entityJavaModelName(newItem.getValue());
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
        try {
            ConfigGui.showAndWait();
            //刷新数据源
            refreshDataSource();
        } catch (Exception e) {
            logger.error("【加载Config.xml失败 e:】{}",e.getMessage());
        }
    }

    public void refreshDataSource(){
        //从dataSource.ini中获取所有的数据源
        IConfig iConfig = new MongoConfigControl();
        try {
            List<MongoOptions> allConfigList = iConfig.readAllConfigByList();
            treeView.setShowRoot(false);
            treeView.setRoot(new TreeItem<>());
            if(!CommonUtils.isEmpty(allConfigList)){
                fillTreeViewItem(allConfigList);
            }
        } catch (Exception e) {
            logger.error("【刷新数据源出现异常 e:】{}",e.getMessage());
            DialogComment.detailMessageDialog(Const._ERROR,"出现未知错误","",e);
        }
    }

    public void registerTreeViewEvent(){
        //注册根节点菜单栏
        registerNodeMenu();
        //注册点击事件
        addTreeViewEvent();
        //注册子节点点击事件
        tableItemListenerBind();
    }

    private void registerNodeMenu() {

        ContextMenu menu     = new ContextMenu();
        MenuItem editItem    = new MenuItem("编辑连接");
        MenuItem removeItem  = new MenuItem("删除连接");
        MenuItem refreshItem = new MenuItem("刷新连接");

        menu.getItems().addAll(editItem,refreshItem,removeItem);
        treeView.setContextMenu(menu);

        //设置菜单点击事件
        editItem.setOnAction(e ->{
            System.out.println("点击编辑菜单");
        });

        removeItem.setOnAction(e -> {
            removeDataSource();
            refreshDataSource();
        });

        refreshItem.setOnAction(e -> {
            refreshDataSource();
        });
    }

    private void removeDataSource() {
        TreeItem<String> treeItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        MongoOptions options = (MongoOptions)treeItem.getGraphic().getUserData();
        //删除ini配置文件中的内容
        IConfig configHelper = new MongoConfigControl();
        ((MongoConfigControl) configHelper).setConfig(options.getSaveName());
        try {
            configHelper.removeConfig();
        } catch (Exception e) {
            DialogComment.detailMessageDialog(Const._ERROR,"删除节点失败","",e);
            return;
        }
        //删除缓存中的节点
        MongoDBUtil.removeMongoDB(options);
    }

    /**
     * treeView注册双击事件
     */
    public void addTreeViewEvent(){
        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                TreeItem<String> treeItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
                MongoOptions options = (MongoOptions)treeItem.getGraphic().getUserData();
                //连接数据库 加载详细dbDName信息
                DB db = MongoDBUtil.getMongoDB(options);
                try {
                    Assert.isNotNull(db,"连接失败", CommonException.class);
                } catch (Exception e) {
                    CommentUtilSource.alertMessage(Const._ERROR,e.getMessage(), Alert.AlertType.ERROR);
                }
                Set<String> collectionNameSet = db.getCollectionNames();
                if(collectionNameSet == null || collectionNameSet.isEmpty()){
                    CommentUtilSource.alertMessage(Const._TIPS,"未获取到相关的表", Alert.AlertType.WARNING);
                    return;
                }
                appendChildrenItemNode(treeItem,collectionNameSet);
            }
        });
    }

    private void appendChildrenItemNode(TreeItem<String> treeItem, Set<String> collectionNameSet) {

        treeItem.getChildren().clear();

        collectionNameSet.forEach(name ->{
            TreeItem<String> childrenItem = new TreeItem<>(name);
            ImageView dbImage = new ImageView("img/table4.png");
            dbImage.setFitHeight(16);
            dbImage.setFitWidth(16);
            childrenItem.setGraphic(dbImage);
            treeItem.getChildren().add(childrenItem);
        });
        //设置树为展开
        treeItem.setExpanded(true);
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

        String entityName  = Assert.isNotNull(fieldEntityName.getText(),"Java实体类名不能为空", ParamsInvalidException.class);
        String path        = Assert.isNotNull(fieldProjectPath.getText(),"项目所在目录不能为空", ParamsInvalidException.class);
        String packagePath = Assert.isNotNull( packageField.getText(),"包路径不能为空", ParamsInvalidException.class);
        String outFilePath = Assert.isNotNull( outFileField.getText(),"包存放目录不能为空", ParamsInvalidException.class);
        Assert.isNotNull( fieldTableName.getText(),"Java实体类名称不能为空", ParamsInvalidException.class);
        //读取选择表中的字段信息
        List<ValNode> attrList = assembleValNodes();
        if(attrList == null){
            return;
        }
        ICreateJava ICreateJava = new CreateJavaImpl();
        ICreateJava.createEntity(entityName,attrList,path + "/" + outFilePath,packagePath);
    }


    private List<ValNode> assembleValNodes() {

        if(selectedDB == null){
            CommentUtilSource.alertMessage("提示","Mongo连接为空,请重新打开软件初始化", Alert.AlertType.ERROR);
            return null;
        }

        String tableName = fieldTableName.getText();

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
            String fieldType = modelDBObject.get(field).getClass().getName().split("\\.")[2];
            node.setFieldType(fieldType);
            node.setNeedAnnotation(needAnnotationField.isSelected());
            nodeList.add(node);
        });
        return nodeList;
    }

    private BaseGenerateNode parserFieldInfo(){
        BaseGenerateNode baseGenerateNode = new BaseGenerateNode();
        baseGenerateNode.setValNodes(assembleValNodes());
        return baseGenerateNode;
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
            ConfigNode node = fetchConfig();
            //写入配置文件
            IConfig config = new KeepConfigControl().setConfig(configName,node);
            config.addConfig();
        } catch (Exception e) {
            CommentUtilSource.alertMessage(Const._ERROR,"保存配置失败", Alert.AlertType.ERROR);
            return;
        }
        CommentUtilSource.alertMessage(Const._SUCCESS,"保存配置成功", Alert.AlertType.INFORMATION);
    }

    private ConfigNode fetchConfig() {
        ConfigConfigNode node = new ConfigConfigNode();
        node.setDaoOutFilePath(daoOutFileField.getText());
        node.setDaoPackagePath(daoPackageField.getText());
        node.setFieldProjectPath(fieldProjectPath.getText());
        node.setNeedAnnotation(needAnnotationField.isSelected());
        node.setNeedDbRef(dbRefField.isSelected());
        node.setNeedIClass(idClassField.isSelected());
        node.setOutFilePath(outFileField.getText());
        node.setPackagePath(packageField.getText());
        node.setAddTime(DateUtils.formatYMDHMS(new Date()));
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
     * 打开配置确认框
     * @return 配置名称
     */
    private String openConfigNameDialog() throws IOException {
        ConfigSureDialogStage configSureDialogStage = new ConfigSureDialogStage();
        ConfigSureDialogController controller = configSureDialogStage.getController();
        configSureDialogStage.showAndWait();
        return controller.getConfigName();
    }

    /**
     * 打开配置管理界面
     */
    public void clickConfigImage(MouseEvent mouseEvent) throws IOException {
        ConfigViewDialogStage configViewDialogStage = new ConfigViewDialogStage();
        configViewDialogStage.showAndWait();
    }

    private void openLoginDialog() throws IOException {
        LoginDialogStage loginDialogStage = new LoginDialogStage();
        loginDialogStage.showAndWait();
    }
}
