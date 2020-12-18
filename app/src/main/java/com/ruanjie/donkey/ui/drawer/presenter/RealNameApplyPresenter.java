package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.RealNameApplyActivity;
import com.ruanjie.donkey.ui.drawer.UserInfoActivity;
import com.ruanjie.donkey.ui.drawer.contract.ChangeNicknameContract;
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
public class RealNameApplyPresenter extends BasePresenter implements RealNameApplyContract.Model {


    @Override
    public void uploadImage(int which,String content) {
        RetrofitClient.getService()
                .uploadImage(content)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<UploadBean>() {
                    @Override
                    public void onSuccess(@android.support.annotation.Nullable UploadBean data) {
                        ((RealNameApplyActivity) mView).uploadImage(which,data);
                    }
                });
    }

    @Override
    public void realNameApply(String username, String idcard, String idcard_font, String idcard_back, String idcard_hand) {
        RetrofitClient.getService()
                .realNameApply(username,idcard,idcard_font,idcard_back,idcard_hand)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@android.support.annotation.Nullable String data) {
                        ((RealNameApplyActivity) mView).realNameApply(data);
                    }
                });
    }
}
