package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.ConfigBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.WalletDetailBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface WalletDetailContract {

    interface View extends IBaseDisplay {
        void walletDetail(List<WalletDetailBean> data);

    }

    interface Model extends IBasePresenter {
        void walletDetail(int page,int pageSize);

    }
}
