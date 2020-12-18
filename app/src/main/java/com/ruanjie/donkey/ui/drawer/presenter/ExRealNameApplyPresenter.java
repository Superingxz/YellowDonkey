package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.ExRealNameBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.drawer.EXRealNameApplyActivity;
import com.ruanjie.donkey.ui.drawer.RealNameApplyActivity;
import com.ruanjie.donkey.ui.drawer.contract.ExRealNameApplyContract;
import com.ruanjie.donkey.ui.drawer.contract.RealNameApplyContract;
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
public class ExRealNameApplyPresenter extends BasePresenter implements ExRealNameApplyContract.Model {


    @Override
    public void uploadImage(int which,String content) {
        RetrofitClient.getService()
                .uploadImage(content)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<UploadBean>() {
                    @Override
                    public void onSuccess(@android.support.annotation.Nullable UploadBean data) {
                        ((EXRealNameApplyActivity) mView).uploadImage(which,data);
                    }
                });
    }

    @Override
    public void exRealNameApply(String real_name, String id_card, String id_card_photo
            , String id_card_photo2, String id_card_photo3) {
        RetrofitClient.getService()
                .exRealNameApply(real_name,id_card,id_card_photo,id_card_photo2,id_card_photo3)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@android.support.annotation.Nullable String data) {
                        ((EXRealNameApplyActivity) mView).exRealNameApply(data);
                    }
                });
    }

    @Override
    public void getExRealNameApplyData() {
        RetrofitClient.getService()
                .getExRealNameApplyData()
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<ExRealNameBean>() {
                    @Override
                    public void onSuccess(@android.support.annotation.Nullable ExRealNameBean data) {
                        ((EXRealNameApplyActivity) mView).getExRealNameApplyData(data);
                    }
                });
    }


}
