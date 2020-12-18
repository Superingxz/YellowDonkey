package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.ui.drawer.WithDrawActivity;
import com.ruanjie.donkey.ui.drawer.contract.AllBalanceContract;
import com.ruanjie.donkey.ui.drawer.contract.WithDrawContract;
import com.ruanjie.donkey.ui.drawer.fragment.AllBalanceFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   LoginPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/29 11:28
 * 描述:     TODO
 */
public class WithDrawPresenter extends BasePresenter implements WithDrawContract.Model {



    @Override
    public void withDraw(String money, String bankcard, String bank) {
        RetrofitClient.getService()
                .withDraw(money,bankcard,bank)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((WithDrawActivity) mView).withDraw(data);
                    }
                });
    }
}
