package com.ruanjie.donkey.ui.upload.contract;

import com.ruanjie.donkey.bean.UploadBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.upload.contract
 * 文件名:   FaultContract
 * 创建者:    QJM
 * 创建时间: 2019/8/14 15:17
 * 描述:     TODO
 */
public interface UploadContract {
    interface View extends IBaseDisplay {
        void upload(String data);
        void uploadImage(UploadBean data);
        void initRecyclerView();
        void initDatas();
    }

    interface Model extends IBasePresenter {
        void uploadFault(String content, String images);
        void uploadIlleagal(String content, String images);
        void uploadImage(String content);

    }
}
