package com.ruanjie.donkey.ui.sign.contract;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.contract
 * 文件名:   FindPasswordContract
 * 创建者:    QJM
 * 创建时间: 2019/7/31 14:35
 * 描述:     TODO
 */
public interface FindPasswordContract {

    interface View extends IBaseDisplay {
        boolean checkPhone(String phone);
        boolean checkform(String phone,String code);
        void getVerificationCodeSuccess();
        void verificationSuccess(String phone, String verificationCode);
        void getVerificationCodeFail(String message);
    }

    interface Model extends IBasePresenter {
        void getVerificationCode(String phone,int type);
        void checkVerificationCode(String phone,String code,int type);
    }
}
