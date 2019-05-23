package com.generate.model;

import com.abs.Node;

public class ConfigNode extends Node {

    /**
     * 项目所在目录地址
     */
    private String fieldProjectPath;
    /**
     * Java类所在包名
     */
    private String packagePath;
    /**
     * 实体类目录路径
     */
    private String daoPackagePath;
    /**
     * Java工具类包名
     */
    private String outFilePath;
    /**
     * 工具类目录路径
     */
    private String daoOutFilePath;
    /**
     * 是否需要添加Mongo注解
     */
    private boolean needAnnotation;
    /**
     * 是否需要添加DBRef引用
     */
    private boolean needDbRef;
    /**
     * 是否跳过_id和_class属性的生成
     */
    private boolean needIClass;

    public String getFieldProjectPath() {
        return fieldProjectPath;
    }

    public void setFieldProjectPath(String fieldProjectPath) {
        this.fieldProjectPath = fieldProjectPath;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getDaoPackagePath() {
        return daoPackagePath;
    }

    public void setDaoPackagePath(String daoPackagePath) {
        this.daoPackagePath = daoPackagePath;
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    public String getDaoOutFilePath() {
        return daoOutFilePath;
    }

    public void setDaoOutFilePath(String daoOutFilePath) {
        this.daoOutFilePath = daoOutFilePath;
    }

    public boolean isNeedAnnotation() {
        return needAnnotation;
    }

    public void setNeedAnnotation(boolean needAnnotation) {
        this.needAnnotation = needAnnotation;
    }

    public boolean isNeedDbRef() {
        return needDbRef;
    }

    public void setNeedDbRef(boolean needDbRef) {
        this.needDbRef = needDbRef;
    }

    public boolean isNeedIClass() {
        return needIClass;
    }

    public void setNeedIClass(boolean needIClass) {
        this.needIClass = needIClass;
    }
}
