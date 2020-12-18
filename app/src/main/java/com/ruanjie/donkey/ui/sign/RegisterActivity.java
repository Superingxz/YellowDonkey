package com.ruanjie.donkey.ui.sign;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.CompoundButton;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.app.Constants;
import com.ruanjie.donkey.listener.IUserChecker;
import com.ruanjie.donkey.ui.main.MainActivity;
import com.ruanjie.donkey.ui.sign.contract.RegisterContract;
import com.ruanjie.donkey.ui.sign.model.AccountManager;
import com.ruanjie.donkey.ui.sign.presenter.RegisterPresenter;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.widget.TimerTextView;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxRegTool;
import com.vondear.rxtool.RxTextTool;
import com.vondear.rxtool.view.RxToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign
 * 文件名:   RegisterActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/1 21:06
 * 描述:     TODO
 */
public class RegisterActivity extends ToolbarActivity<RegisterPresenter> implements RegisterContract.View, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.et_phone)
    AppCompatEditText etPhone = null;
    @BindView(R.id.et_verification_code)
    AppCompatEditText etVerificationCode = null;
    @BindView(R.id.et_setting_password)
    AppCompatEditText etSettingPassword = null;
    @BindView(R.id.et_again_password)
    AppCompatEditText etRePassword = null;
    @BindView(R.id.cb_agreement)
    AppCompatCheckBox cbAgreement = null;
    @BindView(R.id.bt_register)
    AppCompatButton btRegister = null;
    @BindView(R.id.tv_agreement)
    AppCompatTextView tvAgreement = null;
    @BindView(R.id.tv_get_verification_code)
    TimerTextView tvGetVerificationCode = null;
    @BindView(R.id.iv_visible)
    AppCompatImageView ivIsVisble;
    @BindView(R.id.iv_invisible)
    AppCompatImageView ivIsInvisible;
    @BindView(R.id.et_name)
    AppCompatEditText etName;
    @BindView(R.id.et_id_number)
    AppCompatEditText etIdNumber;

    @OnClick(R.id.tv_get_verification_code)
    void onGetVerificationCode(){
        final String mPhone = etPhone.getText().toString().trim();
        if (checkPhone(mPhone)){
            getPresenter().getVerificationCode(mPhone, Constants.REGISTER);
        }
    }

    @OnClick(R.id.iv_visible)
    void onIsVisible(){
        if (etSettingPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            etSettingPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ImageUtil.loadImage(ivIsVisble,R.mipmap.visible_icon);
        } else {
            etSettingPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ImageUtil.loadImage(ivIsVisble,R.mipmap.invisible_icon);
        }

        final String mPassword = etSettingPassword.getText().toString();
        if (!TextUtils.isEmpty(mPassword)) {
            etSettingPassword.setSelection(mPassword.length());
        }
    }

    @OnClick(R.id.iv_invisible)
    void onIsInvisible(){
        if (etRePassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            etRePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ImageUtil.loadImage(ivIsInvisible,R.mipmap.visible_icon);
        } else {
            etRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ImageUtil.loadImage(ivIsInvisible,R.mipmap.invisible_icon);
        }

        final String mPassword = etRePassword.getText().toString();
        if (!TextUtils.isEmpty(mPassword)) {
            etRePassword.setSelection(mPassword.length());
        }
    }

    @OnClick(R.id.bt_register)
    void onRegister(){
        final String mPhone = etPhone.getText().toString().trim();
        final String mVerificationCode = etVerificationCode.getText().toString().trim();

        if (checkPhone(mPhone) && checkVerificationCode(mVerificationCode)){
            getPresenter().checkVerificationCode(mPhone,mVerificationCode,Constants.REGISTER);
        }

    }

    @OnClick(R.id.tv_agreement)
    void onAgreement(){
         getPresenter().userAgreement();
    }

    public static void start(Context context) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle(getString(R.string.register));
    }

    @Override
    protected void initialize() {
        RxTextTool.getBuilder(getString(R.string.read_and_agree))
                .append(getString(R.string.agreement))
                .setForegroundColor(ContextCompat.getColor(getContext(),R.color.text_yellow))
                .into(tvAgreement);
        cbAgreement.setOnCheckedChangeListener(this);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_register;
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

    private boolean checkVerificationCode(String verificationCode) {
        boolean isPass = true;

        if (TextUtils.isEmpty(verificationCode)){
            RxToast.error(getString(R.string.code_empty));
            etVerificationCode.setError(getString(R.string.code_empty));
            isPass = false;
        } else {
            etVerificationCode.setError(null);
        }
        return isPass;
    }
    @Override
    public boolean checkform(String phone, String password, String rePassword, String code,String name,String id) {
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

        if (TextUtils.isEmpty(password)) {
            etSettingPassword.setError(getString(R.string.password_empty));
            isPass = false;
        } else {
            etSettingPassword.setError(null);
        }

        if (TextUtils.isEmpty(rePassword)) {
            etRePassword.setError(getString(R.string.again_password_empty));
            if (!(rePassword.equals(password))) {
                etRePassword.setError(getString(R.string.again_password_error));
                isPass = false;
            }
        } else {
            etRePassword.setError(null);
        }

        if (TextUtils.isEmpty(name)) {
            etName.setError(getString(R.string.edit_name));
            isPass = false;
        } else {
            etName.setError(null);
        }

        if (TextUtils.isEmpty(id)) {
            etIdNumber.setError(getString(R.string.edit_id_number));
            isPass = false;
        }else if (!RxRegTool.isIDCard18(id)){
            RxToast.error(getString(R.string.id_error));
            isPass = false;
        } else {
            etName.setError(null);
        }

        return isPass;
    }

    @Override
    public void getVerificationCodeSuccess() {
        tvGetVerificationCode.start();
    }
    @Override
    public void getVerificationCodeFail(String errorMessage) {
        tvGetVerificationCode.cancel();
    }

    @Override
    public void verificationSuccess(String phone, String verificationCode) {
        final String mSettingPassword = etSettingPassword.getText().toString().trim();
        final String mRePassword = etRePassword.getText().toString().trim();
        final String mName = etName.getText().toString().trim();
        final String mIdNumber = etIdNumber.getText().toString().trim();

        if (checkform(phone,mSettingPassword,mRePassword,verificationCode,mName,mIdNumber)) {
            if (cbAgreement.isChecked()) {
                getPresenter().register(phone, mSettingPassword, mRePassword, verificationCode,mName,mIdNumber);
            }else {
                RxToast.showToast(getString(R.string.please_read_and_agree) + getString(R.string.agreement));
            }
        }
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            cbAgreement.setChecked(true);
        }else {
            cbAgreement.setChecked(false);
        }
    }

    @Override
    public void registerSuccess(String phone, String password) {
        RxToast.success("注册成功");
        getPresenter().login(phone,password);
    }

    @Override
    public void loginSuccess() {
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void alreadyLogin() {
                RxActivityTool.skipActivityAndFinishAll(getContext(), MainActivity.class);
            }

            @Override
            public void notLogin() {
                RxActivityTool.skipActivityAndFinishAll(getContext(),LoginActivity.class);
            }
        });
    }


}
