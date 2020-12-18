package com.ruanjie.donkey.utils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.Map;

public class LogUtils {
    public static boolean isDebug = true;//是否Debug 控制打印

    /**
     * Logger 工具打印
     */
    public static void d(String tag, String msg) {
        if (isDebug) Logger.t(tag).d(msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug) Logger.t(tag).i(msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug) Logger.t(tag).v(msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug) Logger.t(tag).w(msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) Logger.t(tag).e(msg);
    }

    public static void json(String tag, String json) {
        if (isDebug) Logger.t(tag).json(json);
    }

    public static void xml(String tag, String xml) {
        if (isDebug) Logger.t(tag).xml(xml);
    }


    /**
     * 打印对象model
     * @param tag
     * @param bean
     */
    public static void logBean(String tag, Object bean) {
        if (isDebug)
            Logger.t(tag).json(new Gson().toJson(bean));
    }

    /**
     * 将传进去的字符串参数格式化打印
     *
     * @param maps
     * @return
     */
    public static void logMap(String tag, Map<String, String> maps) {
        String tempStr = "";
        for (Map.Entry entry : maps.entrySet()) {
            String keyStr = (String) entry.getKey();
            String valueStr = (String) entry.getValue();
            tempStr = tempStr + keyStr + " = " + valueStr + "\n";
        }
        if (isDebug)
            Logger.t(tag).i(tempStr);
    }
}
