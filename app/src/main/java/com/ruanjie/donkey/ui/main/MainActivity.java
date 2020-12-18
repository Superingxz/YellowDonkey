package com.ruanjie.donkey.ui.main;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.location.Location;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mirkowu.statusbarutil.StatusBarUtil;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.FenceListBean;
import com.ruanjie.donkey.bean.IndexBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.MarkerDataBean;
import com.ruanjie.donkey.bean.NotifyMessageBean;
import com.ruanjie.donkey.bean.ParkingListBean;
import com.ruanjie.donkey.bean.UnReadBean;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.ruanjie.donkey.ui.billing.BillingModeActivity;
import com.ruanjie.donkey.ui.billing.PayArrearsActivity;
import com.ruanjie.donkey.ui.billing.UseTimingActivity;
import com.ruanjie.donkey.ui.drawer.EXRealNameApplyActivity;
import com.ruanjie.donkey.ui.drawer.ExChangeActivity;
import com.ruanjie.donkey.ui.drawer.InviteFriendsActivity;
import com.ruanjie.donkey.ui.drawer.JoinSelectActivity;
import com.ruanjie.donkey.ui.drawer.MainTainActivity;
import com.ruanjie.donkey.ui.drawer.MyCouponsActivity;
import com.ruanjie.donkey.ui.drawer.MyTravelActivity;
import com.ruanjie.donkey.ui.drawer.MyWalletActivity;
import com.ruanjie.donkey.ui.drawer.PayDepositActivity;
import com.ruanjie.donkey.ui.drawer.RechargeActivity;
import com.ruanjie.donkey.ui.drawer.SettingActivity;
import com.ruanjie.donkey.ui.drawer.UserInfoActivity;
import com.ruanjie.donkey.ui.help.HelpListActivity;
import com.ruanjie.donkey.ui.main.contract.MainContract;
import com.ruanjie.donkey.ui.main.presenter.MainPresenter;
import com.ruanjie.donkey.ui.message.MyMessageActivity;
import com.ruanjie.donkey.ui.scanner.ScanUnlockActivity;
import com.ruanjie.donkey.ui.shop.ShopActivity;
import com.ruanjie.donkey.ui.sign.LoginActivity;
import com.ruanjie.donkey.ui.upload.FaultUploadActivity;
import com.ruanjie.donkey.ui.upload.IllegalUploadActivity;
import com.ruanjie.donkey.ui.webView.WebViewActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.MRxPermissionsUtil;
import com.ruanjie.donkey.utils.SPManager;
import com.ruanjie.donkey.utils.TimeUtils;
import com.ruanjie.toolsdk.config.ToolSdk;
import com.softgarden.baselibrary.base.BaseActivity;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;
import com.softgarden.baselibrary.utils.BaseSPManager;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.EmptyUtil;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.RxPermissionsUtil;
import com.softgarden.baselibrary.utils.SPUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.baselibrary.widget.NoScrollViewPager;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.WalkingParam;
import com.tencent.lbssearch.object.result.WalkingResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.map.sdk.compat.SupportMapFragmentCompat;
import com.tencent.map.sdk.compat.TencentMapCompat;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.Polygon;
import com.tencent.tencentmap.mapsdk.maps.model.PolygonOptions;
import com.tencent.tencentmap.mapsdk.maps.model.Polyline;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;
import com.tencent.tencentmap.mapsdk.maps.model.ScaleAnimation;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxDeviceTool;
import com.vondear.rxtool.RxLocationTool;
import com.vondear.rxtool.RxPermissionsTool;
import com.vondear.rxtool.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.jessyan.autosize.AutoSizeConfig;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, OnTabSelectListener,
        TencentMap.OnMapLoadedCallback, TencentMap.OnCameraChangeListener, TencentMap.OnMapClickListener,
        TencentMap.OnMarkerClickListener {

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
    @BindView(R.id.stvShop)
    SuperTextView stvShop;
    @BindView(R.id.stvSetting)
    SuperTextView stvSetting;
    @BindView(R.id.rlTopLayout)
    RelativeLayout rlTopLayout;
    @BindView(R.id.mViewPager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.customer_service_layout)
    RelativeLayout customerServiceLayout;
    @BindView(R.id.tv_notify_message)
    TextBannerView mNotifyMessage;
    @BindView(R.id.tv_useing_vehicle_code)
    AppCompatTextView tvUseingVehicleCode;
    @BindView(R.id.bt_scan_unlock)
    AppCompatButton btScanUnlock;
    @BindView(R.id.image_toolbar)
    Toolbar imageToolbar;
    @BindView(R.id.iv_logo)
    AppCompatImageView ivLogo;
    @BindView(R.id.iv_yellow_donkey)
    AppCompatImageView ivYellowDonkey;
    @BindView(R.id.iv_message)
    AppCompatImageView ivMessage;
    @BindView(R.id.tv_vehicle_fault)
    AppCompatTextView tvVehicleFault;
    @BindView(R.id.tv_illegal_declaration)
    AppCompatTextView tvIllegalDeclaration;
    @BindView(R.id.tv_online_service)
    AppCompatTextView tvOnlineService;
    @BindView(R.id.tv_user_guide)
    AppCompatTextView tvUserGuide;
    @BindView(R.id.iv_location)
    AppCompatImageView ivLocation;
    @BindView(R.id.iv_customer_service)
    AppCompatImageView ivCustomerService;
    @BindView(R.id.tab_layout)
    SegmentTabLayout mTabLayout;

    private boolean click = true;
    private boolean status = false;
    private double latitude;
    private double longitude;
    private List<String> messages = new ArrayList<>();
    private List<String> messagesUrl = new ArrayList<>();
    private List<Integer> messagesId = new ArrayList<>();
    private List<Integer> couponsId = new ArrayList<>();
    private int isUnPay;
    private int isNeedDeposit;
    private int isNeedRecharge;
    private String orderPrice;
    private String useingVehicleCode;
    private TencentMapCompat tencentMap;
    private MapLocationSource locationSource;
    private BitmapDescriptor myLocationBitmap;
    private BitmapDescriptor locationTagBitmap;
    private BitmapDescriptor electricBikeLocationBitmap;
    private BitmapDescriptor electricBikeSelectBitmap;
    private LatLng mStartPoint = null;
    private LatLng mEndPoint = null;
    private Polyline mCurPolyline;
    private IndexBean.ListBean mCurTag;
    private SupportMapFragmentCompat mapFragment;
    private int time = 0;
    private TimeRunnable mTimeRunnable;
    private LoginBean mLoginBean = SPManager.getLoginBean();
    ;
    private int isUseingVehicle;
    private BitmapDescriptor parkingBitmap, parking2Bitmap, parking3Bitmap;
    private Polygon polygon;
    private int isFirstUseVehicle;
    //    private int couponId;
//    private int messageId;
    private String[] mTitles = {"找驴(车)", "还驴(车)"};
    private Marker parkingMarker;
    private Marker electricBikeMarker;
    private WalkingResultObject.Route route;
    private Marker mMarker;

    private List<IndexBean.ListBean> list;

    private String dir = "";
    private String tip = "";
    private boolean isFirstLocation = true;
    private int isInside;
    private ValueAnimator animator = null;
    private LatLng mCurrentPosition = null;
    private LatLng mMarkerPositon = null;
    private RequestRunnable mRequestRunnable;
    private String vehicleCode;
    private TencentSearch tencentSearch;
    private List<Marker> electricBikeMarkers = new ArrayList<>();
    private List<Marker> parkingMarkers = new ArrayList<>();
    private LatLng[] latLngs;
    private Location location;
    private Polygon mPolygon;
    private List<LatLng> polylines;
    private List<ParkingListBean.PointsBean> parkingPointsList;
    private ArrayList<Polygon> parkingPolygons = new ArrayList<>();
    private int enegy;
    private IndexBean indexBean;
    private Marker mMyLocationMarker;

    @OnClick({R.id.rlTopLayout, R.id.stvWallet, R.id.stvTravel
            , R.id.stvCoupon, R.id.stvJoin, R.id.stvMaintain
            , R.id.stvInvite, R.id.stvExchange, R.id.stvShop
            , R.id.stvSetting, R.id.iv_logo, R.id.iv_message,
            R.id.iv_location, R.id.bt_scan_unlock, R.id.iv_customer_service,
            R.id.tv_vehicle_fault, R.id.tv_illegal_declaration, R.id.tv_online_service,
            R.id.tv_user_guide, R.id.tv_useing_vehicle_code})
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
            case R.id.stvShop://驴币商城
                ShopActivity.start(getContext());
                break;
            case R.id.stvSetting:
                SettingActivity.start(getContext());
                break;
            case R.id.iv_logo:
                if (mLoginBean == null || mLoginBean.getId() <= 0) {
                    showLoginDialog();
                } else {
                    openDrawer();
                }
                break;
            case R.id.iv_message:
                if (mLoginBean == null || mLoginBean.getId() <= 0) {
                    showLoginDialog();
                } else {
                    MyMessageActivity.start(getContext());
                }
                break;
            case R.id.iv_location:
                if (MRxPermissionsUtil.isHasAll(getActivity(), RxPermissionsUtil.FINE_LOCATION_STORAGE)) {
                    mCurrentPosition = new LatLng(latitude, longitude);
                    if (mCurrentPosition != null) {
                        tencentMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentPosition, 16));
                        getPresenter().index(String.valueOf(mCurrentPosition.longitude), String.valueOf(mCurrentPosition.latitude), 800);
                    }
                } else {
                    initGpsAndPermissions();
                }

                break;
            case R.id.bt_scan_unlock:
                if (mLoginBean == null || mLoginBean.getId() <= 0) {
                    showLoginDialog();
                } else if (isFirstUseVehicle == 1) {
                    getPresenter().showPrice();
                } else if (isNeedDeposit == 1) {
                    PayDepositActivity.start(getContext(), mLoginBean.getDeposit());
                } else if (isUnPay == 1) {
                    PayArrearsActivity.start(getContext(), orderPrice);
                    finish();
                } else if (isNeedRecharge == 1) {
                    RechargeActivity.start(getContext());
                    finish();
                } else {
                    ScanUnlockActivity.start(getContext());
                }

                break;
            case R.id.iv_customer_service:
                if (mLoginBean == null || mLoginBean.getId() <= 0) {
                    showLoginDialog();
                } else if (click) {
                    customerServiceLayout.setVisibility(View.VISIBLE);
                    click = false;
                } else {
                    customerServiceLayout.setVisibility(View.GONE);
                    click = true;
                }
                break;
            case R.id.tv_vehicle_fault:
                FaultUploadActivity.start(getContext());
                break;
            case R.id.tv_illegal_declaration:
                IllegalUploadActivity.start(getContext());
                break;
            case R.id.tv_online_service:
                DiaLogUtils.showTipDialog(getContext(), "即将拨通客服电话"
                        , "020-12580580"
                        , "取消"
                        , "立即拨打"
                        , new PromptDialog.OnButtonClickListener() {
                            @Override
                            public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                if (isPositiveClick) {
                                    RxDeviceTool.callPhone(getActivity(), String.valueOf(020 - 12580580));
                                }
                            }
                        });
                break;
            case R.id.tv_user_guide:
                HelpListActivity.start(getContext());
                break;
            case R.id.tv_useing_vehicle_code:
                UseTimingActivity.start(getActivity(), useingVehicleCode);
                break;
            default:
                break;
        }
    }


    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    public static void start(Context context) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void start(Context context, boolean status, boolean isCloseActs) {
        // starter.putExtra(F);
        Intent starter = new Intent(context, MainActivity.class).putExtra("status", status);

        if (isCloseActs) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(starter);
    }


    public static void start(Context context, boolean isCloseActs) {
        Intent starter = new Intent(context, MainActivity.class);
//        mIsCloseActs = isCloseActs;
//        starter.putExtra();
        if (isCloseActs) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(starter);
    }

    private void initGpsAndPermissions() {
        if (RxPermissionsUtil.checkGPSEnable(getContext())) {
            RxPermissionsTool.with(getActivity())
                    .addPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .addPermission(Manifest.permission.READ_PHONE_STATE)
                    .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .initPermission();
//            RxPermissionsUtil.request(getActivity(),RxPermissionsUtil.FINE_LOCATION_STORAGE);
            if (RxLocationTool.isLocationEnabled(getContext())) {
                initLocation();
            }
        } else {
            DiaLogUtils.showTipDialog(getContext(), "检测到GPS/位置服务\n功能未开启，请开启~", null,
                    getString(R.string.base_cancel), getString(R.string.open), new PromptDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                            if (isPositiveClick) {
                                RxLocationTool.openGpsSettings(getContext());
                            } else {
                                RxPermissionsTool.with(getActivity())
                                        .addPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                                        .addPermission(Manifest.permission.READ_PHONE_STATE)
                                        .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                        .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        .initPermission();
//                               RxPermissionsUtil.request(getActivity(),RxPermissionsUtil.COARSE_LOCATION_STORAGE);
                                if (RxLocationTool.isLocationEnabled(getContext())) {
                                    initLocation();
                                }
                            }
                        }
                    });
        }
    }

    private void initDrawerViews() {
        StatusBarUtil.setTransparentForDrawerLayout(this, mDrawerLayout);
        StatusBarUtil.setStatusBarLightMode(getActivity());
        //禁止侧边栏滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

//        mLoginBean = SPManager.getLoginBean();
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
    public void initNotifyMessage(List<NotifyMessageBean> beans) {
        if (EmptyUtil.isNotEmpty(beans) || beans.size() != 0) {
            mNotifyMessage.setVisibility(View.VISIBLE);
            for (int i = 0; i < beans.size(); i++) {
                messages.add(beans.get(i).getTitle());
                messagesUrl.add(beans.get(i).getContent_url());
                messagesId.add(beans.get(i).getId());
                couponsId.add(beans.get(i).getCoupon_id());
//                messageId = beans.get(i).getId();
//                couponId = beans.get(i).getCoupon_id();
            }
            mNotifyMessage.setDatasWithDrawableIcon(messages, ContextCompat.getDrawable(getContext(), R.mipmap.gift_icon), 18, Gravity.LEFT);
            mNotifyMessage.setItemOnClickListener(new ITextBannerItemClickListener() {
                @Override
                public void onItemClick(String data, int position) {
                    WebViewActivity.start(getContext(), data, messagesUrl.get(position), messagesId.get(position), couponsId.get(position));
                }
            });
        }
    }

    @Override
    public void initIndex(IndexBean bean) {
        if (electricBikeMarkers != null && electricBikeMarkers.size() > 0) {
            for (Marker electricBikeMarker : electricBikeMarkers) {
                electricBikeMarker.remove();
            }
        }
        if (bean != null) {
            isFirstUseVehicle = bean.getIs_first();
            isUnPay = bean.getIs_unpay();
            isNeedDeposit = bean.getNeed_deposit();
            isNeedRecharge = bean.getNeed_recharge();
            orderPrice = String.valueOf(bean.getOrder_price());
            isUseingVehicle = bean.getIs_using();
            useingVehicleCode = bean.getUsing_car();
            time = bean.getUsing_car_duration();
            isInside = bean.getIs_inside();

            if (isInside == 0) {
                RxToast.showToast(getContext(), "该区域暂未开通服务，敬请期待", Toast.LENGTH_LONG);
            }
//            List<IndexBean.ListBean> list = bean.getList();
            list = bean.getList();
            if (EmptyUtil.isNotEmpty(list) && list.size() > 0) {
//                if (electricBikeMarker == null){
                for (IndexBean.ListBean listBean : list) {
                    MarkerDataBean markerDataBean = new MarkerDataBean();
                    markerDataBean.setIndexBean(bean);
                    markerDataBean.setListBean(listBean);

                    electricBikeMarker = tencentMap.addMarker(
                            new MarkerOptions(new LatLng(Double.parseDouble(listBean.getLat()), Double.parseDouble(listBean.getLng())))
                                    .icon(electricBikeLocationBitmap)
                                    .fastLoad(true)
                                    .tag(markerDataBean));
                    electricBikeMarkers.add(electricBikeMarker);
                    enegy = listBean.getEnegy();
                    vehicleCode = listBean.getCode();
                }
//            }
            }
        }


        if (status || isUseingVehicle == 1) {
            if (isUseingVehicle == 0 && EmptyUtil.isNotEmpty(messagesId) && messagesId.size() > 0) {
                tvUseingVehicleCode.setVisibility(View.GONE);
                mNotifyMessage.setVisibility(View.VISIBLE);
                btScanUnlock.setText("扫码解锁");
                if (mTimeRunnable != null) {
                    ToolSdk.getHandler().removeCallbacks(mTimeRunnable);
                    mTimeRunnable = null;
                }
            } else {
                tvUseingVehicleCode.setVisibility(View.VISIBLE);
                mNotifyMessage.setVisibility(View.GONE);
                tvUseingVehicleCode.setText("正在使用编号" + useingVehicleCode + "的电动车");
                if (mTimeRunnable == null) {
                    mTimeRunnable = new TimeRunnable();
                    ToolSdk.getHandler().postDelayed(mTimeRunnable, 0);
                }
                btScanUnlock.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_radius_yellow));
                btScanUnlock.setOnClickListener(v ->
                        UseTimingActivity.start(getContext(), useingVehicleCode));
//            getPresenter().vehicleDetail(useingVehicleCode);
            }
        } else {
            if (EmptyUtil.isNotEmpty(messagesId) && messagesId.size() > 0) {
                tvUseingVehicleCode.setVisibility(View.GONE);
                mNotifyMessage.setVisibility(View.VISIBLE);
                btScanUnlock.setText("扫码解锁");
//            getPresenter().vehicleDetail(vehicleCode);
            }
        }

    }

    @Override
    public void initParkingList(List<ParkingListBean> beans) {
        //这里再加一个type=3时候的停车图标
        parkingBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.parking_icon);
        parking2Bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.type2);
        parking3Bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.type3);
        if (EmptyUtil.isNotEmpty(parkingMarkers) && parkingMarkers.size() > 0) {
            for (Marker parkingMarker : parkingMarkers) {
                parkingMarker.remove();
            }
        }
        if (EmptyUtil.isNotEmpty(parkingPolygons) && parkingPolygons.size() > 0) {
            for (Polygon parkingPolygon : parkingPolygons) {
                parkingPolygon.remove();
            }
        }

        if (EmptyUtil.isNotEmpty(beans) && beans.size() > 0) {
//            if (parkingMarker == null){
            for (ParkingListBean bean : beans) {
                parkingPointsList = bean.getPoints();
                if (EmptyUtil.isEmpty(parkingPointsList)) {
                    continue;
                }
                parkingMarker = tencentMap.addMarker(new MarkerOptions(
                        new LatLng(Double.parseDouble(bean.getLat()),
                                Double.parseDouble(bean.getLng())))
                        .icon(bean.getType() == 2 ? parking2Bitmap
                                : bean.getType() == 3 ? parking3Bitmap
                                : parkingBitmap)
                        .fastLoad(true));
                parkingMarkers.add(parkingMarker);

                addPolygon(parkingPointsList, bean.getType());
                    /*for (ParkingListBean.PointsBean pointsBean : pointsList) {

                    }*/
            }
//            }
        }
    }

    protected void addPolygon(List<ParkingListBean.PointsBean> pointsBeans, int type) {

        for (int i = 0; i < pointsBeans.size(); i++) {
            LatLng latLngs[] = new LatLng[pointsBeans.size()];

            for (int j = 0; j < pointsBeans.size(); j++) {
                ParkingListBean.PointsBean pointsBean = pointsBeans.get(j);
                try {
                    latLngs[j] = new LatLng(Double.valueOf(pointsBean.getLat()), Double.valueOf(pointsBean.getLng()));
                } catch (Exception e) {
                    LogUtils.d("error", e.getMessage());
                }
            }
            //根据type=3变化停车点颜色
            polygon = tencentMap.addPolygon(new PolygonOptions().
                    add(latLngs).
                    fillColor(ContextCompat.getColor(getContext(), type == 2 ? R.color.color_inner_2
                            : type == 3 ? R.color.color_inner_3
                            : R.color.color_inner_1)).
                    strokeColor(ContextCompat.getColor(getContext(),
                            type == 2 ? R.color.color_type_2
                                    : type == 3 ? R.color.color_type_3
                                    : R.color.color_type_1))
                    .strokeWidth(5).clickable(false));

            parkingPolygons.add(polygon);
        }
    }

    @Override
    public void initFenceList(List<FenceListBean> beans) {
        if (EmptyUtil.isNotEmpty(beans) && beans.size() > 0) {
            for (FenceListBean bean : beans) {
                List<FenceListBean.PointsBean> pointsList = bean.getPoints();

                if (EmptyUtil.isEmpty(pointsList)) {
                    continue;
                }

                for (int i = 0; i < pointsList.size(); i++) {
                    LatLng latLngs[] = new LatLng[pointsList.size()];

                    for (int j = 0; j < pointsList.size(); j++) {
                        FenceListBean.PointsBean pointsBean = pointsList.get(j);
                        try {
                            latLngs[j] = new LatLng(Double.parseDouble(pointsBean.getLat()), Double.parseDouble(pointsBean.getLng()));
                        } catch (Exception e) {
                            LogUtils.d("error", e.getMessage());
                        }
                    }
                    mPolygon = tencentMap.addPolygon(new PolygonOptions().
                            add(latLngs).
                            fillColor(ContextUtil.getColor(R.color.inner_blue)).
                            strokeColor(ContextUtil.getColor(R.color.outer_blue)).
                            strokeWidth(5).clickable(false));
                }
            }
        }
    }


    @Override
    public void isShowPrice() {
        BillingModeActivity.start(getContext());
//        finish();
    }

    @Override
    public void vehicleDetail(VehicleDetailBean bean) {
        if (bean.getUser_id() != mLoginBean.getId()) {
            tvUseingVehicleCode.setVisibility(View.GONE);
            mNotifyMessage.setVisibility(View.VISIBLE);
            btScanUnlock.setText("扫码解锁");
        }
    }

    @Override
    public void getUnReadCount(UnReadBean data) {
        int num = data.getNum();
        if (num <= 0) {//隐藏红点
            mMsgBadge.setBadgeText("");
        } else if (num > 99) {//显示99+
            mMsgBadge.setBadgeText("99+");
        } else {
            mMsgBadge.setBadgeNumber(num);
        }
    }

    @Override
    public void initBitmap() {
        myLocationBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.yellow_dot_icon);
        locationTagBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location_tag_icon);
        electricBikeLocationBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.electric_bike_location_icon);
        electricBikeSelectBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.electric_bike_location_select_icon);
    }

    @Override
    public void initMap() {
        FragmentManager fm = getSupportFragmentManager();
        mapFragment = (SupportMapFragmentCompat) fm.findFragmentById(R.id.fragment_map);
        if (mapFragment != null) {
            if (tencentMap == null) {
                tencentMap = mapFragment.getMap();
                tencentSearch = new TencentSearch(getContext());
//                tencentMap.getUiSettings().setZoomControlsEnabled(false);
                tencentMap.getUiSettings().setGestureScaleByMapCenter(true);
                tencentMap.setOnMapLoadedCallback(this);
                tencentMap.setOnMarkerClickListener(this);
                tencentMap.setOnMapClickListener(this);
                tencentMap.setOnCameraChangeListener(this);
//                tencentMap.setOnMarkerDragListener(this);
            }
        }
    }

    @Override
    public void initLocation() {
        locationSource = new MapLocationSource(getContext());
//        tencentMap.setLocationSource(locationSource);
        tencentMap.setMyLocationEnabled(true);
    }

    @Override
    public void initMyLocationMarker() {
        mMyLocationMarker = tencentMap.addMarker(new MarkerOptions(new LatLng(latitude, longitude))
                .icon(myLocationBitmap).fastLoad(true));
        mMyLocationMarker.setClickable(false);
    }

    /*@Override
    public void initLocationStyle() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.anchor(0.5f, 0.5f);
        myLocationStyle.icon(myLocationBitmap);
        myLocationStyle.fillColor(0);
        myLocationStyle.strokeColor(0);
        myLocationStyle.strokeWidth(0);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        tencentMap.setMyLocationStyle(myLocationStyle);
    }*/

    @Override
    public void initMarker() {
        if (mMarker == null) {
            mMarker = tencentMap.addMarker(new MarkerOptions(new LatLng(latitude, longitude))
                    .icon(locationTagBitmap).fastLoad(true));
            mMarker.setFixingPoint(tencentMap.getMapWidth() / 2, tencentMap.getMapHeight() / 2 - 200);
            animMarker();
        }
       /* else {
            mMarker.setFixingPoint(tencentMap.getMapWidth() / 2,tencentMap.getMapHeight() / 2 + 100);
            mMarker.setFastLoad(true);
            animMarker();
        }*/
        mMarker.setClickable(false);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        L.i("onCreate");
        StatusBarUtil.setStatusBarPadding(getContext(), imageToolbar);
//        isLogin();
        initDrawerViews();
        status = getIntent().getBooleanExtra("status", false);
        getPresenter().notifyMessage();
        mMsgBadge = new QBadgeView(getContext())
                .bindTarget(ivMessage)
                .setBadgeTextSize(10, true)
                .setBadgePadding(1, true)
                .setBadgePadding(1, true);

        mTabLayout.setTabData(mTitles);
        mTabLayout.setCurrentTab(0);
        initBitmap();
        initMap();
        initGpsAndPermissions();
        mTabLayout.setOnTabSelectListener(this);
        //clustering
/*        mClusterManager = new ClusterManager<TencentMapItem>(this, tencentMap);

//设置聚合渲染器, 默认 cluster manager 使用的就是 DefaultClusterRenderer 可以不调用下列代码
        renderer = new DefaultClusterRenderer<>(this, tencentMap, mClusterManager);
//如果需要修改聚合点生效阈值，需要调用这个方法，这里指定聚合中的点大于1个时才开始聚合，否则显示单个 marekr
        renderer.setMinClusterSize(1);
        mClusterManager.setRenderer(renderer);
        //添加聚合
        tencentMap.setOnCameraChangeListener(mClusterManager);*/
    }


    Badge mMsgBadge;

    private void isLogin() {
        if (mLoginBean == null || TextUtils.isEmpty(String.valueOf(mLoginBean.getId()))) {
            DiaLogUtils.showTipDialog(getContext(), "你还没登录呢！"
                    , "注册登录后才可扫码用车哦~"
                    , "取消"
                    , "去登录"
                    , new PromptDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                            if (isPositiveClick) {
                                LoginActivity.start(getContext());
                                finish();
                            }
                        }
                    });
        } else {
            Log.d("lh", String.valueOf(mLoginBean.getUser_id() + " " + String.valueOf(mLoginBean.getToken())));
        }
    }

    private void showLoginDialog() {
        DiaLogUtils.showTipDialog(getContext(), "你还没登录呢！"
                , "注册登录后才可扫码用车哦~"
                , "取消"
                , "去登录"
                , new PromptDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                        if (isPositiveClick) {
                            RxActivityTool.skipActivityAndFinishAll(getContext(), LoginActivity.class);
                        }
                    }
                });
    }

    public void setStatusBarLightMode() {
        if (!BaseSPManager.isNightMode()) {
            StatusBarUtil.setStatusBarLightModeWithNoSupport(getActivity(), true);
        }
    }

    @Override
    public void onTabSelect(int position) {
        switch (position) {
            case 0:
                if (EmptyUtil.isNotEmpty(parkingMarkers) && parkingMarkers.size() > 0) {
                    for (Marker parkingMarker : parkingMarkers) {
                        parkingMarker.remove();
                    }
                }
                if (EmptyUtil.isNotEmpty(parkingPolygons) && parkingPolygons.size() > 0) {
                    for (Polygon parkingPolygon : parkingPolygons) {
                        parkingPolygon.remove();
                    }
                }
                if (mCurrentPosition != null) {
                    getPresenter().index(String.valueOf(mCurrentPosition.longitude), String.valueOf(mCurrentPosition.latitude), 800);
                }

                break;
            case 1:
                if (EmptyUtil.isNotEmpty(electricBikeMarkers) && electricBikeMarkers.size() > 0) {
                    for (Marker electricBikeMarker : electricBikeMarkers) {
                        electricBikeMarker.remove();
                    }
                }
                if (mCurrentPosition != null) {
                    getPresenter().parikingList(mCurrentPosition.longitude, mCurrentPosition.latitude, 800);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onMapLoaded() {
//        initMarker();
        if (mRequestRunnable == null) {
            mRequestRunnable = new RequestRunnable();
            ToolSdk.getHandler().postDelayed(mRequestRunnable, 0);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        electricBikeMarker = marker;
        mMarkerPositon = marker.getPosition();

       /* if (latLngs == null) {
            latLngs = getCoords();
            if (latLngs[0] == null) {
                RxToast.showToast("起点坐标不合规则");
                return false;
            }
            if (latLngs[1] == null) {
                RxToast.showToast("终点坐标不合规则");
                return false;
            }
        }*/
        if (mCurPolyline != null) {
            mCurPolyline.remove();
            mCurPolyline = null;
        }
        if (electricBikeMarker != null) {
            electricBikeMarker.setIcon(electricBikeLocationBitmap);
            electricBikeMarker = null;
        }

        if (mCurTag != null) {
//            IndexBean.ListBean tag = (IndexBean.ListBean) marker.getTag();
            MarkerDataBean markerTag = (MarkerDataBean) marker.getTag();
            if (markerTag != null) {
                IndexBean.ListBean listBean = markerTag.getListBean();
                if (listBean != null && listBean.getId() == mCurTag.getId()) {
                    mCurTag = null;
                    mMarkerPositon = null;
                    return true;
                }
            }
        }
        startAnim(marker);
        electricBikeMarker = marker;
        marker.setIcon(electricBikeSelectBitmap);
        if (mCurrentPosition != null && mMarkerPositon != null) {
            getWalkPlan(marker, mCurrentPosition, mMarkerPositon);
        }
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    electricBikeMarker = marker;
//                    mStartPoint = new LatLng(mCurrentPosition.latitude, mCurrentPosition.longitude);
//                    mMarker.setPosition(mStartPoint);
//                    mEndPoint =new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
                    marker.setIcon(electricBikeSelectBitmap);
//                    marker.setPosition(marker.getPosition());
                    if (mCurrentPosition != null && mMarkerPositon != null) {
                        getWalkPlan(marker,mCurrentPosition, mMarkerPositon);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();*/

        MarkerDataBean tag = (MarkerDataBean) marker.getTag();

        indexBean = tag.getIndexBean();

        mCurTag = tag.getListBean();

        return true;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        mCurrentPosition = cameraPosition.target;
//        getPresenter().index(String.valueOf(mCurrentPosition.longitude), String.valueOf(mCurrentPosition.latitude), 10000);
    }

    @Override
    public void onCameraChangeFinished(CameraPosition cameraPosition) {
        mCurrentPosition = cameraPosition.target;
//        mStartPosition = cameraPosition.target;
//        LatLng target = cameraPosition.target;
        if (mCurPolyline != null) {
            mCurPolyline.remove();
            mCurPolyline = null;
        }

        if (mMarker != null) {
            animMarker();
        }

        /*if (mCurrentPosition != null && mMarkerPositon != null) {
            getWalkPlan(mCurrentPosition, mMarkerPositon);
        }*/
        if (mTabLayout.getCurrentTab() == 0) {
            if (mCurrentPosition != null) {
                getPresenter().index(String.valueOf(mCurrentPosition.longitude), String.valueOf(mCurrentPosition.latitude), 800);
            }
        } else if (mTabLayout.getCurrentTab() == 1) {
            if (mCurrentPosition != null) {
                getPresenter().parikingList(mCurrentPosition.longitude, mCurrentPosition.latitude, 800);
            }
        }


    }

    @Override
    public void onMapClick(LatLng latlng) {

        if (electricBikeMarker != null) {
            electricBikeMarker.setIcon(electricBikeLocationBitmap);
            electricBikeMarker.hideInfoWindow();
            electricBikeMarker = null;
        }

        if (mCurPolyline != null) {
            mCurPolyline.remove();
            mCurPolyline = null;
        }
       /* if(mCurrentPosition != null) {
            CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
                    mCurrentPosition, 17f);
            tencentMap.animateCamera(cameraUpate);
        }*/
    }

    /*protected LatLng[] getCoords() {
        LatLng start = new LatLng(mCurrentPosition.latitude,mCurrentPosition.longitude);
        LatLng destination = new LatLng(mMarkerPositon.latitude,mMarkerPositon.longitude);
        LatLng[] latLngs = {start, destination};
        return latLngs;
    }*/

    private class TimeRunnable implements Runnable {
        @Override
        public void run() {
            btScanUnlock.setText("正在用车中\n" + TimeUtils.formattedTime(time++));
            ToolSdk.getHandler().postDelayed(this, 1000);
        }
    }

    private class RequestRunnable implements Runnable {
        @Override
        public void run() {
            if (mCurrentPosition != null) {
                getPresenter().index(String.valueOf(mCurrentPosition.longitude), String.valueOf(mCurrentPosition.latitude), 800);
            } else {
                getPresenter().index(String.valueOf(longitude), String.valueOf(latitude), 800);
            }

            ToolSdk.getHandler().postDelayed(this, 30000);
        }
    }

    private void startAnim(Marker marker) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f);
        anim.setDuration(300);
        marker.setAnimation(anim);
        marker.startAnimation();
    }

    private void animMarker() {
        if (animator != null) {
            animator.start();
            return;
        }
        animator = ValueAnimator.ofFloat(tencentMap.getMapHeight() / 2, tencentMap.getMapHeight() / 2 - 30);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(150);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                mMarker.setFixingPoint(tencentMap.getMapWidth() / 2, Math.round(value));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mMarker.setIcon(locationTagBitmap);
            }
        });
        animator.start();
    }

    private void endAnim() {
        if (animator != null && animator.isRunning())
            animator.end();
    }

    /**
     * 步行规划，只能设置起点和终点
     */
    protected void getWalkPlan(Marker marker, LatLng start, LatLng end) {
        WalkingParam walkingParam = new WalkingParam();
        walkingParam.from(start);
        walkingParam.to(end);
        tencentSearch.getRoutePlan(walkingParam, new HttpResponseListener<WalkingResultObject>() {

            @Override
            public void onSuccess(int statusCode, WalkingResultObject object) {
//                route = object.result.routes.get(0);

                if (object == null) {
                    return;
                }
                List<WalkingResultObject.Route> routes = object.result.routes;
                for (WalkingResultObject.Route route : routes) {
                    dir = getDistance(route.distance);
                    tip = getDuration(route.duration);
                    polylines = route.polyline;
                    mCurPolyline = drawSolidLine(polylines);
                }


                if (indexBean != null) {
                    List<IndexBean.ListBean> list = indexBean.getList();
                    MarkerDataBean tag = (MarkerDataBean) marker.getTag();

                    if (tag != null) {
                        IndexBean.ListBean mListBean = tag.getListBean();

                        marker.setTitle("电量：" + mListBean.getEnegy() + "%"
                                + "距离：" + dir
                                + "时间：" + tip);
                        marker.setSnippet("编号：" + mListBean.getCode());
                        marker.showInfoWindow();
                    }
                }

                    /*for (int i = 0; i < list.size(); i++) {
                         if (marker != null){
                            marker.setTitle("电量：" + list.get(i).getEnegy() + "%"
                                    + "距离：" + dir
                                    + "时间：" + tip);
                            marker.setSnippet("编号：" + list.get(i).getCode());
                            marker.showInfoWindow();
                        }
                    }*/

                L.e("searchdemo", "plan success");


            }

            @Override
            public void onFailure(int statusCode, String responseString, Throwable throwable) {

            }
        });
    }


    /**
     * 将路线以实线画到地图上
     *
     * @param latLngs
     */
    protected Polyline drawSolidLine(List<LatLng> latLngs) {
        return tencentMap.addPolyline(new PolylineOptions().
                addAll(latLngs).
                width(20).lineCap(true).
                color(0xff24CC80));
    }

    /**
     * 将距离转换成米或千米
     *
     * @param distance
     * @return
     */
    protected String getDistance(float distance) {
        if (distance < 1000) {
            return Integer.toString((int) distance) + "米";
        } else {
            return Float.toString((float) ((int) (distance / 10)) / 100) + "千米";
        }
    }

    /**
     * 将时间转换成小时+分钟
     *
     * @param duration
     * @return
     */
    protected String getDuration(float duration) {
        if (duration < 60) {
            return Integer.toString((int) duration) + "分";
        } else {
            return Integer.toString((int) (duration / 60)) + "小时"
                    + Integer.toString((int) (duration % 60)) + "分";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.i("onStart");
        mapFragment.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i("onResume");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mapFragment.onResume();
        locationSource.onResume();
//        initLocationStyle();
        getPresenter().fenceList();
        /*if (status) {
            if (mRunnable == null) {
                mRunnable = new TimeRunnable();
                ToolSdk.getHandler().postDelayed(mRunnable, 0);
            }
        }*/
        //获取未读消息数量
        getPresenter().getUnReadCount();
//        getPresenter().index(String.valueOf(longitude),String.valueOf(latitude),10000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.i("onPause");
        mapFragment.onPause();
        locationSource.onPause();
        if (mTimeRunnable != null) {
            ToolSdk.getHandler().removeCallbacks(mTimeRunnable);
            mTimeRunnable = null;
        }
        if (mRequestRunnable != null) {
            ToolSdk.getHandler().removeCallbacks(mRequestRunnable);
            mRequestRunnable = null;
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        L.i("onStop");
        mapFragment.onStop();

    }

    @Override
    protected void onRestart() {
        L.i("onRestart");
        super.onRestart();

    }

    @Override
    protected void onDestroy() {

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (tencentMap.isMyLocationEnabled()) {
            tencentMap.setMyLocationEnabled(false);
        }
        if (!tencentMap.isDestroyed()) {
            tencentMap.clearAllOverlays();
            mapFragment.onDestroyView();
        }
        super.onDestroy();
        L.i("onDestory");
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

    public void openDrawer() {
        if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
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


    public class MapLocationSource implements TencentLocationListener {

        private Context mContext;
        private TencentLocationManager locationManager;
        private TencentLocationRequest locationRequest;
//        private OnLocationChangedListener mChangedListener;

        public MapLocationSource(Context context) {
            mContext = context;
            locationManager = TencentLocationManager.getInstance(mContext);
            locationRequest = TencentLocationRequest.create();
            locationRequest.setInterval(3000);
//            locationManager.requestLocationUpdates(locationRequest, this);
        }

        @Override
        public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
            if (error == TencentLocation.ERROR_OK /*&& mChangedListener != null*/) {
                L.e("maplocation", "location: " + tencentLocation.getCity()
                        + " " + tencentLocation.getProvider() + " " + tencentLocation.getBearing());
                latitude = tencentLocation.getLatitude();
                longitude = tencentLocation.getLongitude();


                LogUtils.i("地图定位", "latitude = " + latitude
                        + "\nlongitude = " + longitude);

                SPUtil.put("latitude",latitude+","+longitude);

                if (mMyLocationMarker != null) {
                    mMyLocationMarker.remove();
                }
                initMyLocationMarker();

                if (isFirstLocation) {
                    isFirstLocation = false;
                    location = new Location(tencentLocation.getProvider());
                    location.setLatitude(tencentLocation.getLatitude());
                    location.setLongitude(tencentLocation.getLongitude());
                    location.setAccuracy(tencentLocation.getAccuracy());
                    // 定位 sdk 只有 gps 返回的值才有可能获取到偏向角
                    location.setBearing(tencentLocation.getBearing());
                    tencentMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                    getPresenter().fenceList();
                    initMarker();
                    if (location.getLongitude() != 0 && location.getLatitude() != 0) {
                        getPresenter().index(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()), 800);
                    }
//                    mChangedListener.onLocationChanged(location);
                }

//                L.i("MapFragmentTag:" + longitude + "+" + latitude);
            }
        }

        @Override
        public void onStatusUpdate(String name, int status, String desc) {

        }

        /*@Override
        public void activate(OnLocationChangedListener onLocationChangedListener) {
            mChangedListener = onLocationChangedListener;
            int error = locationManager.requestLocationUpdates(locationRequest, this);
            switch (error) {
                case 1:
                    L.i("设备缺少使用腾讯定位服务需要的基本条件");
                    break;
                case 2:
                    L.i("manifest 中配置的 key 不正确");
                    break;
                case 3:
                    L.i("自动加载libtencentloc.so失败");
                    break;

                default:
                    break;
            }
        }*/


        public void onResume() {
            locationManager.requestLocationUpdates(locationRequest, this);
        }

        public void onPause() {
            locationManager.removeUpdates(this);
        }

        public void onDestory() {
            locationManager.removeUpdates(this);
        }

       /* @Override
        public void deactivate() {
            locationManager.removeUpdates(this);
            mContext = null;
            locationManager = null;
            locationRequest = null;
            mChangedListener = null;
        }*/

    }

    /*   @Override
    public void showError(Throwable t) {
        super.showError(t);
        if(t.getMessage().equals("请先注册或重新登录") && mLoginBean == null){
            DiaLogUtils.showTipDialog(getContext(), "你还没登录呢！"
                    , "注册登录后才可扫码用车哦~"
                    , "取消"
                    , "去登录"
                    , new PromptDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                            if (isPositiveClick) {
                                LoginActivity.start(getContext());
                                finish();
                            }
                        }
                    });
        }
    }
*/

}
