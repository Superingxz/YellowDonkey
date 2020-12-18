package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.bean.ShopsBean;
import com.ruanjie.donkey.ui.shop.contract.ShopContract;
import com.ruanjie.donkey.ui.shop.contract.SortContract;
import com.ruanjie.donkey.ui.shop.fragment.ShopFragment;
import com.ruanjie.donkey.ui.shop.fragment.SortFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

public class SortPresenter extends BasePresenter
        implements SortContract.Model {

    @Override
    public void getShopSorts(int pid) {
        RetrofitClient.getService()
                .getShopSorts(pid)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ShopSortBean>>() {
                    @Override
                    public void onSuccess(List<ShopSortBean> data) {
                        ((SortFragment) mView).getShopSorts(data);
                    }
                });
    }

    @Override
    public void getShopList(String lng, String lat, String keyword
            , int category_id, int sort, int page
            , int pageSize) {
        RetrofitClient.getService()
                .getShopList(lng, lat, keyword
                        , category_id, sort, page
                        , pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ShopsBean>>() {
                    @Override
                    public void onSuccess(List<ShopsBean> data) {
                        ((SortFragment) mView).getShopList(data);
                    }
                });
    }


}
