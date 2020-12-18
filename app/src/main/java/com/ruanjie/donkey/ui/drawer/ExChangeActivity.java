package com.ruanjie.donkey.ui.drawer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ExChangeMarkerDataBean;
import com.ruanjie.donkey.bean.VehicleListBean;
import com.ruanjie.donkey.ui.drawer.contract.ExChangeContract;
import com.ruanjie.donkey.ui.drawer.presenter.ExChangePresenter;
import com.ruanjie.donkey.ui.scanner.ExScanActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.EmptyUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExChangeActivity extends ToolbarActivity<ExChangePresenter>
        implements TencentLocationListener,ExChangeContract.View, TencentMap.OnMarkerClickListener, TencentMap.OnCameraChangeListener, TencentMap.OnMapLoadedCallback {

    @BindView(R.id.civLocation)
    CircleImageView civLocation;
    @BindView(R.id.tvScan)
    BLTextView tvScan;
    @BindView(R.id.civHistory)
    CircleImageView civHistory;

    private SupportMapFragmentCompat mapFragment;
    private TencentMapCompat tencentMap;
    private BitmapDescriptor myLocationBitmap;
    private BitmapDescriptor tagLocationBitmap;
    private BitmapDescriptor yellowElectricBikeBitmap;
    private Marker yellowElectricBikeMarker;
    private String vehicleCode;
    private LatLng mCurrentPosition;
    private TencentLocation location;
    private double longitude;
    private double latitude;
    private boolean isFirstLocation = true;
    private TencentLocationManager mLocationManager;
    private TencentLocationRequest locationRequest;
//    private MyLocationStyle myLocationStyle;
//    private LocationSource.OnLocationChangedListener mChangedListener;
    private Marker locationTagMarker;
    private ValueAnimator animator = null;//坐标动画
    private ArrayList<Marker> yellowElectricBikeMarkers = new ArrayList<>();
    private VehicleListBean vehicleListBean;
    private Marker myLocationMarker;


    /* @OnLongClick(R.id.vClick)
    boolean onLongClick() {
        showSureDialog();
        return true;
    }
*/
    @OnClick({R.id.civLocation, R.id.civHistory, R.id.tvScan})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civLocation:
                mCurrentPosition = new LatLng(latitude,longitude);
                if (mCurrentPosition != null){
                    tencentMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentPosition,16));
                }
                break;
            case R.id.civHistory:
                ExChangeHistoryActivity.start(getContext());
                break;
            case R.id.tvScan:
                ExScanActivity.start(getContext());
                break;
        }
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, ExChangeActivity.class);
//        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("机动协管")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_exchange;
    }

    @Override
    protected void initialize() {
        initMap();
        initBitmap();
        mLocationManager = TencentLocationManager.getInstance(getContext());
        startLocation();
    }

    @Override
    public ExChangePresenter createPresenter() {
        return new ExChangePresenter(this);
    }

    @Override
    public void getExChangeTask(String data) {
        ToastUtil.s("领取成功");
    }

    @Override
    public void initMap() {
        FragmentManager fm = getSupportFragmentManager();
        mapFragment = (SupportMapFragmentCompat)fm.findFragmentById(R.id.fragment_map);
        if (mapFragment != null) {
            if (tencentMap == null) {
                tencentMap = mapFragment.getMap();
                tencentMap.getUiSettings().setGestureScaleByMapCenter(true);
                tencentMap.setOnMapLoadedCallback(this);
                tencentMap.setOnMarkerClickListener(this);
                tencentMap.setOnCameraChangeListener(this);
            }
        }
    }

    private void initBitmap() {
        myLocationBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.yellow_dot_icon);
        tagLocationBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location_tag_icon);
        yellowElectricBikeBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.yellow_electric_bike_icon);
    }


    /*@Override
    public void initLocationStyle() {
        if (myLocationStyle == null) {
            myLocationStyle = new MyLocationStyle();
            myLocationStyle.anchor(0.5f, 0.5f);
            myLocationStyle.icon(myLocationBitmap);
            myLocationStyle.fillColor(0);
            myLocationStyle.strokeColor(0);
            myLocationStyle.strokeWidth(0);
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
            tencentMap.setMyLocationStyle(myLocationStyle);
        }
    }*/

    @Override
    public void initMarker() {
        if (locationTagMarker == null) {
            locationTagMarker = tencentMap.addMarker(new MarkerOptions(new LatLng(latitude, longitude))
                    .icon(tagLocationBitmap).fastLoad(true));
            locationTagMarker.setFixingPoint(tencentMap.getMapWidth() / 2,tencentMap.getMapHeight() / 2 - 50);
            animMarker();
        }else {
            locationTagMarker.setFixingPoint(tencentMap.getMapWidth() / 2,tencentMap.getMapHeight() / 2 - 50);
            locationTagMarker.setFastLoad(true);
            animMarker();
        }
        locationTagMarker.setClickable(false);
    }
 /*   private void initMyLocationMarker() {
//        if (myLocationMarker == null) {
            myLocationMarker = tencentMap.addMarker(new MarkerOptions(new LatLng(latitude, longitude))
                    .icon(myLocationBitmap));
//            myLocationMarker.setPosition(new LatLng(latitude,longitude));
            myLocationMarker.setFastLoad(true);
            myLocationMarker.setClickable(false);
//        }
        *//*else {
            myLocationMarker.setPosition(new LatLng(latitude,longitude));
        }*//*
        *//*myLocationMarker.setFastLoad(true);
        myLocationMarker.setClickable(false);*//*
    }*/

    private void startLocation() {
        locationRequest= TencentLocationRequest.create();
        locationRequest.setInterval(3000);
        mLocationManager.requestLocationUpdates(locationRequest, this);
//        tencentMap.setLocationSource(this);
        tencentMap.setMyLocationEnabled(true);
    }

    private void stopLocation() {
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void initExChangeList(List<VehicleListBean> beans) {
        if (yellowElectricBikeMarkers != null && yellowElectricBikeMarkers.size() > 0){
            for (Marker yellowElectricBikeMarker : yellowElectricBikeMarkers){
                yellowElectricBikeMarker.remove();
            }
        }
        if (EmptyUtil.isNotEmpty(beans) && beans.size() > 0) {
              for (VehicleListBean bean : beans) {
                 ExChangeMarkerDataBean markerDataBean = new ExChangeMarkerDataBean();
                 markerDataBean.setVehicleListBean(bean);

                    yellowElectricBikeMarker = tencentMap.addMarker(
                            new MarkerOptions(new LatLng(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLng())))
                                    .icon(yellowElectricBikeBitmap)
                                    .fastLoad(true)
                                    .tag(markerDataBean));
                    yellowElectricBikeMarkers.add(yellowElectricBikeMarker);
            }
        }
    }

    @Override
    public void onMapLoaded() {
//        initMarker();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        ExChangeMarkerDataBean tag = (ExChangeMarkerDataBean) marker.getTag();
        vehicleListBean = tag.getVehicleListBean();
        vehicleCode = vehicleListBean.getCode();
        showSureDialog();
        return true;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinished(CameraPosition cameraPosition) {
        mCurrentPosition = cameraPosition.target;
        if (mCurrentPosition != null){
            getPresenter().exchangeList(mCurrentPosition.longitude,mCurrentPosition.latitude,800);
        }
        if (locationTagMarker != null){
            animMarker();
        }
    }

    public void showSureDialog() {

        DiaLogUtils.showTipDialog(getContext(), "温馨提示",
                "您确定接取这辆车的换电任务吗"+"\n编号：" + vehicleCode
                , "取消", "确认", new PromptDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                        if (isPositiveClick) {
                            //获取换电任务
                            getPresenter().getExChangeTask(vehicleCode);
                        }
                    }
                });

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
                locationTagMarker.setFixingPoint(tencentMap.getMapWidth() / 2,Math.round(value));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                locationTagMarker.setIcon(tagLocationBitmap);
            }
        });
        animator.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapFragment.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.onResume();
