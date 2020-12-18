package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.UserInfoActivity;
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
public class UserInfoPresenter extends BasePresenter implements UserInfoContract.Model {


//    private final LoginContract.View view;

    public UserInfoPresenter() {
//        this.view = view;
    }


    @Override
    public void uploadImage(String content) {
        RetrofitClient.getService()
                .uploadImage(content)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<UploadBean>() {
                    @Override
                    public void onSuccess(@android.support.annotation.Nullable UploadBean data) {
                        ((UserInfoActivity) mView).uploadImage(data);
                    }
                });
    }

    @Override
    public void uploadImage2(List<String> pics) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (String path : pics) {
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            parts.add(MultipartBody.Part.createFormData("name", file.getName(), requestBody));
        }
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), "image");
//        RequestBody requestBody2 = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), "image");
        RetrofitClient.getService()
                .uploadImage2(parts)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((UserInfoActivity) mView).uploadImage2(data);
                    }
                });
    }

    @Override
    public void changeHead(String avatar) {
        RetrofitClient.getService()
                .changeHead(avatar)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<UploadBean>() {
                    @Override
                    public void onSuccess(UploadBean data) {
                        ((UserInfoActivity) mView).changeHead(data);
                    }
                });
    }

    @Override
    public void changeUserInfo(int type,int sex, String nickname, String birthday) {
        RetrofitClient.getService()
                .changeUserInfo(sex, nickname, birthday)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((UserInfoActivity) mView).changeUserInfo(type, sex, nickname, birthday);
                    }
                });
    }

    @Override
    public void getUserInfo() {
        RetrofitClient.getService()
                .getUserInfo()
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean data) {
                        ((UserInfoActivity) mView).getUserInfo(data);
                    }
                });
    }
}
