package com.ruanjie.donkey.ui.drawer.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.VehicleListBean;
import com.ruanjie.donkey.ui.drawer.ExChangeActivity;
import com.ruanjie.donkey.ui.drawer.contract.ExChangeContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   LoginPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/29 11:28
 * 描述:     TODO
 */
public class ExChangePresenter extends BasePresenter implements ExChangeContract.Model {


    private final ExChangeContract.View view;

    public ExChangePresenter(ExChangeContract.View view) {
            this.view = view;
    }

    @Override
    public void getExChangeTask(String code) {
        RetrofitClient.getService()
                .getExChangeTask(code)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((ExChangeActivity) mView).getExChangeTask(data);
                    }
                });
    }

    @Override
    public void exchangeList(Double lng, Double lat, int distance) {
        getParams().put("need_charge",1);
        getParams().put("lng",String.valueOf(lng));
        getParams().put("lat",String.valueOf(lat));
        getParams().put("distance",distance);
        RetrofitClient.getService()
                .getVehicleListData(getParams())
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<List<VehicleListBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<VehicleListBean> data) {
                      view.initExChangeList(data);
                    }
                });
    }
}
