package com.ruanjie.donkey.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by adam on 2017/12/11.
 */

public class LocationUtils {

    private static LocationManager locationManager;

    /**
     * 计算两点间的距离--公里
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static String getDistance(double lat1, double lon1, double lat2, double lon2) {
        LogUtils.d("location", "lat1 --> " + lat1 + " lon1 --> " + lon1 + " lat2 --> " + lat2 + " lon2 --> " + lon2);
        float[] results = new float[1];
        try {
            Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        double distance = results[0] / 1000d;
        String result = String.valueOf(distance);
        int temp = result.indexOf(".");
        if (distance < 0) {
            result = result.substring(temp + 1, temp + 4) + "m";
        } else {
            result = result.substring(0, temp) + "km";
        }
        return result;
    }

    public static boolean getLocation(Context context, LocationListener locationListener) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) return false;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        String providerName = locationManager.getBestProvider(criteria, true);
        LocationProvider proDiverList = locationManager.getProvider(providerName);
        String name = proDiverList.getName();
        if (LocationManager.NETWORK_PROVIDER.equals(proDiverList.getName())) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            return true;
        } else if (LocationManager.GPS_PROVIDER.equals(proDiverList.getName())) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            return true;
        } /*else{
            return true;*/
        else {
            // 没有开启定位
            return false;
        }
    }

    /**
     * 判断是否有可用的内容提供器
     *
     * @return 不存在返回null
     */
    private static String judgeProvider(LocationManager locationManager) {
        String providerName = locationManager.getBestProvider(new Criteria(), true);
        LocationProvider proDiverList = locationManager.getProvider(providerName);
        if (LocationManager.NETWORK_PROVIDER.equals(proDiverList.getName())) {
            return LocationManager.NETWORK_PROVIDER;
        } else if (LocationManager.GPS_PROVIDER.equals(proDiverList.getName())) {
            return LocationManager.GPS_PROVIDER;
        } else {
            return null;
        }
    }

    public static LocationManager getLocationManager() {
        return locationManager;
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvaliable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) (context
                .getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return !(networkinfo == null || !networkinfo.isAvailable());
    }

    /**
     * 判断网络类型 wifi  3G
     */
    public static boolean isWifiNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getTypeName().equalsIgnoreCase("wifi")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 扫描并保存wifi
     */
    /*public static WifiInfoBody getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            wifiManager.startScan();
            List<ScanResult> lsScanResult = wifiManager.getScanResults();//记录所有附近wifi的搜索结果
            if (!(lsScanResult != null && lsScanResult.size() > 0)) return null;
            Collections.sort(lsScanResult, (scanResult1, scanResult2) -> scanResult2.level - scanResult1.level);// 信号强度排序
            List<WifiAccessPoints> wifiAccessPoints = new ArrayList<>();
            WifiInfoBody wifiInfoBodies = new WifiInfoBody();
            // 超出13个wifi google报错
            for (int i = 0; i < lsScanResult.size(); i++) {
                if (i >= 12) break;
                ScanResult scanResult = lsScanResult.get(i);
                WifiAccessPoints wifi = new WifiAccessPoints();
                wifi.setMacAddress(scanResult.BSSID.toUpperCase());
                wifi.setSignalStrength(scanResult.level);
                wifi.setChannel(getChannelByFrequency(scanResult.frequency));
                wifi.setAge(0);
                wifiAccessPoints.add(wifi);
            }
            wifiInfoBodies.setWifiAccessPoints(wifiAccessPoints);
            return wifiInfoBodies;
        }
        return null;
    }*/

    public static WifiInfo getWifiByConnection(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            return wifiManager.getConnectionInfo();
        }
        return null;
    }

    /**
     * 根据频率获得信道
     */
    public static short getChannelByFrequency(int frequency) {
        short channel = -1;
        switch (frequency) {
            case 2412:
                channel = 1;
                break;
            case 2417:
                channel = 2;
                break;
            case 2422:
                channel = 3;
                break;
            case 2427:
                channel = 4;
                break;
            case 2432:
                channel = 5;
                break;
            case 2437:
                channel = 6;
                break;
            case 2442:
                channel = 7;
                break;
            case 2447:
                channel = 8;
                break;
            case 2452:
                channel = 9;
                break;
            case 2457:
                channel = 10;
                break;
            case 2462:
                channel = 11;
                break;
            case 2467:
                channel = 12;
                break;
            case 2472:
                channel = 13;
                break;
            case 2484:
                channel = 14;
                break;
            case 5745:
                channel = 149;
                break;
            case 5765:
                channel = 153;
                break;
            case 5785:
                channel = 157;
                break;
            case 5805:
                channel = 161;
                break;
            case 5825:
                channel = 165;
                break;
        }
        return channel;
    }

}
