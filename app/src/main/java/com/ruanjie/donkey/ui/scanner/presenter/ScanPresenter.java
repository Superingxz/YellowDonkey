package com.ruanjie.donkey.ui.scanner.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.UnlockBean;
import com.ruanjie.donkey.ui.scanner.contract.ScanContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.scanner.presenter
 * 文件名:   ScanPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/8 11:52
 * 描述:     TODO
 */
public class ScanPresenter extends BasePresenter implements ScanContract.Model{

    private final ScanContract.View view;

    public ScanPresenter(ScanContract.View view) {
        this.view = view;
    }

    @Override
    public void unlock(String code) {
        RetrofitClient.getService()
                .getScanUnlockData(code)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<UnlockBean>() {
                    @Override
                    public void onSuccess(@Nullable UnlockBean data) {
                        view.unlockSuccess(data);
                    }
                });
    }

    @Override
    public void transVehicle(String code,String lng,String lat) {
        RetrofitClient.getService()
                .getTransVehicleData(code,lng,lat)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@Nullable String data) {
                        view.transVehicleSuccess();
                    }
                });
    }


}
