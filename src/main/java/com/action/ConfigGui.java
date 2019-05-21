package com.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
public class ConfigGui extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        //Prop Window Dialog
        com.gui.ConfigGui.main(null);
    }

    public static void main(String[] args) {
        com.gui.ConfigGui.main(null);
    }
}
