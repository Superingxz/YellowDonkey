package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.SPManager;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteFriendsActivity extends ToolbarActivity implements UMShareListener {


    @BindView(R.id.llWechat)
    LinearLayout llWechat;
    @BindView(R.id.llFriend)
    LinearLayout llFriend;
    @BindView(R.id.llWeibo)
    LinearLayout llWeibo;
    @BindView(R.id.llQq)
    LinearLayout llQq;
    @BindView(R.id.tvInviteCode)
    TextView tvInviteCode;

    public static void start(Context context) {
        Intent starter = new Intent(context, InviteFriendsActivity.class);
//        starter.putExtra("邀请好友", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("邀请好友")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_invite_friends;
    }

    @Override
    protected void initialize() {
        LoginBean loginBean = SPManager.getLoginBean();
        tvInviteCode.setText("我的邀请码：" + loginBean.getInvite_code());
    }

    private void openShare(int which) {
        //获取地图截图(Bitmap)
        Bitmap bitmap = null;
        SHARE_MEDIA share_media = null;

        switch (which) {
            case 1:
                share_media = SHARE_MEDIA.WEIXIN;
                break;
            case 2:
                share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
                break;
            case 3:
                share_media = SHARE_MEDIA.SINA;
                break;
            case 4:
                share_media = SHARE_MEDIA.QQ;
                break;
        }

        UMWeb web = new UMWeb("https://www.pgyer.com/4XK8");
        web.setTitle("小黄驴共享");//标题
        web.setThumb(new UMImage(getContext(), R.drawable.share_icon));  //缩略图
        web.setDescription("我在使用小黄驴共享\n赶紧来下载使用吧\n邀请码:"
                + SPManager.getLoginBean().getInvite_code());//描述

        new ShareAction(getActivity())
                .setPlatform(share_media)//传入平台
                .withMedia(web)
                .setCallback(this)//回调监听器
                .share();
    }

    /**
     * QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        LogUtils.i("UM_SHARE", "onStart");
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        LogUtils.i("UM_SHARE", "onResult = " + share_media.getName());
        ToastUtil.s("分享成功");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        LogUtils.i("UM_SHARE", "onError = " + throwable.getMessage());

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        LogUtils.i("UM_SHARE", "onCancel");

    }

    @OnClick({R.id.llWechat, R.id.llFriend, R.id.llWeibo, R.id.llQq})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llWechat:
                openShare(1);
                break;
            case R.id.llFriend:
                openShare(2);
                break;
            case R.id.llWeibo:
                openShare(3);
                break;
            case R.id.llQq:
                openShare(4);
                break;
        }
    }

}
