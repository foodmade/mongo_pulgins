package com.custom.evnet;

import com.controller.Controller;

import java.util.EventListener;

public interface EventListenerData extends EventListener {

    default void handler(EventData eventData){

    }

    void setController(ControllerEvent controllerEvent);
}
