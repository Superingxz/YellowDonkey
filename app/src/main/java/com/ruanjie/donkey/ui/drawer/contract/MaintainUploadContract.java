package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.MainTainBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface MaintainUploadContract {

    interface View extends IBaseDisplay {
        void uploadMaintain(String data);
        void uploadImage(UploadBean data);

    }

    interface Model extends IBasePresenter {
        void uploadMaintain(String content, String images);
        void uploadImage(String content);

    }
}
