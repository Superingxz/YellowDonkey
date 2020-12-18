package com.ruanjie.donkey.ui.billing.contract;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.billing.contract
 * 文件名:   BillingModeContract
 * 创建者:    QJM
 * 创建时间: 2019/8/28 11:23
 * 描述:     TODO
 */
public interface BillingModeContract {

    interface View extends IBaseDisplay {

    }

    interface Model extends IBasePresenter {
        void vehicleAgreement();
    }
}
