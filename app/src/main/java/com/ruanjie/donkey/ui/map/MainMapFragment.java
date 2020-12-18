package com.ruanjie.donkey.ui.map;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.FenceListBean;
import com.ruanjie.donkey.bean.IndexBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.NotifyMessageBean;
import com.ruanjie.donkey.bean.ParkingListBean;
import com.ruanjie.donkey.bean.UnReadBean;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.ruanjie.donkey.event.PositionEvent;
import com.ruanjie.donkey.listener.OnFragmentOpenDrawerListener;
import com.ruanjie.donkey.ui.billing.BillingModeActivity;
import com.ruanjie.donkey.ui.billing.PayArrearsActivity;
import com.ruanjie.donkey.ui.drawer.PayDepositActivity;
import com.ruanjie.donkey.ui.drawer.RechargeActivity;
import com.ruanjie.donkey.ui.help.HelpListActivity;
import com.ruanjie.donkey.ui.main.contract.MainContract;
import com.ruanjie.donkey.ui.main.presenter.MainPresenter;
import com.ruanjie.donkey.ui.message.MyMessageActivity;
import com.ruanjie.donkey.ui.scanner.ScanUnlockActivity;
import com.ruanjie.donkey.ui.sign.LoginActivity;
import com.ruanjie.donkey.ui.upload.FaultUploadActivity;
import com.ruanjie.donkey.ui.upload.IllegalUploadActivity;
import com.ruanjie.donkey.ui.webView.WebViewActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.SPManager;
import com.ruanjie.toolsdk.ui.fragments.RootFragment;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.RxPermissionsUtil;
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
import com.tencent.map.sdk.compat.MapViewCompat;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.Polyline;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;
import com.tencent.tencentmap.mapsdk.maps.model.ScaleAnimation;
import com.vondear.rxtool.RxDeviceTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author by DELL
 * @date on 2018/2/24
 * @describe
 */
@Deprecated
public class MainMapFragment extends RootFragment<MainPresenter> implements MainContract.View {

    protected OnFragmentOpenDrawerListener mOpenDraweListener;

    @BindView(R.id.map_layout)
    FrameLayout mMapLayout;
    @BindView(R.id.customer_service_layout)
    RelativeLayout customerServiceLayout;
    @BindView(R.id.tv_notify_message)
    TextBannerView mNotifyMessage;
    @BindView(R.id.tv_useing_vehicle_code)
    AppCompatTextView tvUseingVehicleCode;
    @BindView(R.id.bt_scan_unlock)
    AppCompatButton btScanUnlock;

    private TencentMap tencentMap;
    private MapLocationSource mapLocationSource;
    private BitmapDescriptor myLocationBitmap;
    private BitmapDescriptor locationTag;
    private BitmapDescriptor electricBikeLocation;
    private boolean isClickIdentification = false;
    private boolean click = true;
    private List<String> messages = new ArrayList<>();
    private List<String> messagesUrl = new ArrayList<>();
    private double latitude;
    private double longitude;

    private CameraPosition mCameraPosition;
    private LatLng mCurLatLng;
    private Polyline mCurPolyline;
    private IndexBean.ListBean mCurTag;
    private LatLng mMarkerPositon;
    boolean isFirst = true;
    private int isNeedDeposit;
    private int isUnPay;
    private int isNeedRecharge;
    private String orderPrice;
    private int isFirstUseVehicle;
    private int status;
    private String useingVehicleCode;
    private MapViewCompat mMapView;
    private LoginBean loginBean;

    @OnClick(R.id.iv_location)
    void onLocation() {
            mCurLatLng = new LatLng(latitude, longitude);
            isFirst = false;
            tencentMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
            getPresenter().index(String.valueOf(longitude), String.valueOf(latitude), 10000);
    }

    @OnClick(R.id.bt_scan_unlock)
    void onScanUnlock() {
        if (loginBean == null) {
            LoginActivity.start(getActivity());
        } else if (isNeedDeposit == 1) {
            PayDepositActivity.start(getActivity(), loginBean.getDeposit());
        }else if (isFirstUseVehicle == 1){
            BillingModeActivity.start(getActivity());
        } else if (isUnPay == 1) {
            PayArrearsActivity.start(getActivity(),orderPrice);
        }else if (isNeedRecharge == 1){
            RechargeActivity.start(getActivity());
        }else {
            ScanUnlockActivity.start(getActivity());
        }
    }

    @OnClick(R.id.iv_customer_service)
    void onCustomerService() {
        if (click) {
            customerServiceLayout.setVisibility(View.VISIBLE);
            click = false;
        } else {
            customerServiceLayout.setVisibility(View.GONE);
            click = true;
        }
    }

    @OnClick(R.id.tv_vehicle_fault)
    void onVehicleFault() {
        FaultUploadActivity.start(getProxyActivity());
    }

    @OnClick(R.id.tv_illegal_declaration)
    void onIllegalDeclaration() {
        IllegalUploadActivity.start(getProxyActivity());
    }

    @OnClick(R.id.tv_online_service)
    void onOnlineService() {
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
    }

