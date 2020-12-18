package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.PriceBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface PayDepositContract {

    interface View extends IBaseDisplay {
        void payPrice(PriceBean data);
        void payDeposit(RechargeBean data);
    }

    interface Model extends IBasePresenter {
        void payDeposit(int pay_type);
    }
}
