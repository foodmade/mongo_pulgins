package com.generate.model;

public class Bean {

    /** bean 名称 */
     private String name;
     /** bean 首字母小写名称 */
     private String lowerName;
     /** bean 路径 */
     private String beanUrl;
     /** package entity 路径 */
     private String packageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLowerName() {
        return lowerName;
    }

    public void setLowerName(String lowerName) {
        this.lowerName = lowerName;
    }

    public String getBeanUrl() {
        return beanUrl;
    }

    public void setBeanUrl(String beanUrl) {
        this.beanUrl = beanUrl;
    }

    public String getPackageUrl() {
        return packageUrl;
    }

    public void setPackageUrl(String packageUrl) {
        this.packageUrl = packageUrl;
    }
}
