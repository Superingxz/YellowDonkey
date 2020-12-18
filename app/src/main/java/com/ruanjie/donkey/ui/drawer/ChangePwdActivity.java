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
import com.ruanjie.donkey.ui.drawer.contract.ChangePwdContract;
import com.ruanjie.donkey.ui.drawer.presenter.ChangePwdPresenter;
import com.ruanjie.donkey.widget.TimerTextView;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.RegularUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePwdActivity extends ToolbarActivity<ChangePwdPresenter>
implements ChangePwdContract.View{

    int mType;
    @BindView(R.id.etPhone)
    AppCompatEditText etPhone;
    @BindView(R.id.ttvTimer)
    TimerTextView ttvTimer;
    @BindView(R.id.etCode)
    AppCompatEditText etCode;
    @BindView(R.id.tvNext)
    TextView tvNext;


    public static void start(Context context, int type) {
        Intent starter = new Intent(context, ChangePwdActivity.class);
        starter.putExtra("type", type);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("找回密码")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initialize() {
        mType = getIntent().getIntExtra("type", 0);
        getToolbar().setTitle(mType == 0 ? "修改密码" : "找回密码");


    }

    @OnClick({R.id.tvNext, R.id.ttvTimer})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNext:
                String codeStr = etCode.getText().toString().trim();
                if(TextUtils.isEmpty(codeStr)){
                    ToastUtil.s("请输入验证码");
                    return;
                }

           SetPwdActivity.start(getContext());
                break;
            case R.id.ttvTimer:
                String phoneStr = etPhone.getText().toString().trim();
                if(TextUtils.isEmpty(phoneStr)){
                    ToastUtil.s("请输入手机号");
                    return;
                }
                if(!RegularUtil.isMobileSimple(phoneStr)){
                    ToastUtil.s("请输入正确的手机号");
                    return;
                }
                //1:注册,2:忘记密码,3:修改密码,5:修改手机号
                getPresenter().getCode(phoneStr,3);

                break;
        }
    }

    @Override
    public ChangePwdPresenter createPresenter() {
        return new ChangePwdPresenter();
    }

    @Override
    public void getCode(String data) {
        ttvTimer.start();
    }
}
