package com.ruanjie.donkey.ui.shop.contract;

import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.main.contract
 * 文件名:   ShopContract
 * 创建者:    QJM
 * 创建时间: 2019/8/13 10:15
 * 描述:     TODO
 */
public interface GoodsDetailContract {

    interface View extends IBaseDisplay {
        void getGoodsDetail(GoodsDetailBean data);

        void buyGoods(BuyGoodsBean data);

    }

    interface Model extends IBasePresenter {

        void getGoodsDetail(int goods_id);

        void buyGoods(int goods_id);


    }
}
