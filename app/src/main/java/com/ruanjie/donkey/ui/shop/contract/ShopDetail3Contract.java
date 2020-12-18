package com.ruanjie.donkey.ui.shop.contract;

import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.bean.ShopAppraiceBean;
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
public interface ShopDetail3Contract {

    interface View extends IBaseDisplay {
        void getAppraiseList(List<ShopAppraiceBean> data);

    }

    interface Model extends IBasePresenter {

        void getAppraiseList(int store_id);



    }
}
