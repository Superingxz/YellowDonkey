package com.ruanjie.donkey.ui.map;

import android.content.Context;
import android.location.Location;

import com.ruanjie.donkey.bean.LocationBean;
import com.softgarden.baselibrary.utils.L;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.map
 * 文件名:   MapLocationSource
 * 创建者:    QJM
 * 创建时间: 2019/8/6 17:03
 * 描述:     TODO
 */
public class MapLocationSource implements LocationSource, TencentLocationListener {

    private Context mContext;
    private TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;
    private OnLocationChangedListener mChangedListener;
    public Location location;
    public double longitude;
    public double latitude;
    private boolean isFirstLocation = true;

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
            LocationBean locationBean = new LocationBean();
            locationBean.latitude = tencentLocation.getLatitude();
            locationBean.longitude = tencentLocation.getLongitude();
            if (isFirstLocation) {
                isFirstLocation = false;
                location = new Location(tencentLocation.getProvider());
                location.setLongitude(locationBean.longitude);
                location.setLatitude(locationBean.latitude);
                location.setAccuracy(tencentLocation.getAccuracy());
                // 定位 sdk 只有 gps 返回的值才有可能获取到偏向角
                location.setBearing(tencentLocation.getBearing());
                mChangedListener.onLocationChanged(location);
            }
        }


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

    public void onResume() {
        locationManager.requestLocationUpdates(locationRequest, this);
    }

    public void onPause() {
        locationManager.removeUpdates(this);
    }


}
