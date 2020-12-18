package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.api.HostUrl;
import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.VersionBean;
import com.ruanjie.donkey.ui.drawer.presenter.SettingPresenter;
import com.ruanjie.donkey.ui.sign.LoginActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.SPManager;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;
import com.softgarden.baselibrary.utils.AppUtil;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends ToolbarActivity<SettingPresenter> {


    @BindView(R.id.stvAboutWe)
    SuperTextView stvAboutWe;
    @BindView(R.id.stvChangePwd)
    SuperTextView stvChangePwd;
    @BindView(R.id.stvCheckVersion)
    SuperTextView stvCheckVersion;
    @BindView(R.id.tvLogout)
    TextView tvLogout;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Override
    public SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("设置")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initialize() {

    }
    private void showUpdateDia(String urlStr) {
        DiaLogUtils.showTipDialog(getContext(), "发现新版本", "是否进行下载"
                , "取消", "确定"
                , new PromptDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                        if (isPositiveClick) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url =
                                    Uri.parse(HostUrl.HOST_URL
                                            .substring(0, HostUrl.HOST_URL.length() - 1) + urlStr);
                            LogUtils.i("content_url", "content_url = " + content_url);
                            intent.setData(content_url);
                            startActivity(intent);
                        }
                        dialog.dismiss();
                    }
                });
    }

    @OnClick({R.id.stvAboutWe, R.id.stv_user_agreement,R.id.stvChangePwd, R.id.stvCheckVersion, R.id.tvLogout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stvAboutWe:
                getPresenter().about();
                break;
            case R.id.stv_user_agreement:
                getPresenter().userAgreement();
                break;
            case R.id.stvChangePwd:
                SetPwdActivity.start(getContext());
//                ChangePwdActivity.start(getContext(),0);
                break;
            case R.id.stvCheckVersion:
                RetrofitClient.getService()
                        .checkUpload()
                        .compose(new NetworkTransformer<>(this))
                        .subscribe(new RxCallback<VersionBean>() {
                            @Override
                            public void onSuccess(@Nullable VersionBean data) {
                                String versionCode = AppUtil.getVersionCode(getContext());
                                String version = data.getVersion();
                                if (!TextUtils.isEmpty(versionCode) && !TextUtils.isEmpty(version)) {
                                    int vCode = Integer.parseInt(versionCode);
                                    int versionInt = Integer.parseInt(version);
                                    LogUtils.i("tag", "vCode = " + vCode
                                            + "\nversionInt = " + versionInt);
                                    if (versionInt > vCode) {
                                        //弹框提示有新版本
                                        showUpdateDia(data.getUrl());
                                    }else{
                                        ToastUtil.s("当前已是最新版本");
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtil.s("当前已是最新版本");
                                super.onError(e);
                            }
                        });
                break;
            case R.id.tvLogout:
                DiaLogUtils.showTipDialog(getContext(), "温馨提示"
                        , "确认退出当前账户?"
                        , "取消"
                        , "确定"
                        , new PromptDialog.OnButtonClickListener() {
                            @Override
                            public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                if (isPositiveClick) {
                                    SPManager.removeLoginBean();
                                    LoginActivity.start(getContext(), true);
                                }
                            }
                        });

                break;
        }
    }

}
