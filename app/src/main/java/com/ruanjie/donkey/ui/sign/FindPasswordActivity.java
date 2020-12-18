package com.ruanjie.donkey.ui.sign;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Patterns;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.app.Constants;
import com.ruanjie.donkey.event.PhoneEvent;
import com.ruanjie.donkey.ui.sign.contract.FindPasswordContract;
import com.ruanjie.donkey.ui.sign.presenter.FindPasswordPresenter;
import com.ruanjie.donkey.widget.TimerTextView;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.vondear.rxtool.view.RxToast;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign
 * 文件名:   FindPasswordActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/1 21:16
 * 描述:     TODO
 */
public class FindPasswordActivity extends ToolbarActivity<FindPasswordPresenter> implements FindPasswordContract.View {

    @BindView(R.id.et_phone)
    AppCompatEditText etPhone = null;
    @BindView(R.id.et_verification_code)
    AppCompatEditText etVerificationCode = null;
    @BindView(R.id.tv_get_verification_code)
    TimerTextView tvGetVerificationCode = null;

    @OnClick(R.id.tv_get_verification_code)
    void onGetCode(){
        final String mPhone = etPhone.getText().toString().trim();
        if (checkPhone(mPhone)){
            getPresenter().getVerificationCode(mPhone, Constants.FORGET_PASSWORD);
        }
    }

    @OnClick(R.id.bt_next)
    void onNext(){
        final String mPhone = etPhone.getText().toString().trim();
        final String mVerificationCode = etVerificationCode.getText().toString().trim();

        if (checkform(mPhone,mVerificationCode)){
            getPresenter().checkVerificationCode(mPhone,mVerificationCode,Constants.FORGET_PASSWORD);
        }
    }

    public static void start(Context context) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, FindPasswordActivity.class));
    }

    @Override
    public FindPasswordPresenter createPresenter() {
        return new FindPasswordPresenter(this);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle(getString(R.string.find_password));
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_find_password;
    }

    @Override
    public boolean checkPhone(String phone) {
        boolean isPass = true;

        if (TextUtils.isEmpty(phone)){
            etPhone.setError(getString(R.string.phone_empty));
            isPass = false;
        } else if (!Patterns.PHONE.matcher(phone).matches() || phone.length() != 11) {
            RxToast.error(getString(R.string.phone_error));
            etPhone.setError(getString(R.string.phone_error));
            isPass = false;
        }else {
            etPhone.setError(null);
        }
        return isPass;
    }

    @Override
    public boolean checkform(String phone, String code) {

        boolean isPass = true;

        if (TextUtils.isEmpty(phone)){
            etPhone.setError(getString(R.string.phone_empty));
            isPass = false;
        } else if (!Patterns.PHONE.matcher(phone).matches() || phone.length() != 11) {
            RxToast.error(getString(R.string.phone_error));
            etPhone.setError(getString(R.string.phone_error));
            isPass = false;
        }else {
            etPhone.setError(null);
        }

        if (TextUtils.isEmpty(code)){
            RxToast.error(getString(R.string.code_empty));
            etVerificationCode.setError(getString(R.string.code_empty));
            isPass = false;
        }else {
            etVerificationCode.setError(null);
        }

        return isPass;
    }

    @Override
    public void getVerificationCodeSuccess() {
        tvGetVerificationCode.start();
    }

    @Override
    public void getVerificationCodeFail(String message) {
        tvGetVerificationCode.cancel();
    }

    @Override
    public void verificationSuccess(String phone, String verificationCode) {
        EventBus.getDefault().postSticky(new PhoneEvent(phone,verificationCode));
        ChangePasswordActivity.start(getContext());
    }
}
