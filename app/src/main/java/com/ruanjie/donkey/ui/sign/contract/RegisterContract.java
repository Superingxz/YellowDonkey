package com.ruanjie.donkey.ui.sign.contract;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.contract
 * 文件名:   RegisterContract
 * 创建者:    QJM
 * 创建时间: 2019/7/30 10:46
 * 描述:     TODO
 */
public interface RegisterContract {

    interface View extends IBaseDisplay {
        void registerSuccess(String phone, String password);
        void loginSuccess();
        boolean checkPhone(String phone);
        boolean checkform(String phone,String password,String rePassword,String code,String name,String id);
        void getVerificationCodeSuccess();
        void verificationSuccess(String phone, String verificationCode);
        void getVerificationCodeFail(String errorMessage);
    }

    interface Model extends IBasePresenter {
        void register(String phone,String password,String rePassword,String code,String name,String id);
        void login(String phone, String password);
        void getVerificationCode(String phone,int type);
        void checkVerificationCode(String phone,String code,int type);
        void userAgreement();
    }
}
