package com.ruanjie.toolsdk.config;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.tencent.bugly.crashreport.CrashReport;
import com.vondear.rxtool.RxTool;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.toolsdk.config
 * 文件名:   Configurator
 * 创建者:    QJM
 * 创建时间: 2019/7/23 16:28
 * 描述:     TODO
 */
public class Configurator {

    private static final HashMap<Object,Object> CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS= new ArrayList<>();
    private static final Handler HANDLER = new Handler();

    private Configurator() {
        CONFIGS.put(ConfigKeys.CONFIG_READY,false);
        CONFIGS.put(ConfigKeys.HANDLER,HANDLER);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    final HashMap<Object,Object> getConfigs(){
        return CONFIGS;
    }

    public final void configure(){
        initIcons();
        CONFIGS.put(ConfigKeys.CONFIG_READY,true);
        CrashReport.initCrashReport(ToolSdk.getApplicationContext(), StaticConstant.BUGLY_APPID, true);
        RxTool.init(ToolSdk.getApplicationContext());
    }



    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    private void initIcons(){
        if (ICONS.size()>0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    private void checkConfiguration(){
        final boolean isReady = (boolean) CONFIGS.get(ConfigKeys.CONFIG_READY);

        if (!isReady){
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) CONFIGS.get(key);
    }
}