//        initMyLocationMarker();
        getPresenter().exchangeList(longitude,latitude,800);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapFragment.onPause();
        stopLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapFragment.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mLocationManager != null){
            mLocationManager = null;

        }
        if (locationRequest != null){
            locationRequest = null;
        }
        if (!tencentMap.isDestroyed()){
            tencentMap.clearAllOverlays();
            mapFragment.onDestroyView();
        }
        super.onDestroy();

    }


    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
        if (error == TencentLocation.ERROR_OK /*&& mChangedListener != null*/){
               this.location = tencentLocation;
               this.longitude = tencentLocation.getLongitude();
               this.latitude = tencentLocation.getLatitude();
            if (myLocationMarker != null){
                myLocationMarker.remove();
            }

            myLocationMarker = tencentMap.addMarker(new MarkerOptions(new LatLng(latitude, longitude))
                    .icon(myLocationBitmap).fastLoad(true));
            myLocationMarker.setClickable(false);

            if (isFirstLocation) {
                this.isFirstLocation = false;
                //当前点
                Location location = new Location(tencentLocation.getProvider());
                location.setLatitude(tencentLocation.getLatitude());
                location.setLongitude(tencentLocation.getLongitude());
                location.setAccuracy(tencentLocation.getAccuracy());
                // 定位 sdk 只有 gps 返回的值才有可能获取到偏向角
                location.setBearing(tencentLocation.getBearing());
                //定位到当前位置并且设置缩放级别
                tencentMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),16));
                initMarker();
                getPresenter().exchangeList(location.getLongitude(),location.getLatitude(),800);
//                mChangedListener.onLocationChanged(location);
            }
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }


  /*  @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        isFirstLocation = true;
        mChangedListener = onLocationChangedListener;

    }

    @Override
    public void deactivate() {
        *//*stopLocation();
        mLocationManager = null;
        locationRequest = null;*//*
        mChangedListener = null;
    }*/


}
