package com.ruanjie.donkey.ui.sign.model;

import com.ruanjie.donkey.listener.IUserChecker;
import com.softgarden.baselibrary.utils.SPUtil;

/**
 * 项目名:   WabaoEC
 * 包名:     com.qjm.wabao.app
 * 文件名:   AccountManager
 * 创建者:    QJM
 * 创建时间: 2019/5/11 7:47
 * 描述:     TODO
 */

public class AccountManager {

    private enum  SignTag{
            SIGN_TAG
    }

    //保存用户登录状态,登录后调用
    public static void setSignState(boolean state){
        SPUtil.put(SignTag.SIGN_TAG.name(),state);
    }

    private static boolean isLogin(){
        return (boolean) SPUtil.get(SignTag.SIGN_TAG.name(),false);
    }

    public static void checkAccount(IUserChecker checker){
        if (isLogin()){
            checker.alreadyLogin();
        }else {
            checker.notLogin();
        }
    }

}
