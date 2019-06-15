package com.custom.evnet;

import javax.swing.event.ChangeEvent;

public class UseConfigEvent extends ChangeEvent implements EventData{
    /**
     * Constructs a ChangeEvent object.
     *
     * @param source the Object that is the source of the event
     *               (typically <code>this</code>)
     */
    public UseConfigEvent(Object source) {
        super(source);
    }

    @Override
    public Object getData() {
        return source;
    }
}
