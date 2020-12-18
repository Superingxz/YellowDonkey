package com.ruanjie.toolsdk.config;

import android.content.Context;
import android.os.Handler;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.toolsdk.config
 * 文件名:   ToolSdk
 * 创建者:    QJM
 * 创建时间: 2019/7/23 16:52
 * 描述:     TODO
 */
public final class ToolSdk {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());

        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    //通get方法过key获取全局HashMap中的对象
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    public static Context getApplicationContext() {
        return (Context) getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

}
