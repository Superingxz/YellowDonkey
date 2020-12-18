package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.UploadBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface RealNameApplyContract {

    interface View extends IBaseDisplay {
        void uploadImage(int which, UploadBean data);

        void realNameApply(String data);

    }

    interface Model extends IBasePresenter {
        void uploadImage(int which, String content);

        void realNameApply(String username, String idcard, String idcard_font, String idcard_back, String idcard_hand);

    }
}
