package com.ruanjie.donkey.utils;



import com.softgarden.baselibrary.base.EventBusBean;

import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {
    public static void post(int code, Object data) {
        EventBus.getDefault().post(
                new EventBusBean.Builder()
                        .setCode(code)
                        .setData(data)
                        .build());
    }
}
