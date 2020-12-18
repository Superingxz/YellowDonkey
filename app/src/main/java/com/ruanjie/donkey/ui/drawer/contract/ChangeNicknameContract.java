package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.UploadBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface ChangeNicknameContract {

    interface View extends IBaseDisplay {
        void changeUserInfo(String data);

    }

    interface Model extends IBasePresenter {
        void changeUserInfo(int sex, String nickname, String birthday);

    }
}
