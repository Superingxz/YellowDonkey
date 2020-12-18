package com.ruanjie.donkey.ui.upload.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.upload.contract.UploadContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.upload.presenter
 * 文件名:   FaultPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/14 15:15
 * 描述:     TODO
 */
public class UploadPresenter extends BasePresenter implements UploadContract.Model {

    private final UploadContract.View view;

    public UploadPresenter(UploadContract.View view) {
        this.view = view;
    }

    @Override
    public void uploadFault(String content, String images) {
        RetrofitClient.getService()
                .uploadFault(content,images)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.upload(data);
                    }
                });
    }

    @Override
    public void uploadIlleagal(String content, String images) {
        RetrofitClient.getService()
                .uploadIllegal(content,images)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.upload(data);
                    }
                });
    }

    @Override
    public void uploadImage(String content) {
        RetrofitClient.getService()
                .uploadImage(content)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<UploadBean>() {
                    @Override
                    public void onSuccess(UploadBean data) {
                        view.uploadImage(data);
                    }
                });
    }
}
