package com.generate.model;

import java.util.List;

public class BaseGenerateNode {

    private List<ValNode> valNodes;

    private List<String> importPackageNames;

    public List<ValNode> getValNodes() {
        return valNodes;
    }

    public void setValNodes(List<ValNode> valNodes) {
        this.valNodes = valNodes;
    }

    public List<String> getImportPackageNames() {
        return importPackageNames;
    }

    public void setImportPackageNames(List<String> importPackageNames) {
        this.importPackageNames = importPackageNames;
    }
}
