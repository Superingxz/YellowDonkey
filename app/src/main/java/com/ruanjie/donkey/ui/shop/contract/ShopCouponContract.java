package com.ruanjie.donkey.ui.shop.contract;

import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.bean.ShopCouponBean;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.main.contract
 * 文件名:   ShopContract
 * 创建者:    QJM
 * 创建时间: 2019/8/13 10:15
 * 描述:     TODO
 */
public interface ShopCouponContract {

    interface View extends IBaseDisplay {

        void getCouponList(List<ShopOrderBean> data);
        void submitOrder(String data);
        void deleteOrder(String data);
    }

    interface Model extends IBasePresenter {

        void getCouponList(int type,int store_id, int page, int pageSize);
        void submitOrder(int order_id);
        void deleteOrder(int order_id);

    }
}