    @OnClick(R.id.tv_user_guide)
    void onUserGuide() {
        HelpListActivity.start(getProxyActivity());
    }

    @OnClick(R.id.tv_useing_vehicle_code)
    void onUseingVehicleCode(){
//        UseTimingActivity.start(getActivity(),useingVehicleCode);
    }

    public static MainMapFragment newInstance() {
        Bundle args = new Bundle();
        MainMapFragment fragment = new MainMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setStatusBarColor(ContextCompat.getColor(mContext.getApplicationContext(), R.color.transparent))
                .setBackButton(0)
                .addLeftImage(R.mipmap.logo_icon, v -> {
                    if (mOpenDraweListener != null) {
                        mOpenDraweListener.openDrawer();
                    }
                })
                .setTitle(R.string.app_name)
                .addRightImage(R.mipmap.message_icon, v -> {
                    MyMessageActivity.start(getActivity());
                });
    }

    @Override
    public void initNotifyMessage(List<NotifyMessageBean> beans) {
        for (int i = 0; i < beans.size(); i++) {
            messages.add(beans.get(i).getTitle());
            messagesUrl.add(beans.get(i).getContent_url());
        }
        mNotifyMessage.setDatasWithDrawableIcon(messages, ContextCompat.getDrawable(getContext(), R.mipmap.gift_icon), 18, Gravity.LEFT);
        mNotifyMessage.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                WebViewActivity.start(getActivity(), data, messagesUrl.get(position));
            }
        });
    }

    @Override
    public void initIndex(IndexBean bean) {
       if (bean != null) {
           isUnPay = bean.getIs_unpay();
           isNeedDeposit = bean.getNeed_deposit();
           isNeedRecharge = bean.getNeed_recharge();
           orderPrice = String.valueOf(bean.getOrder_price());
           isFirstUseVehicle = bean.getIs_first();
           useingVehicleCode = bean.getUsing_car();

           List<IndexBean.ListBean> list = bean.getList();
           if (list != null && list.size() > 0) {
               for (IndexBean.ListBean listBean : list) {
                   MarkerOptions options = new MarkerOptions(new LatLng(Double.parseDouble(listBean.getLat()), Double.parseDouble(listBean.getLng())))
                           .icon(electricBikeLocation)
                           .tag(listBean);
                   Marker marker = tencentMap.addMarker(options);
                   startAnim(marker);
               }
           }
       }
    }

    @Override
    public void initParkingList(List<ParkingListBean> beans) {

    }

    @Override
    public void initFenceList(List<FenceListBean> beans) {

    }

    @Override
    public void initBitmap() {

    }


    @Override
    protected void initEventAndData() {
        EventBus.getDefault().register(this);
        loginBean = SPManager.getLoginBean();
        RxPermissionsUtil.request(getActivity(),RxPermissionsUtil.COARSE_LOCATION_STORAGE);
        RxPermissionsUtil.request(getActivity(),RxPermissionsUtil.CAMERA_STORAGE);
        getPresenter().index("","",0);
        getPresenter().notifyMessage();
        initMap();
        initLocation();
//        initLocationStyle();
    }
    @Override
    protected void lazyLoad() {
//        L.d("系统语言=" + Locale.getDefault().getLanguage() + "  " + Locale.getDefault().getCountry());
//        L.d("语言=" + BaseSPManager.getLanguage().getLanguage() + "  " + BaseSPManager.getLanguage().getCountry());
    }

    @Override
    public void initMap() {
        mMapView = new MapViewCompat(getProxyActivity());
        if (mMapView != null) {
            mMapLayout.addView(mMapView);
        }
        if (tencentMap == null) {
            tencentMap = mMapView.getMap();
        }

        tencentMap.setOnMapLoadedCallback(new TencentMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

            }
        });
        tencentMap.setOnCameraChangeListener(new TencentMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinished(CameraPosition cameraPosition) {
                mCameraPosition = cameraPosition;
                mCurLatLng = cameraPosition.target;

                LatLng target = cameraPosition.target;

                if (mCurPolyline != null) {
                    mCurPolyline.remove();
                    mCurPolyline = null;
                }

                if (mCurLatLng != null && mMarkerPositon != null) {
                    getWalkPlan(mCurLatLng, mMarkerPositon);
                }

                getPresenter().index(String.valueOf(target.longitude), String.valueOf(target.latitude), 10000);

            }
        });
        tencentMap.setOnMapClickListener(new TencentMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latlng) {
            }
        });

        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMarkerPositon = marker.getPosition();

                if (mCurPolyline != null) {
                    mCurPolyline.remove();
                    mCurPolyline = null;
                }

                if (mCurTag != null) {
                    IndexBean.ListBean tag = (IndexBean.ListBean) marker.getTag();
                    if (tag.getId() == mCurTag.getId()) {
                        mCurTag = null;
                        mMarkerPositon = null;
                        return true;
                    }
                }

                if (mCurLatLng != null && mMarkerPositon != null) {
                    getWalkPlan(mCurLatLng, mMarkerPositon);
                }

                mCurTag = (IndexBean.ListBean) marker.getTag();

                return true;
            }
        });


        tencentMap.setOnMarkerDragListener(new TencentMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
    }

    @Override
    public void initLocation() {
        myLocationBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.my_location_icon);
        locationTag = BitmapDescriptorFactory.fromResource(R.mipmap.location_tag_icon);
        electricBikeLocation = BitmapDescriptorFactory.fromResource(R.mipmap.electric_bike_location_icon);
        mapLocationSource = new MapLocationSource(getContext());
        tencentMap.setLocationSource(mapLocationSource);
        tencentMap.setMyLocationEnabled(true);
    }

    @Override
    public void initMyLocationMarker() {

    }

    /*   @Override
       public void initLocationStyle() {
           MyLocationStyle myLocationStyle = new MyLocationStyle();
   //      myLocationStyle.anchor(0.5f, 0.5f);
           myLocationStyle.icon(myLocationBitmap);
           myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
           tencentMap.setMyLocationStyle(myLocationStyle);
       }
   */
    @Override
    public void initMarker() {

    }

    @Override
    public void isShowPrice() {

    }

    @Override
    public void vehicleDetail(VehicleDetailBean bean) {

    }

    @Override
    public void getUnReadCount(UnReadBean data) {

    }


    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_main;
    }

    /*@Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.addLeftImage(R.mipmap.menu, v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });
    }

   */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDraweListener = (OnFragmentOpenDrawerListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentOpenDrawerListener");
        }
    }

    private void startAnim(Marker marker) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f);
        anim.setDuration(300);
        marker.setAnimation(anim);
        marker.startAnimation();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        getPresenter().index("","",0);
    }


    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mMapView.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPosition(PositionEvent event) {
        if (event.getStatus() == 2){
            tvUseingVehicleCode.setVisibility(View.VISIBLE);
            mNotifyMessage.setVisibility(View.GONE);
            btScanUnlock.setText("正在用车中");
            btScanUnlock.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_radius_yellow));
            btScanUnlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    UseTimingActivity.start(getActivity(),useingVehicleCode);
                }
            });
        }else {
            tvUseingVehicleCode.setVisibility(View.GONE);
            mNotifyMessage.setVisibility(View.VISIBLE);
            btScanUnlock.setText("扫码解锁");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDraweListener = null;
    }


    /**
     * 步行规划，只能设置起点和终点
     */
    protected void getWalkPlan(LatLng start, LatLng end) {
        TencentSearch tencentSearch = new TencentSearch(mContext);
        WalkingParam walkingParam = new WalkingParam();
        walkingParam.from(start);
        walkingParam.to(end);
        tencentSearch.getRoutePlan(walkingParam, new HttpResponseListener<WalkingResultObject>() {
            @Override
            public void onSuccess(int statusCode, WalkingResultObject object) {
                WalkingResultObject.Route route = object.result.routes.get(0);
                List<LatLng> polyline = route.polyline;

                if (object == null) {
                    return;
                }
                Log.e("searchdemo", "plan success");
                mCurPolyline = drawSolidLine(polyline);
            }

            @Override
            public void onFailure(int statusCode, String responseString, Throwable throwable) {
            }
        });
    }

    private void showPlans(List<LatLng> polyline) {

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


    /* @Override
     public void onRefresh() {

     }*/

    public class MapLocationSource implements LocationSource, TencentLocationListener {

        private Context mContext;
        private TencentLocationManager locationManager;
        private TencentLocationRequest locationRequest;
        private OnLocationChangedListener mChangedListener;

        public MapLocationSource(Context context) {
            mContext = context;
            locationManager = TencentLocationManager.getInstance(mContext);
            locationRequest = TencentLocationRequest.create();
//        locationRequest.setInterval(2000);

        }

        @Override
        public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
            if (error == TencentLocation.ERROR_OK && mChangedListener != null) {
                L.e("maplocation", "location: " + tencentLocation.getCity()
                        + " " + tencentLocation.getProvider() + " " + tencentLocation.getBearing());
                latitude = tencentLocation.getLatitude();
                longitude = tencentLocation.getLongitude();
                Location location = new Location(tencentLocation.getProvider());
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                location.setAccuracy(tencentLocation.getAccuracy());
                // 定位 sdk 只有 gps 返回的值才有可能获取到偏向角
                location.setBearing(tencentLocation.getBearing());
                mChangedListener.onLocationChanged(location);
                L.i("MapFragmentTag:" + longitude + "+" + latitude);

            }
         /*LatLng latLng = new LatLng(tencentLocation.getLatitude(),
                tencentLocation.getLongitude());
        final Marker marker = tencentMap.addMarker(new MarkerOptions(latLng)
                .title("广州")
                .snippet("DefaultMarker"));
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location_tag_icon));
*/
        }

        @Override
        public void onStatusUpdate(String name, int status, String desc) {

        }

        @Override
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
        }

        @Override
        public void deactivate() {
            locationManager.removeUpdates(this);
            mContext = null;
            locationManager = null;
            locationRequest = null;
            mChangedListener = null;
        }

        public void onPause() {
            locationManager.removeUpdates(this);
        }

        public void onResume() {
            locationManager.requestLocationUpdates(locationRequest, this);
        }
    }

}
