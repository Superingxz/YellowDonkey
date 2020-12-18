package com.ruanjie.donkey.ui.billing.contract;

import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.billing.contract
 * 文件名:   PayArrearsContract
 * 创建者:    QJM
 * 创建时间: 2019/8/16 14:38
 * 描述:     TODO
 */
public interface PayArrearsContract {

    interface View extends IBaseDisplay {
        void payArrears(RechargeBean data);
        void getCoupons(List<CouponBean> data);
    }

    interface Model extends IBasePresenter {
        void payArrears(int pay_type,int couponId);
        void getCoupons();
    }
}
