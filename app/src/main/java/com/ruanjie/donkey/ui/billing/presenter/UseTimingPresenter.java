package com.ruanjie.donkey.ui.billing.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.LockBean;
import com.ruanjie.donkey.bean.ParkingAreaBean;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.ruanjie.donkey.ui.billing.contract.UseTimingContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.billing.presenter
 * 文件名:   UseTimingPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/15 2:42
 * 描述:     TODO
 */
public class UseTimingPresenter extends BasePresenter implements UseTimingContract.Model {

    private final UseTimingContract.View view;

    public UseTimingPresenter(UseTimingContract.View view) {
        this.view = view;
    }

    @Override
    public void pauseUseVehicle() {
        RetrofitClient.getService()
                .getPauseUseVehicleData()
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@Nullable String data) {
                        view.pauseSuccess();
                    }
                });
    }

    @Override
    public void continueUseVehicle() {
        RetrofitClient.getService()
                .getContinueUseVehicleData()
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@Nullable String data) {
                        view.retainSuccess();
                    }
                });
    }


    @Override
    public void lockVehicle(String lng,String lat) {

        RetrofitClient.getService()
                .getLockVehicleData(lng,lat)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<LockBean>() {
                    @Override
                    public void onSuccess(@Nullable LockBean data) {
                            view.lockVehicleSuccess(data);
                    }
                });
    }

    @Override
    public void vehicleDetail(String code) {
        RetrofitClient.getService()
                .getVehicleDetailData(code)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<VehicleDetailBean>() {
                    @Override
                    public void onSuccess(@Nullable VehicleDetailBean data) {
                        view.vehicleDetail(data);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    @Override
    public void isParkingArea(String lng,String lat,boolean isEnd) {
        getParams().put("lng",lng);
        getParams().put("lat",lat);

        RetrofitClient.getService()
                .getIsParkingAreaData(getParams())
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<ParkingAreaBean>() {
                    @Override
                    public void onSuccess(@Nullable ParkingAreaBean data) {
                        view.isParkingArea(data,isEnd);
                    }
                });
    }
}
