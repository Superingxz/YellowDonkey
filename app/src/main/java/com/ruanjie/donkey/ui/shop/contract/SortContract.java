package com.ruanjie.donkey.ui.shop.contract;

import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.bean.ShopsBean;
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
public interface SortContract {

    interface View extends IBaseDisplay {

        void getShopSorts(List<ShopSortBean> data);

        void getShopList(List<ShopsBean> data);

    }

    interface Model extends IBasePresenter {
        void getShopSorts(int pid);

        void getShopList(String lng, String lat, String keyword
                , int category_id, int sort, int page
                , int pageSize);
    }
}
