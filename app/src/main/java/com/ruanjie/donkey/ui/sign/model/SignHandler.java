package com.ruanjie.donkey.ui.sign.model;

import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.RegisterBean;
import com.ruanjie.donkey.ui.sign.contract.LoginContract;
import com.ruanjie.donkey.ui.sign.contract.RegisterContract;
import com.ruanjie.donkey.utils.SPManager;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign
 * 文件名:   SignHandler
 * 创建者:    QJM
 * 创建时间: 2019/7/30 15:43
 * 描述:     TODO
 */
public class SignHandler {

    public static void login(LoginBean bean,LoginContract.View view){
        final int userId = bean.getUser_id();
        final String nickname = bean.getNickname();
        final String avatar = bean.getAvatar();
        final int sex = bean.getSex();
        final String token = bean.getToken();

        SPManager.setLoginBean(bean);

//        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);

        //已经注册并登录成功了
        AccountManager.setSignState(true);
        view.loginSuccess();
    }
    public static void login(LoginBean bean,RegisterContract.View view){
        final int userId = bean.getUser_id();
        final String nickname = bean.getNickname();
        final String avatar = bean.getAvatar();
        final int sex = bean.getSex();
        final String token = bean.getToken();

        SPManager.setLoginBean(bean);

//        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);

        //已经注册并登录成功了
        AccountManager.setSignState(true);
        view.loginSuccess();
    }

    public static void register(RegisterBean bean, RegisterContract.View view,String phone, String password){

        final String userId = bean.getUser_id();
        final String nickname = bean.getNickname();
        final String avatar = bean.getAvatar();
        final int sex = bean.getSex();

//        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);

//        AccountManager.setSignState(true);
        view.registerSuccess(phone,password);
    }
}
