package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.TokenBean;
import com.ruanjie.donkey.ui.drawer.contract.SetPwdContract;
import com.ruanjie.donkey.ui.drawer.presenter.SetPwdPresenter;
import com.ruanjie.donkey.ui.sign.LoginActivity;
import com.ruanjie.donkey.utils.OtherUtils;
import com.ruanjie.donkey.utils.SPManager;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SetPwdActivity extends ToolbarActivity<SetPwdPresenter>
        implements SetPwdContract.View {

    String mCodeStr;
    @BindView(R.id.etPwd1)
    AppCompatEditText etPwd1;
    @BindView(R.id.etPwd2)
    AppCompatEditText etPwd2;
    @BindView(R.id.etPwd3)
    AppCompatEditText etPwd3;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.vEye1)
    View vEye1;
    @BindView(R.id.rlEye1)
    RelativeLayout rlEye1;
    @BindView(R.id.vEye2)
    View vEye2;
    @BindView(R.id.rlEye2)
    RelativeLayout rlEye2;
    @BindView(R.id.vEye3)
    View vEye3;
    @BindView(R.id.rlEye3)
    RelativeLayout rlEye3;


    public static void start(Context context) {
        Intent starter = new Intent(context, SetPwdActivity.class);
//        starter.putExtra("codeStr", codeStr);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("修改密码")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    protected void initialize() {


    }

    @OnClick({R.id.tvSubmit, R.id.rlEye1, R.id.rlEye2, R.id.rlEye3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlEye1:
                OtherUtils.changeEye(vEye1, etPwd1);
                break;
            case R.id.rlEye2:
                OtherUtils.changeEye(vEye2, etPwd2);
                break;
            case R.id.rlEye3:
                OtherUtils.changeEye(vEye3, etPwd3);
                break;
            case R.id.tvSubmit:
                if (OtherUtils.stringIsNull(etPwd1, "请输入当前密码")) {
                    return;
                }
                if (OtherUtils.stringIsNull(etPwd2, "请输入密码")) {
                    return;
                }
                if (OtherUtils.stringIsNull(etPwd3, "请输入确认密码")) {
                    return;
                }
                if (!OtherUtils.stringIsSame(etPwd2, etPwd3, "两次密码不一致")) {
                    return;
                }
                String pwdStr1 = etPwd1.getText().toString().trim();
                String pwdStr2 = etPwd2.getText().toString().trim();
                String pwdStr3 = etPwd3.getText().toString().trim();
                getPresenter().changePwd(pwdStr1, pwdStr2, pwdStr3);
                break;

        }
    }

    @Override
    public SetPwdPresenter createPresenter() {
        return new SetPwdPresenter();
    }

    @Override
    public void changePwd(TokenBean data) {
        ToastUtil.s("修改成功，请重新登录");
        SPManager.removeLoginBean();
        LoginActivity.start(getContext(), true);
    }
}
