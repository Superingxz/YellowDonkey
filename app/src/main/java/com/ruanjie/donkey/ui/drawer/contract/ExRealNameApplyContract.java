package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.ExRealNameBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface ExRealNameApplyContract {

    interface View extends IBaseDisplay {
        void uploadImage(int which, UploadBean data);
        void exRealNameApply(String data);
        void getExRealNameApplyData(ExRealNameBean data);

    }

    interface Model extends IBasePresenter {
        void uploadImage(int which, String content);

        void exRealNameApply(String real_name, String id_card, String id_card_photo, String id_card_photo2, String id_card_photo3);
        void getExRealNameApplyData();

    }
}
