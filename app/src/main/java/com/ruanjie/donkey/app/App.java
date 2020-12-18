package com.ruanjie.donkey.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.ruanjie.toolsdk.config.ToolSdk;
import com.ruanjie.toolsdk.ui.icon.FontModule;
import com.softgarden.baselibrary.BaseApplication;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * @author by DELL
 * @date on 2018/2/6
 * @describe
 */

public class App extends BaseApplication{


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ToolSdk.init(this)
               .withIcon(new FontAwesomeModule())
               .withIcon(new FontModule())
               .configure();

        //友盟初始化
        UMConfigure.init(this
                , Constants.UMENG_ID
                , "umeng"
                , UMConfigure.DEVICE_TYPE_PHONE
                , "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        //友盟平台配置
        PlatformConfig.setWeixin(Constants.WEIXIN_APPID, Constants.WEIXIN_SECRET);
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("2325247768", "ae9f7d77d5c5c8fc564abb26a0b265ec","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1109684863", "lnqqVY8UPaLaOwq1");
        PlatformConfig.setAlipay("2019071065791280");

    }



}
