package com.ruanjie.donkey.ui.drawer.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.VehicleListBean;
import com.ruanjie.donkey.ui.drawer.contract.MainTainContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.drawer.presenter
 * 文件名:   MainTainPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/15 14:57
 * 描述:     TODO
 */
public class MainTainPresenter extends BasePresenter implements MainTainContract.Model {

    private final MainTainContract.View view;


    public MainTainPresenter(MainTainContract.View view) {
        this.view = view;
    }

    @Override
    public void chargeVehicleList(Double lng, Double lat, int distance) {
        getParams().put("need_charge",1);
        getParams().put("lng",lng);
        getParams().put("lat",lat);
        getParams().put("distance",distance);
        RetrofitClient.getService()
                .getVehicleListData(getParams())
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<List<VehicleListBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<VehicleListBean> data) {
                        view.chargeVehicleList(data);
                    }
                });
    }

    @Override
    public void illegalParkingVehicleList(Double lng, Double lat, int distance) {
        getParams().put("need_stop",1);
        getParams().put("lng",lng);
        getParams().put("lat",lat);
        getParams().put("distance",distance);
        RetrofitClient.getService()
                .getVehicleListData(getParams())
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<List<VehicleListBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<VehicleListBean> data) {
                        view.illegalParkingVehicleList(data);
                    }
                });
    }

    @Override
    public void faultVehicleList(Double lng, Double lat, int distance) {
        getParams().put("need_repair",1);
        getParams().put("lng",lng);
        getParams().put("lat",lat);
        getParams().put("distance",distance);
        RetrofitClient.getService()
                .getVehicleListData(getParams())
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<List<VehicleListBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<VehicleListBean> data) {
                        view.faultVehicleList(data);
                    }
                });
    }
}
