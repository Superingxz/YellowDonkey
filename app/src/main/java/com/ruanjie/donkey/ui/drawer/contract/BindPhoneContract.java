package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.LoginBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface BindPhoneContract {

    interface View extends IBaseDisplay {
        void bindPhone(LoginBean data);
        void getCode(String data);
        void getUserInfo(LoginBean data);

    }

    interface Model extends IBasePresenter {
        void bindPhone(String user_id, String token, String phone,String code,String password,String repassword);
        void getCode(String phone,int type);
        void getUserInfo(String user_id, String token);

    }
}
