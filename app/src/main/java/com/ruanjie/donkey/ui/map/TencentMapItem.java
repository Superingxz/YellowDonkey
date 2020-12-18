package com.ruanjie.donkey.ui.map;

import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.ClusterItem;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.map
 * 文件名:   TencentMapItem
 * 创建者:    QJM
 * 创建时间: 2019/9/16 15:12
 * 描述:     TODO
 */
public class TencentMapItem implements ClusterItem {

    private final LatLng mLatLng;

    public TencentMapItem(double latitude, double longitude) {
        // TODO Auto-generated constructor stub
        mLatLng = new LatLng(latitude, longitude);
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }
}
