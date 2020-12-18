package com.ruanjie.donkey.ui.sign.contract;

import com.ruanjie.donkey.bean.AliLoginBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.contract
 * 文件名:   LoginContract
 * 创建者:    QJM
 * 创建时间: 2019/7/29 11:45
 * 描述:     TODO
 */
public interface LoginContract {

    interface View extends IBaseDisplay {
        void loginSuccess();

        boolean checkform(String phone, String password);

        void getAliLoginData(AliLoginBean data);

        void alipayLogin(LoginBean data);
        void wechatLogin(LoginBean data);

    }

    interface Model extends IBasePresenter {
        void login(String phone, String password);

        void getAliLoginData(String pid);

        void alipayLogin(String authCode);
        void wechatLogin(String type,String access_token,String openid);
    }
}
