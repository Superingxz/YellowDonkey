package com.ruanjie.donkey.ui.shop.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.FenceListBean;
import com.ruanjie.donkey.bean.IndexBean;
import com.ruanjie.donkey.bean.NotifyMessageBean;
import com.ruanjie.donkey.bean.ParkingListBean;
import com.ruanjie.donkey.bean.ShopBean;
import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.bean.ShopsBean;
import com.ruanjie.donkey.bean.TodayDatasDetailBean;
import com.ruanjie.donkey.bean.UnReadBean;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.ruanjie.donkey.ui.drawer.TodayDatasDetailActivity;
import com.ruanjie.donkey.ui.shop.contract.ShopContract;
import com.ruanjie.donkey.ui.shop.fragment.ShopFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;
import java.util.WeakHashMap;

public class ShopPresenter extends BasePresenter implements ShopContract.Model {


    @Override
    public void getShopBanners() {
        RetrofitClient.getService()
                .getShopBanners()
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<String>>() {
                    @Override
                    public void onSuccess(List<String> data) {
                        ((ShopFragment) mView).getShopBanners(data);
                    }
                });
    }

    @Override
    public void getShopSorts(int pid) {
        RetrofitClient.getService()
                .getShopSorts(pid)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ShopSortBean>>() {
                    @Override
                    public void onSuccess(List<ShopSortBean> data) {
                        ((ShopFragment) mView).getShopSorts(data);
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
                        ((ShopFragment) mView).getShopList(data);
                    }
                });
    }
}
