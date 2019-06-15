package com.custom.evnet;

import javafx.event.Event;
import javafx.event.EventHandler;

public class UseConfigEvent implements EventHandler {
    @Override
    public void handle(Event event) {
        System.out.println("执行use按钮");
    }
}
