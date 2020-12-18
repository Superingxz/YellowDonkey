package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.TokenBean;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.SetPwdActivity;
import com.ruanjie.donkey.ui.drawer.contract.ChangeNicknameContract;
import com.ruanjie.donkey.ui.drawer.contract.SetPwdContract;
import com.ruanjie.donkey.ui.sign.ChangePasswordActivity;
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
public class SetPwdPresenter extends BasePresenter implements SetPwdContract.Model,SetPwdContract.ChangePwd{


    @Override
    public void changePwd(String old_password, String new_password, String re_password) {
        RetrofitClient.getService()
                .changePwd(old_password,new_password,re_password)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<TokenBean>() {
                    @Override
                    public void onSuccess(TokenBean data) {
                        ((SetPwdActivity) mView).changePwd(data);
                    }
                });
    }

    //忘记密码
    @Override
    public void resetPwd(String phone, String code, String new_password, String re_password) {
        RetrofitClient.getService()
                .resetPwd(phone,code,new_password,re_password)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((ChangePasswordActivity) mView).change(data);
                    }
                });
    }
}
