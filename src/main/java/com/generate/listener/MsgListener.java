package com.generate.listener;

import com.generate.model.MsgNode;
import com.generate.utils.DateUtils;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgListener {

    private static ConcurrentLinkedQueue<MsgNode> msgQueue = new ConcurrentLinkedQueue<>();

    private static volatile boolean isListener = false;

    public synchronized static void setIsListener(boolean b){
        MsgListener.isListener = b;
    }

    public static boolean isIsListener() {
        return isListener;
    }

    public static void processMsg(String type, String msg){
        MsgNode msgNode = getMsgNode(type,msg);
        msgQueue.add(msgNode);
    }

    private static MsgNode getMsgNode(String type, String msg) {
        MsgNode node = new MsgNode();
        node.setMsg(getMsgPrefix() + msg);
        node.setMsg_type(type);
        node.setTime(DateUtils.formatYMDHMS(new Date()));
        node.setExpire_time(System.currentTimeMillis() + 2000);
        return node;
    }

    private static String getMsgPrefix(){
        return "[" + DateUtils.formatDate(new Date(),DateUtils.DATE_FORMAT_MM_SS) +"]  ";
    }

    public static MsgNode popMsgNode(){
        return msgQueue.poll();
    }
}
