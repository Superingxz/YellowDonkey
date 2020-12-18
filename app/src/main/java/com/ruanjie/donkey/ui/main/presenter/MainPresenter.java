package com.ruanjie.donkey.ui.main.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.FenceListBean;
import com.ruanjie.donkey.bean.IndexBean;
import com.ruanjie.donkey.bean.NotifyMessageBean;
import com.ruanjie.donkey.bean.ParkingListBean;
import com.ruanjie.donkey.bean.UnReadBean;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.ruanjie.donkey.ui.main.contract.MainContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;
import java.util.WeakHashMap;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.main.presenter
 * 文件名:   ShopPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/13 10:14
 * 描述:     TODO
 */
public class MainPresenter extends BasePresenter implements MainContract.Model {

    private final MainContract.View view;

    private final WeakHashMap<String,Object> PARAMS = getParams();

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }
    @Override
    public void notifyMessage() {
        PARAMS.put("type",2);
        RetrofitClient.getService()
                .getNotifyMessageData(PARAMS)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<List<NotifyMessageBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<NotifyMessageBean> data) {
                        view.initNotifyMessage(data);
                    }
                });
    }

    @Override
    public void index(String lng,String lat,int distance) {
        PARAMS.put("lng",lng);
        PARAMS.put("lat",lat);
        PARAMS.put("distance",distance);
        RetrofitClient.getService()
                .getIndexData(PARAMS)
                .compose(new NetworkTransformer<>(view,false))
                .subscribe(new RxCallback<IndexBean>() {
                    @Override
                    public void onSuccess(@Nullable IndexBean data) {
                        view.initIndex(data);
                    }
                });
    }

    @Override
    public void parikingList(Double lng, Double lat, int distance) {

        RetrofitClient.getService()
                .getParkingListData(String.valueOf(lng),String.valueOf(lat),distance)
                .compose(new NetworkTransformer<>(view,false))
                .subscribe(new RxCallback<List<ParkingListBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<ParkingListBean> data) {
                            view.initParkingList(data);
                    }
                });
    }

    @Override
    public void fenceList() {
        RetrofitClient.getService()
                .getFenceListData()
                .compose(new NetworkTransformer<>(view,false))
                .subscribe(new RxCallback<List<FenceListBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<FenceListBean> data) {
                          view.initFenceList(data);
                    }
                });
    }

    @Override
    public void showPrice() {
        RetrofitClient.getService()
                .getShowPriceData()
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@Nullable String data) {
                        view.isShowPrice();
                    }
                });
    }

    @Override
    public void vehicleDetail(String code) {
        RetrofitClient.getService()
                .getVehicleDetailData(code)
                .compose(new NetworkTransformer<>(view,false))
                .subscribe(new RxCallback<VehicleDetailBean>() {
                    @Override
                    public void onSuccess(@Nullable VehicleDetailBean data) {
                        view.vehicleDetail(data);
                    }

                });
    }

    @Override
    public void getUnReadCount() {
        RetrofitClient.getService()
                .getUnReadCount()
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<UnReadBean>() {
                    @Override
                    public void onSuccess(@Nullable UnReadBean data) {
                        view.getUnReadCount(data);
                    }

                });
    }


}
