package com.custom.evnet;

import java.util.Collection;
import java.util.HashSet;

public class EventSource {

    private Collection listeners;

    /**
     * 添加监听器
     */
    public void addListener(EventListenerData eventListenerData){
        if(listeners==null){
            listeners = new HashSet();
        }
        listeners.add(eventListenerData);
    }

    //给事件源注销监听器
    public void remvoeDolistener(EventListenerData eventListenerData){
        if(listeners == null){
            return;
        }
        listeners.remove(eventListenerData);
    }

    /**
     * 刷新数据
     */
    public void RefreshEvent(EventData data){
        notifies(data);
    }

    /**
     * 触发事件
     */
    private void notifies(EventData eventData){
        for (Object listener : listeners) {
            ((EventListenerData) listener).handler(eventData);
        }
    }
}
