package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface CouponNoUseContract {

    interface View extends IBaseDisplay {
        void getCoupons(List<CouponBean> data);
        void deleteCoupon(String data);


    }

    interface Model extends IBasePresenter {
        void getCoupons(int type, int tab_type, int status,int page,int pageSize);
        void deleteCoupon(int coupon_id);


    }
}
