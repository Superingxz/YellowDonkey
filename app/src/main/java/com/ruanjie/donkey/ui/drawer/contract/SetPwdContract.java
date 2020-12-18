package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.TokenBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface SetPwdContract {

    interface View extends IBaseDisplay {
        void changePwd(TokenBean data);

    }

    interface Model extends IBasePresenter {
        void changePwd(String old_password, String new_password, String re_password);

    }

    interface Change extends IBaseDisplay {
        void change(String data);

    }

    interface ChangePwd extends IBasePresenter {
        void resetPwd(String phone ,String code,String new_password, String re_password);

    }
}
