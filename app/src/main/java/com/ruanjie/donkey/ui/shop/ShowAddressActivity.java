package com.ruanjie.donkey.ui.shop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.tencent.map.sdk.compat.SupportMapFragmentCompat;
import com.tencent.map.sdk.compat.TencentMapCompat;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.OnClick;


public class ShowAddressActivity extends ToolbarActivity {


    @BindView(R.id.iv_location)
    AppCompatImageView ivLocation;
    private TencentMapCompat tencentMap;
    String mLatStr;
    String mLngStr;

    public static void start(Context context, String latStr, String lngStr) {
        Intent starter = new Intent(context, ShowAddressActivity.class);
        starter.putExtra("latStr", latStr);
        starter.putExtra("lngStr", lngStr);

        context.startActivity(starter);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_show_address;
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @Override
    protected void initialize() {
        initMap();

    }

    private SupportMapFragmentCompat mapFragment;
    private BitmapDescriptor myLocationBitmap;
    private BitmapDescriptor locationTagBitmap;
    Marker mMarker;

    private void initMap() {
        mLatStr = getIntent().getStringExtra("latStr");
        mLngStr = getIntent().getStringExtra("lngStr");

        myLocationBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.yellow_dot_icon);
        locationTagBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location_tag_icon);


        FragmentManager fm = getSupportFragmentManager();
        mapFragment = (SupportMapFragmentCompat) fm.findFragmentById(R.id.fragment_map);
        if (mapFragment != null) {
            if (tencentMap == null) {
                tencentMap = mapFragment.getMap();
//                tencentSearch = new TencentSearch(getContext());
////                tencentMap.getUiSettings().setZoomControlsEnabled(false);
//                tencentMap.getUiSettings().setGestureScaleByMapCenter(true);
//                tencentMap.setOnMapLoadedCallback(this);
//                tencentMap.setOnMarkerClickListener(this);
//                tencentMap.setOnMapClickListener(this);
//                tencentMap.setOnCameraChangeListener(this);
////                tencentMap.setOnMarkerDragListener(this);
            }
            LatLng latLng = new LatLng(Double.parseDouble(mLatStr), Double.parseDouble(mLngStr));
            tencentMap.addMarker(new MarkerOptions(latLng)
                    .icon(myLocationBitmap).fastLoad(true));

            tencentMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));


//            mMarker = tencentMap.addMarker(new MarkerOptions(latLng)
//                    .icon(locationTagBitmap).fastLoad(true));
//            mMarker.setFixingPoint(tencentMap.getMapWidth() / 2, tencentMap.getMapHeight() / 2 - 200);

        }
    }

    private ValueAnimator animator = null;


    @Override
    protected void onStart() {
        super.onStart();
        mapFragment.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapFragment.onStop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        if (tencentMap.isMyLocationEnabled()) {
            tencentMap.setMyLocationEnabled(false);
        }
        if (!tencentMap.isDestroyed()) {
            tencentMap.clearAllOverlays();
            mapFragment.onDestroyView();
        }
        super.onDestroy();
    }

    @OnClick({R.id.ivBack, R.id.iv_location})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.iv_location:
//                LatLng latLng = new LatLng(Double.parseDouble(mLatStr), Double.parseDouble(mLngStr));
//                tencentMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                break;
        }
    }
}
