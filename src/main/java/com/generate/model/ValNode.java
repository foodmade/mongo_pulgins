package com.generate.model;

import com.generate.utils.CommonUtils;

public class ValNode {

    /**
     * 属性名
     */
    private String attrName;
    /**
     * 数据类型
     */
    private String fieldType;
    /**
     * 默认值
     */
    private Object defaultVal = null;
    /**
     * 属性名首字母大写
     */
    private String flcapitalName;

    public boolean isNeedAnnotation() {
        return needAnnotation;
    }

    public void setNeedAnnotation(boolean needAnnotation) {
        this.needAnnotation = needAnnotation;
    }

    /**
     * 是否需要自动添加Mongo注解
     */
    private boolean needAnnotation;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
        setFlcapitalName(CommonUtils.getCapitalcaseChar(attrName));
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Object getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(Object defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getFlcapitalName() {
        return flcapitalName;
    }

    public void setFlcapitalName(String flcapitalName) {
        this.flcapitalName = flcapitalName;
    }
}
