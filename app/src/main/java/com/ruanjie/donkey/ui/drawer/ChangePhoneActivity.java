package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.app.Constants;
import com.ruanjie.donkey.ui.drawer.contract.ChangePhoneContract;
import com.ruanjie.donkey.ui.drawer.presenter.ChangePhonePresenter;
//import com.ruanjie.donkey.ui.sign.LoginActivity;
import com.ruanjie.donkey.ui.sign.LoginActivity;
import com.ruanjie.donkey.utils.SPManager;
import com.ruanjie.donkey.widget.TimerTextView;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.RegularUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePhoneActivity extends ToolbarActivity<ChangePhonePresenter>
        implements ChangePhoneContract.View {


    @BindView(R.id.etPhone)
    AppCompatEditText etPhone;
    @BindView(R.id.etCode)
    AppCompatEditText etCode;
    @BindView(R.id.ttvTimer)
    TimerTextView ttvTimer;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    public static void start(Context context) {
        Intent starter = new Intent(context, ChangePhoneActivity.class);
//        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("修改手机号")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void initialize() {

    }


    @OnClick({R.id.ttvTimer, R.id.tvSubmit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ttvTimer:
                String phoneStr = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneStr)) {
                    etPhone.setError("手机号不能为空！");
                    return;
                }
                if (!RegularUtil.isMobileSimple(phoneStr)) {
                    etPhone.setError("请输入正确手机号！");
                    return;
                }
                //1:注册,2:忘记密码,3:修改密码,5:修改手机号
                getPresenter().getCode(phoneStr, Constants.CHANGE_PHONE_NUMBER);
                break;
            case R.id.tvSubmit:
                String phoneStr1 = etPhone.getText().toString().trim();
                String codeStr = etCode.getText().toString().trim();

                if (TextUtils.isEmpty(phoneStr1)) {
                    etPhone.setError("手机号不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(codeStr)) {
                    etCode.setError("验证码不能为空！");
                    return;
                }
                getPresenter().changePhone(phoneStr1, codeStr);

                break;
        }
    }

    @Override
    public ChangePhonePresenter createPresenter() {
        return new ChangePhonePresenter();
    }

    @Override
    public void getCode(String data) {
        ttvTimer.start();
    }

    @Override
    public void changePhone(String data) {
        ToastUtil.s("修改成功，请重新登录");
        SPManager.removeLoginBean();
        LoginActivity.start(getContext(), true);

    }
}
