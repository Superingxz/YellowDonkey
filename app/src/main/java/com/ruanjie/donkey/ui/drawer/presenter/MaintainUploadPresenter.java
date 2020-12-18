package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.MainTainBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.drawer.MainTainUploadActivity;
import com.ruanjie.donkey.ui.drawer.MaintainHistoryActivity;
import com.ruanjie.donkey.ui.drawer.contract.MaintainHistoryContract;
import com.ruanjie.donkey.ui.drawer.contract.MaintainUploadContract;
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
public class MaintainUploadPresenter extends BasePresenter
        implements MaintainUploadContract.Model {


    @Override
    public void uploadMaintain(String content, String images) {
        RetrofitClient.getService()
                .uploadMaintain(content,images)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((MainTainUploadActivity) mView).uploadMaintain(data);
                    }
                });
    }

    @Override
    public void uploadImage(String content) {
        RetrofitClient.getService()
                .uploadImage(content)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<UploadBean>() {
                    @Override
                    public void onSuccess(UploadBean data) {
                        ((MainTainUploadActivity) mView).uploadImage(data);
                    }
                });
    }
}
