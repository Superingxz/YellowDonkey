package com.ruanjie.donkey.ui.sign;

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
import com.ruanjie.donkey.event.PhoneEvent;
import com.ruanjie.donkey.ui.drawer.contract.SetPwdContract;
import com.ruanjie.donkey.ui.drawer.presenter.SetPwdPresenter;
import com.ruanjie.donkey.utils.OtherUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordActivity extends ToolbarActivity<SetPwdPresenter>
        implements SetPwdContract.Change {

    @BindView(R.id.etPwd2)
    AppCompatEditText etPwd2;
    @BindView(R.id.etPwd3)
    AppCompatEditText etPwd3;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.vEye2)
    View vEye2;
    @BindView(R.id.rlEye2)
    RelativeLayout rlEye2;
    @BindView(R.id.vEye3)
    View vEye3;
    @BindView(R.id.rlEye3)
    RelativeLayout rlEye3;

    private String phone ="";
    private String code = "";

    public static void start(Context context) {
//        starter.putExtra("codeStr", codeStr);
        context.startActivity(new Intent(context, ChangePasswordActivity.class));
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("设置新密码")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.tvSubmit, R.id.rlEye2, R.id.rlEye3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlEye2:
                OtherUtils.changeEye(vEye2, etPwd2);
                break;
            case R.id.rlEye3:
                OtherUtils.changeEye(vEye3, etPwd3);
                break;
            case R.id.tvSubmit:
                if (OtherUtils.stringIsNull(etPwd2, "请输入密码")) {
                    return;
                }
                if (OtherUtils.stringIsNull(etPwd3, "请输入确认密码")) {
                    return;
                }
                if (!OtherUtils.stringIsSame(etPwd2, etPwd3, "两次密码不一致")) {
                    return;
                }
                String pwdStr2 = etPwd2.getText().toString().trim();
                String pwdStr3 = etPwd3.getText().toString().trim();
                getPresenter().resetPwd(phone,code,pwdStr2, pwdStr3);
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onPostPhone(PhoneEvent event) {
        phone = event.getPhone();
        code = event.getCode();
    }

    @Override
    public SetPwdPresenter createPresenter() {
        return new SetPwdPresenter();
    }

    @Override
    public void change(String data) {
        ToastUtil.s("重置密码成功");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
