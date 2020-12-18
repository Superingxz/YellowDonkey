package com.ruanjie.donkey.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.allen.library.SuperTextView;
import com.mirkowu.statusbarutil.StatusBarUtil;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.listener.OnFragmentOpenDrawerListener;
import com.ruanjie.donkey.ui.drawer.EXRealNameApplyActivity;
import com.ruanjie.donkey.ui.drawer.ExChangeActivity;
import com.ruanjie.donkey.ui.drawer.InviteFriendsActivity;
import com.ruanjie.donkey.ui.drawer.JoinSelectActivity;
import com.ruanjie.donkey.ui.drawer.MainTainActivity;
import com.ruanjie.donkey.ui.drawer.MyCouponsActivity;
import com.ruanjie.donkey.ui.drawer.MyTravelActivity;
import com.ruanjie.donkey.ui.drawer.MyWalletActivity;
import com.ruanjie.donkey.ui.drawer.SettingActivity;
import com.ruanjie.donkey.ui.drawer.UserInfoActivity;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.SPManager;
import com.ruanjie.toolsdk.config.ToolSdk;
import com.ruanjie.toolsdk.ui.activities.ProxyActivity;
import com.ruanjie.toolsdk.ui.fragments.RootFragment;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;
import com.softgarden.baselibrary.utils.BaseSPManager;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.baselibrary.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.jessyan.autosize.AutoSizeConfig;
@Deprecated
public class MainMapActivity extends ProxyActivity
        implements OnFragmentOpenDrawerListener {

    @BindView(R.id.civHead)
    CircleImageView civHead;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.tvIsAuth)
    AppCompatTextView tvIsAuth;
    @BindView(R.id.stvWallet)
    SuperTextView stvWallet;
    @BindView(R.id.stvTravel)
    SuperTextView stvTravel;
    @BindView(R.id.stvCoupon)
    SuperTextView stvCoupon;
    @BindView(R.id.stvJoin)
    SuperTextView stvJoin;
    @BindView(R.id.stvMaintain)
    SuperTextView stvMaintain;
    @BindView(R.id.stvInvite)
    SuperTextView stvInvite;
    @BindView(R.id.stvExchange)
    SuperTextView stvExchange;
    @BindView(R.id.stvSetting)
    SuperTextView stvSetting;
    @BindView(R.id.rlTopLayout)
    RelativeLayout rlTopLayout;
    @BindView(R.id.mViewPager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;
    private String status;


    @OnClick({R.id.rlTopLayout, R.id.stvWallet, R.id.stvTravel
            , R.id.stvCoupon, R.id.stvJoin, R.id.stvMaintain
            , R.id.stvInvite, R.id.stvExchange, R.id.stvSetting})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlTopLayout:
                //个人信息编辑
                UserInfoActivity.start(getContext());
                break;
            case R.id.stvWallet:
                MyWalletActivity.start(getContext());
                break;
            case R.id.stvTravel:
                MyTravelActivity.start(getContext());
                break;
            case R.id.stvCoupon:
                MyCouponsActivity.start(getContext());
                break;
            case R.id.stvJoin:
                JoinSelectActivity.start(getContext());
                break;
            case R.id.stvMaintain:
                MainTainActivity.start(getContext());
                break;
            case R.id.stvInvite:
                InviteFriendsActivity.start(getContext());
                break;
            case R.id.stvExchange:
                //获取用户信息，判断是否通过外协换电实名认证
                RetrofitClient.getService()
                        .getUserInfo()
                        .compose(new NetworkTransformer<>(this))
                        .subscribe(new RxCallback<LoginBean>() {
                            @Override
                            public void onSuccess(LoginBean data) {
                                SPManager.setLoginBean(data);
                                //0=未认证,1=已审核,2=不通过
                                int assist_status = data.getAssist_status();
                                if (assist_status != 1) {
                                    EXRealNameApplyActivity.start(getContext(), assist_status);
                                } else {
                                    ExChangeActivity.start(getContext());
                                }
                            }
                        });
                break;
            case R.id.stvSetting:
                SettingActivity.start(getContext());
                break;
        }
    }


    public static void start(Context context) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, MainMapActivity.class));
    }
    public static void start(Context context,String status) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, MainMapActivity.class)
                .putExtra("status",status));
    }


    public static void start(Context context, boolean isCloseActs) {
        Intent starter = new Intent(context, MainMapActivity.class);
//        mIsCloseActs = isCloseActs;
//        starter.putExtra();
        if (isCloseActs) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(starter);
    }

    private void initDrawerViews() {
        //禁止侧边栏滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        LoginBean mLoginBean = SPManager.getLoginBean();
        if (mLoginBean != null) {
            ImageUtil.loadImage(civHead, mLoginBean.getAvatar(), R.mipmap.userinfo_head);
            tvName.setText(mLoginBean.getNickname());
//            tvIsAuth.setVisibility(mLoginBean.getIs_realname() == 1 ? View.VISIBLE : View.INVISIBLE);

            tvIsAuth.setText(mLoginBean.getIs_realname() == 1 ? getString(R.string.userinfo_has_real_name)
                    : mLoginBean.getIs_realname() == 2 ? getString(R.string.userinfo_no_real_name)
                    : mLoginBean.getIs_realname() == 3 ? getString(R.string.userinfo_real_name_ing)
                    : getString(R.string.userinfo_no_apply)
            );

            if (mLoginBean.getIs_realname() == 1) {
                tvIsAuth.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.drawer_auth, 0, 0, 0);
            } else {
                tvIsAuth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            if (mLoginBean.getIs_maintenance() == 1) {
                stvMaintain.setVisibility(View.VISIBLE);
            } else {
                stvMaintain.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void initialize() {
        StatusBarUtil.setTransparentForDrawerLayout(this, mDrawerLayout);
        setStatusBarLightMode();
        requesPemission();
        initDrawerViews();
        status = getIntent().getStringExtra("status");
    }

    public void setStatusBarLightMode() {
        if (!BaseSPManager.isNightMode()) {
            StatusBarUtil.setStatusBarLightModeWithNoSupport(getActivity(), true);
        }
    }

    @Override
    protected Object getLayoutId() {
        ToolSdk.getConfigurator().withActivity(this);
        return R.layout.activity_main_map;
    }

    @Override
    public int setContainerId() {
        return R.id.frame_container;
    }

    @Override
    public RootFragment rootFragment() {
        return MainMapFragment.newInstance();
    }

    private void requesPemission() {
        List<String> permissionlist = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!permissionlist.isEmpty()) {
            String[] perssions = permissionlist.toArray(new String[permissionlist.size()]);
            ActivityCompat.requestPermissions(MainMapActivity.this, perssions, 1);
        } else {
            //   requestionLotion();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * 检查是否为竖屏
     */
    public void checkScreenOrientation() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            AutoSizeConfig.getInstance().setDesignWidthInDp(360).setDesignHeightInDp(640);
        } else {
            AutoSizeConfig.getInstance().setDesignWidthInDp(640).setDesignHeightInDp(360);

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        L.d("onConfigurationChanged调用了");
        checkScreenOrientation();
    }

    @Override
    public void openDrawer() {
        if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void mEventBus(EventBusBean busBean) {
        switch (busBean.getCode()) {
            case MEventBus.REFRESH_USERINFO:
                initDrawerViews();
                break;
        }
    }

    /**
     * 再按一次退出程序
     */
    private long currentBackPressedTime = 0;
    private static int BACK_PRESSED_INTERVAL = 5000;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUtil.s("再按一次，退出应用！");
                return true;
            } else {
                finish(); // 退出
            }
            return false;

        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


}
