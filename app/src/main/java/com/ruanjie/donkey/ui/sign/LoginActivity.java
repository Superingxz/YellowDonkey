package com.ruanjie.donkey.ui.sign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.AliLoginBean;
import com.ruanjie.donkey.bean.AuthResult;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.ui.drawer.BindPhoneActivity;
import com.ruanjie.donkey.ui.main.MainActivity;
import com.ruanjie.donkey.ui.sign.contract.LoginContract;
import com.ruanjie.donkey.ui.sign.presenter.LoginPresenter;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.SPManager;
import com.ruanjie.donkey.utils.onClickEvent;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ActivityManager;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxTextTool;
import com.vondear.rxtool.view.RxToast;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign
 * 文件名:   LoginActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/1 20:46
 * 描述:     TODO
 */
public class LoginActivity extends ToolbarActivity<LoginPresenter> implements LoginContract.View, UMAuthListener {

    @BindView(R.id.tv_wechat_login)
    AppCompatTextView tv_wechat_login;
    @BindView(R.id.tv_alipay_login)
    AppCompatTextView tv_alipay_login;

    @BindView(R.id.et_phone)
    AppCompatEditText etPhone = null;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword = null;
    @BindView(R.id.tv_register)
    AppCompatTextView tvRegister = null;
    @BindView(R.id.iv_is_visible)
    AppCompatImageView isVisible = null;

    @OnClick(R.id.iv_is_visible)
    void onIsVisible() {
        if (etPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ImageUtil.loadImage(isVisible,R.mipmap.visible_icon);
        } else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ImageUtil.loadImage(isVisible,R.mipmap.invisible_icon);
        }
        final String mPassword = etPassword.getText().toString();
        if (!TextUtils.isEmpty(mPassword)) {
            etPassword.setSelection(mPassword.length());
        }
    }

//    @OnClick(R.id.tv_wechat_login)
//    void onWechatLogin() {
//        platform = 1;
//        getThirdUserInfo(1);
//    }
//
//    @OnClick(R.id.tv_alipay_login)
//    void onAlipayLogin() {
//        getPresenter().getAliLoginData("2088531081937001");
//    }


    @OnClick(R.id.bt_login)
    void onLogin() {
        final String mPhone = etPhone.getText().toString().trim();
        final String mPassword = etPassword.getText().toString().trim();
        if (checkform(mPhone, mPassword)) {
            getPresenter().login(mPhone, mPassword);
        }
    }

    @OnClick(R.id.tv_register)
    void onRegister() {
        RegisterActivity.start(getContext());
    }

    @OnClick(R.id.tv_forget_password)
    void onForgetPassword() {
        FindPasswordActivity.start(getContext());
    }

    /*@OnLongClick(R.id.tv_forget_password)
    boolean asd() {
        etPhone.setText("13392668020");
        etPassword.setText("a123456");
        return true;
    }*/

    public static void start(Context context) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void start(Context context, boolean isCloseActs) {
        Intent starter = new Intent(context, LoginActivity.class);
//        mIsCloseActs = isCloseActs;
//        starter.putExtra();
        if (isCloseActs) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(starter);
    }


    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackButton(R.mipmap.close)
                .setTitle(getString(R.string.login));
    }

    @Override
    protected void initialize() {
        RxTextTool.getBuilder(getString(R.string.no_account))
                .append(getString(R.string.register))
                .setForegroundColor(ContextCompat.getColor(getContext(), R.color.text_yellow))
                .into(tvRegister);

        tv_wechat_login.setOnClickListener(new onClickEvent(2000) {
            @Override
            public void singleClick(View v) {
                platform = 1;
                getThirdUserInfo(1);
            }
        });

        tv_alipay_login.setOnClickListener(new onClickEvent(2000) {
            @Override
            public void singleClick(View v) {
                getPresenter().getAliLoginData("2088531081937001");
            }
        });
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void loginSuccess() {
        RxActivityTool.skipActivityAndFinishAll(getContext(),MainActivity.class);
    }

    @Override
    public boolean checkform(String phone, String password) {

        boolean isPass = true;

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError(getString(R.string.phone_empty));
            isPass = false;
        } else if (!Patterns.PHONE.matcher(phone).matches() || phone.length() != 11) {
            RxToast.error(getString(R.string.phone_error));
            etPhone.setError(getString(R.string.phone_error));
            isPass = false;
        } else {
            etPhone.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.password_empty));
            isPass = false;
        } else {
            etPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public void getAliLoginData(AliLoginBean data) {
        aLiPayLogin(data.getParams());
    }

    @Override
    public void alipayLogin(LoginBean data) {
        if (TextUtils.isEmpty(data.getPhone())) {
            BindPhoneActivity.start(getContext(),new Gson().toJson(data));
        } else {
            SPManager.setLoginBean(data);

            MainActivity.start(this, true);
        }
    }

    @Override
    public void wechatLogin(LoginBean data) {
        if (TextUtils.isEmpty(data.getPhone())) {
            BindPhoneActivity.start(getContext(),new Gson().toJson(data));
        } else {
            SPManager.setLoginBean(data);

            MainActivity.start(this, true);
        }

    }

    int platform;//1 微信 2 支付宝

    private void getThirdUserInfo(int platform) {
        SHARE_MEDIA share_media = null;
        switch (platform) {
            case 1:
                share_media = SHARE_MEDIA.WEIXIN;
                break;
            case 2:
                share_media = SHARE_MEDIA.ALIPAY;
                break;
        }

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(getActivity()).setShareConfig(config);
        UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), share_media, this);
//        UMShareAPI.get(getActivity()).doOauthVerify(getActivity(), share_media, this);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        L.i("三方登录", "开始调起三方平台");
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        LogUtils.logMap("三方登录", map);
//        String unique_code = map.get("uid");
        String access_token = map.get("access_token");
        String type = "";
        switch (platform) {
            case 1:
                type = "wechat";
                String openid = map.get("openid");

                getPresenter().wechatLogin("wechat", access_token, openid);
                break;
            case 2:

                break;
        }
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        ToastUtil.s("登录出错，请重试");
        L.i("三方登录", "调起三方平台出错 = " + throwable.toString());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        L.i("三方登录", "取消调起三方平台 = " + i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void aLiPayLogin(String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtil.s("授权成功");
                        String auth_code = authResult.getAuthCode();
                        getPresenter().alipayLogin(auth_code);
                    } else {

                        LogUtils.i("AliPay_Auth", "getResult = " + authResult.getResult()
                                + "\ngetResultCode = " + authResult.getResultCode()
                                + "\ngetResultStatus = " + authResult.getResultStatus()
                                + "\ngetAuthCode = " + authResult.getAuthCode()
                                + "\ngetMemo = " + authResult.getMemo()
                        );
                        // 其他状态值则为授权失败
                        ToastUtil.s("授权失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().exitApp();
    }
}
