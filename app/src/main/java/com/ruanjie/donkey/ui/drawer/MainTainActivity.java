package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.VehicleListBean;
import com.ruanjie.donkey.ui.drawer.contract.MainTainContract;
import com.ruanjie.donkey.ui.drawer.presenter.MainTainPresenter;
import com.ruanjie.donkey.ui.map.MapLocationSource;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.tencent.map.sdk.compat.SupportMapFragmentCompat;
import com.tencent.map.sdk.compat.TencentMapCompat;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.MyLocationStyle;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainTainActivity extends ToolbarActivity<MainTainPresenter> implements MainTainContract.View {


    @BindView(R.id.civLocation)
    CircleImageView civLocation;
    @BindView(R.id.tvUpload)
    BLTextView tvUpload;
    @BindView(R.id.civHistory)
    CircleImageView civHistory;
    private SupportMapFragmentCompat mapFragment;
    private TencentMapCompat tencentMap;
    private MapLocationSource locationSource;
    private BitmapDescriptor myLocationBitmap;
    private BitmapDescriptor tagLocationBitmap;
    private BitmapDescriptor yellowElectricBikeBitmap;
    private BitmapDescriptor orangeElectricBikeBitmap;
    private BitmapDescriptor redElectricBikeBitmap;



    public static void start(Context context) {
        Intent starter = new Intent(context, MainTainActivity.class);
//        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Override
    public MainTainPresenter createPresenter() {
        return new MainTainPresenter(this);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("维护保养")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_main_tain;
    }

    @Override
    protected void initialize() {
        initMap();
        initLocation();
        initLocationStyle();
        initMarker();
    }

    @OnClick({R.id.civLocation, R.id.civHistory, R.id.tvUpload})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civLocation:

                break;
            case R.id.civHistory:
                MaintainHistoryActivity.start(getContext());
                break;
            case R.id.tvUpload:
                MainTainUploadActivity.start(getContext());
                break;
        }
    }

    @Override
    public void initMap() {
        FragmentManager fm = getSupportFragmentManager();
        mapFragment = (SupportMapFragmentCompat)fm.findFragmentById(R.id.fragment_map);
        if (mapFragment != null) {
            if (tencentMap == null) {
                tencentMap = mapFragment.getMap();
            }
        }
    }

    @Override
    public void initLocation() {
        locationSource = new MapLocationSource(this);
        tencentMap.setLocationSource(locationSource);
        tencentMap.setMyLocationEnabled(true);
    }

    @Override
    public void initLocationStyle() {
        myLocationBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.yellow_dot_icon);
        tagLocationBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location_tag_icon);
        yellowElectricBikeBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.yellow_electric_bike_icon);
        orangeElectricBikeBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.orange_electric_bike_icon);
        redElectricBikeBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.red_electric_bike_icon);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.anchor(0.5f, 0.5f);
        myLocationStyle.icon(myLocationBitmap);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        tencentMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void initMarker() {

        //标注坐标
       /* final Marker marker = tencentMap.
                addMarker(new MarkerOptions(new LatLng(locationSource.latitude,locationSource.longitude)));
        marker.setIcon(tagLocationBitmap);*/
        getPresenter().chargeVehicleList(113.395944,23.131104,10000 );
        getPresenter().illegalParkingVehicleList(113.395944,23.131104,10000);
        getPresenter().faultVehicleList(113.395944,23.131104,10000);
    }

    @Override
    public void chargeVehicleList(List<VehicleListBean> beans) {
        if (beans != null && beans.size() > 0) {
            for (VehicleListBean bean : beans) {
                Marker marker = tencentMap.
                        addMarker(new MarkerOptions(new LatLng(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLng())))
                                .icon(yellowElectricBikeBitmap));
            }
        }
    }

    @Override
    public void illegalParkingVehicleList(List<VehicleListBean> beans) {
        if (beans != null && beans.size() > 0) {
            for (VehicleListBean bean : beans) {
                Marker marker = tencentMap.
                        addMarker(new MarkerOptions(new LatLng(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLng())))
                                .icon(orangeElectricBikeBitmap));
            }
        }
    }

    @Override
    public void faultVehicleList(List<VehicleListBean> beans) {
        if (beans != null && beans.size() > 0) {
            for (VehicleListBean bean : beans) {
                Marker marker = tencentMap.
                        addMarker(new MarkerOptions(new LatLng(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLng())))
                                .icon(redElectricBikeBitmap));
            }
        }
    }


}
