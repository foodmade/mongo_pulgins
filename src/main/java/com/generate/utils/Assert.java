package com.generate.utils;

import com.generate.common.exception.ParamsInvalidException;

public class Assert {

    /**
     * 判断对象是否为空,是则抛出异常
     * @param o           待判断对象
     * @param message     判定失败提示的Message
     * @param exCls       抛出的异常种类
     */
    public static <T> T isNotNull(T o,String message,Class<? extends RuntimeException> exCls) throws Exception {
        if(CommonUtils.isEmpty(o)){
            ParamsInvalidException e = (ParamsInvalidException) exCls.newInstance();
            e.setErr_info(message);
            throw e;
        }
        return o;
    }
}
