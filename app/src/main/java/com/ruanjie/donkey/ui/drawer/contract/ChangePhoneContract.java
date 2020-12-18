package com.ruanjie.donkey.ui.drawer.contract;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface ChangePhoneContract {

    interface View extends IBaseDisplay {
        void getCode(String data);
        void changePhone(String data);

    }

    interface Model extends IBasePresenter {
        void getCode(String phone, int type);
        void changePhone(String new_phone, String new_code);

    }
}
