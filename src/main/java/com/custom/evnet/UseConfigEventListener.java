package com.custom.evnet;

import com.controller.Controller;
import com.controller.MainController;
import com.generate.model.ConfigConfigNode;

public class UseConfigEventListener implements EventListenerData {

    private Controller controller;

    @Override
    public void handler(EventData eventData) {
        MainController mainController = (MainController)controller;
        mainController.useConfigNode((ConfigConfigNode)eventData.getData());
    }

    @Override
    public void setController(ControllerEvent controllerEvent) {
        this.controller = controllerEvent.getController();
    }
}
