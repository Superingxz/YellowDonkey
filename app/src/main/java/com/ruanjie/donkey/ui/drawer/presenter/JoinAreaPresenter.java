package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.JoinAreaInfoBean;
import com.ruanjie.donkey.bean.JoinCityInfoBean;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.JoinAreaActivity;
import com.ruanjie.donkey.ui.drawer.JoinCityActivity;
import com.ruanjie.donkey.ui.drawer.contract.ChangeNicknameContract;
import com.ruanjie.donkey.ui.drawer.contract.JoinAreaContract;
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
public class JoinAreaPresenter extends BasePresenter implements JoinAreaContract.Model {

    @Override
    public void joinArea(String name, String phone, String area_code
            , String area_id) {
        RetrofitClient.getService()
                .joinArea(name,phone,area_code,area_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((JoinAreaActivity) mView).joinArea(data);
                    }
                });
    }

    @Override
    public void getJoinAreaInfo() {
        RetrofitClient.getService()
                .getJoinAreaInfo()
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<JoinAreaInfoBean>() {
                    @Override
                    public void onSuccess(JoinAreaInfoBean data) {
                        ((JoinAreaActivity) mView).getJoinAreaInfo(data);
                    }
                });
    }
}
