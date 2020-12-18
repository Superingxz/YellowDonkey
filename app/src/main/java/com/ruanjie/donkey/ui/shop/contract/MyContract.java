package com.ruanjie.donkey.ui.shop.contract;

import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;
import com.tencent.bugly.crashreport.biz.UserInfoBean;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.main.contract
 * 文件名:   ShopContract
 * 创建者:    QJM
 * 创建时间: 2019/8/13 10:15
 * 描述:     TODO
 */
public interface MyContract {

    interface View extends IBaseDisplay {
        void getUserInfo(LoginBean data);

        void getShopOrderList(List<ShopOrderBean> data);


    }

    interface Model extends IBasePresenter {


        void getUserInfo();

        void getShopOrderList(int type, int status, int page, int pageSize);

    }
}
