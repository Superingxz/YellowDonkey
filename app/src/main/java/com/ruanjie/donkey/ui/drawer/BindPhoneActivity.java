package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.noober.background.view.BLView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.ui.drawer.contract.BindPhoneContract;
import com.ruanjie.donkey.ui.drawer.presenter.BindPhonePresenter;
import com.ruanjie.donkey.ui.main.MainActivity;
import com.ruanjie.donkey.utils.SPManager;
import com.ruanjie.donkey.widget.TimerTextView;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends ToolbarActivity<BindPhonePresenter>
        implements BindPhoneContract.View {


    @BindView(R.id.etPhone)
    AppCompatEditText etPhone;
    @BindView(R.id.ttvTimer)
    TimerTextView ttvTimer;
    @BindView(R.id.etCode)
    AppCompatEditText etCode;
    @BindView(R.id.etPwd1)
    AppCompatEditText etPwd1;
    @BindView(R.id.vPwdEye1)
    BLView vPwdEye1;
    @BindView(R.id.llPwdEye1)
    LinearLayout llPwdEye1;
    @BindView(R.id.etPwd2)
    AppCompatEditText etPwd2;
    @BindView(R.id.vPwdEye2)
    BLView vPwdEye2;
    @BindView(R.id.llPwdEye2)
    LinearLayout llPwdEye2;
    @BindView(R.id.tvBind)
    TextView tvBind;
    @BindView(R.id.tvCheck)
    TextView tvCheck;
    @BindView(R.id.tvProtocol)
    TextView tvProtocol;
    String mJsonStr;
    LoginBean mLoginBean;

    public static void start(Context context, String jsonStr) {
        Intent starter = new Intent(context, BindPhoneActivity.class);
        starter.putExtra("jsonStr", jsonStr);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("绑定手机")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initialize() {
        mJsonStr = getIntent().getStringExtra("jsonStr");
        mLoginBean = new Gson().fromJson(mJsonStr, LoginBean.class);


    }

    @Override
    public BindPhonePresenter createPresenter() {
        return new BindPhonePresenter();
    }

    @Override
    public void bindPhone(LoginBean data) {
        getPresenter().getUserInfo(data.getUser_id() + "", data.getToken());
    }

    @Override
    public void getCode(String data) {
        ttvTimer.start();
    }

    @Override
    public void getUserInfo(LoginBean data) {
        SPManager.setLoginBean(data);
        MainActivity.start(getContext(), true);
    }

    @OnClick({R.id.ttvTimer, R.id.tvBind, R.id.llPwdEye1
            , R.id.llPwdEye2, R.id.vPwdEye1, R.id.vPwdEye2
            , R.id.tvCheck, R.id.tvProtocol})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvProtocol:

                break;
            case R.id.ttvTimer:
                String phoneStr = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneStr)) {
                    ToastUtil.s("请输入手机号");
                    return;
                }
                getPresenter().getCode(phoneStr, 4);

                break;
            case R.id.tvBind:
                if (mLoginBean != null) {
                    String phoneStr1 = etPhone.getText().toString().trim();
                    String codeStr = etCode.getText().toString().trim();
                    String pwdStr1 = etPwd1.getText().toString().trim();
                    String pwdStr2 = etPwd2.getText().toString().trim();
                    if (TextUtils.isEmpty(phoneStr1)) {
                        ToastUtil.s("请输入手机号");
                        return;
                    }
                    if (TextUtils.isEmpty(codeStr)) {
                        ToastUtil.s("请输入验证码");
                        return;
                    }
                    if (TextUtils.isEmpty(pwdStr1)) {
                        ToastUtil.s("请输入密码");
                        return;
                    }
                    if (TextUtils.isEmpty(pwdStr2)) {
                        ToastUtil.s("请输入确认密码");
                        return;
                    }
                    if (!pwdStr1.equals(pwdStr2)) {
                        ToastUtil.s("两次密码不一致");
                        return;
                    }
                    boolean selected = tvCheck.isSelected();
                    if (!selected) {
                        ToastUtil.s("请阅读并同意用户协议");
                        return;
                    }

                    getPresenter().bindPhone(mLoginBean.getUser_id() + "", mLoginBean.getToken(), phoneStr1, codeStr, pwdStr1, pwdStr2);
                }

                break;
            case R.id.llPwdEye1:
            case R.id.vPwdEye1:
                setSelect(etPwd1, vPwdEye1);
                break;
            case R.id.llPwdEye2:
            case R.id.vPwdEye2:
                setSelect(etPwd2, vPwdEye2);
                break;
            case R.id.tvCheck:
                boolean isSelected = !tvCheck.isSelected();
                tvCheck.setSelected(isSelected);
                tvCheck.setCompoundDrawablesWithIntrinsicBounds(
                        isSelected ? R.mipmap.check : R.mipmap.un_check
                        , 0, 0, 0);
                break;
        }
    }

    private void setSelect(AppCompatEditText editText, BLView view) {
        boolean isSelected = !view.isSelected();
        view.setSelected(isSelected);
        editText.setTransformationMethod(isSelected
                ? HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance());
        editText.setSelection(editText.getText().length());
    }
}
