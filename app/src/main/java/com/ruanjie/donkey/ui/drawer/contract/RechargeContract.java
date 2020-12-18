package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface RechargeContract {

    interface View extends IBaseDisplay {
        void recharge(RechargeBean data);

        void getCoupons(List<CouponBean> data);

    }

    interface Model extends IBasePresenter {
        void recharge(String money, int pay_type, int coupon_id);

        void getCoupons(int type, int tab_type, int status, int page, int pageSize);

    }
}
