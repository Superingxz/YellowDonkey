package com.ruanjie.donkey.ui.unlock.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.ProgressNetworkTransformer;
import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.ruanjie.donkey.ui.unlock.contract.WaitUnlockContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.RxCallback;
import com.softgarden.baselibrary.utils.L;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.unlock.presenter
 * 文件名:   WaitUnlockPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/10 18:54
 * 描述:     TODO
 */
public class WaitUnlockPresenter extends BasePresenter implements WaitUnlockContract.Model {

    private final WaitUnlockContract.View view;

    public WaitUnlockPresenter(WaitUnlockContract.View view) {
        this.view = view;
    }
    @Override
    public void unlock(String code) {
        RetrofitClient.getService()
                .getVehicleDetailData(code)
                .compose(new ProgressNetworkTransformer<>(view))
                .subscribe(new RxCallback<VehicleDetailBean>() {
                    @Override
                    public void onSuccess(@Nullable VehicleDetailBean data) {
                        L.i("解锁成功",String.valueOf(data.getStatus()));
                            view.unlockSuccess(data);
                        }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        view.unlockFail();
                    }
                });
    }


}
