package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;
import com.tencent.bugly.crashreport.biz.UserInfoBean;

import java.util.List;


public interface UserInfoContract {

    interface View extends IBaseDisplay {
        void uploadImage(UploadBean data);

        void uploadImage2(String data);

        void changeHead(UploadBean data);

        void changeUserInfo(int type, int sex, String nickname, String birthday);
        void getUserInfo(LoginBean data);

    }

    interface Model extends IBasePresenter {
        void uploadImage(String content);

        void uploadImage2(List<String> pics);

        void changeHead(String avatar);

        void changeUserInfo(int type, int sex, String nickname, String birthday);
        void getUserInfo();

    }
}
