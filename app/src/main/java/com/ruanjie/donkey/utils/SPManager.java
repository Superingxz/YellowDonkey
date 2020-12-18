package com.ruanjie.donkey.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ruanjie.donkey.bean.LoginBean;
import com.softgarden.baselibrary.utils.BaseSPManager;
import com.softgarden.baselibrary.utils.SPUtil;

/**
 * @author by DELL
 * @date on 2019/1/5
 * @describe
 */
public class SPManager extends BaseSPManager {
    public static final String LOGINBEAN = "loginbean";//loginbean

    public static LoginBean getLoginBean() {
        return SPUtil.readObjG(LOGINBEAN, LoginBean.class);
    }

    public static void removeLoginBean() {
        SPUtil.remove(LOGINBEAN);
    }

    public static void setLoginBean(LoginBean loginBean) {
        Log.d("lh",new Gson().toJson(loginBean));
        //只有登录获取到的用户信息才有token
        if (TextUtils.isEmpty(loginBean.getToken())) {
            //获取上一次的token进行赋值
            if (getLoginBean() != null) {
                loginBean.setToken(getLoginBean().getToken());
            }
        }
        SPUtil.saveObjG(LOGINBEAN, loginBean);
    }
}
