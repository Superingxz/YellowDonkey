package com.ruanjie.donkey.ui.drawer.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.UserInfoActivity;
import com.ruanjie.donkey.ui.drawer.contract.ChangeNicknameContract;
import com.ruanjie.donkey.ui.drawer.contract.UserInfoContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   LoginPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/29 11:28
 * 描述:     TODO
 */
public class ChangeNicknamePresenter extends BasePresenter implements ChangeNicknameContract.Model {


    @Override
    public void changeUserInfo(int sex, String nickname, String birthday) {
        RetrofitClient.getService()
                .changeUserInfo(sex,nickname,birthday)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((ChangeNicknameActivity) mView).changeUserInfo(nickname);
                    }
                });
    }
}
