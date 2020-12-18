package com.ruanjie.donkey.ui.unlock.contract;

import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.unlock.contract
 * 文件名:   WaitUnlockContract
 * 创建者:    QJM
 * 创建时间: 2019/8/10 19:04
 * 描述:     TODO
 */
public interface WaitUnlockContract {

    interface View extends IBaseDisplay {
            void showProgress();
            void unlockSuccess(VehicleDetailBean bean);
            void unlockFail();
    }

    interface Model extends IBasePresenter {
           void unlock(String code);
    }
}
