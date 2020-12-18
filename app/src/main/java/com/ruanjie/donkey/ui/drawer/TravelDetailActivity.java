package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.TravelDetailBean;
import com.ruanjie.donkey.ui.drawer.contract.TravelDetailContract;
import com.ruanjie.donkey.ui.drawer.presenter.TravelDetailPresenter;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.LogUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.DecimalUtil;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.tencent.map.sdk.compat.SupportMapFragmentCompat;
import com.tencent.map.sdk.compat.TencentMapCompat;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.LatLngBounds;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TravelDetailActivity extends ToolbarActivity<TravelDetailPresenter>
        implements TravelDetailContract.View, UMShareListener {

    @BindView(R.id.tvTravelMoney)
    AppCompatTextView tvTravelMoney;
    @BindView(R.id.tvTravelDistance)
    AppCompatTextView tvTravelDistance;
    @BindView(R.id.tvTravelTime)
    AppCompatTextView tvTravelTime;
    @BindView(R.id.tvCarNum)
    AppCompatTextView tvCarNum;
    int mId;
    TravelDetailBean mDetailBean;
    private SupportMapFragmentCompat mapFragment;
    private TencentMapCompat tencentMap;

    @OnClick(R.id.travel_costs_layout)
    void onTravelCostsLayout(){
        if (mDetailBean != null){
            Double useVehicleCosts = Double.valueOf(mDetailBean.getCar_fee());
            Double parkingCosts = Double.valueOf(mDetailBean.getParking_fee());

            L.e("useVehicleCostsTag:"+useVehicleCosts);
            L.e("parkingCostsTag:"+parkingCosts);

            DiaLogUtils.showSureDialog(getContext(), "用车费用：" + useVehicleCosts,
                   "调度费用：" +  parkingCosts, "我知道了", dialog -> {
                   });


        }
    }

    public static void start(Context context, int id) {
        Intent starter = new Intent(context, TravelDetailActivity.class);
        starter.putExtra("id", id);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("行程详情")
                .addRightImage(R.mipmap.share_icon, v -> rightClick())
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    private void rightClick() {
        if (mDetailBean != null) {
            //分享
            DiaLogUtils.showShareDialog(getActivity(), new DiaLogUtils.OnDialogShareListener() {
                @Override
                public void onShare(int which) {
                    openShare(which);
                }
            });
        }

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
        web.setThumb(new UMImage(getContext(), R.mipmap.logo));  //缩略图
        web.setDescription("骑行时间: " + mDetailBean.getDuration2()
                + "分钟 骑行距离: " + DecimalUtil.formatDecimal2(mDetailBean.getMileage() / 1000) + "km");//描述

        new ShareAction(getActivity())
                .setPlatform(share_media)//传入平台
                .withMedia(web)
                .setCallback(this)//回调监听器
                .share();
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_travel_detail;
    }

    @Override
    protected void initialize() {
        mId = getIntent().getIntExtra("id", 0);
        getPresenter().getTravelDetail(mId);
        initMap();

    }

    @Override
    public TravelDetailPresenter createPresenter() {
        return new TravelDetailPresenter();
    }

    @Override
    public void getTravelDetail(TravelDetailBean data) {
        mDetailBean = data;
        tvTravelMoney.setText(data.getTotal_price());
        tvTravelDistance.setText(DecimalUtil.formatDecimal2(data.getMileage() / 1000));
        tvTravelTime.setText(String.valueOf(data.getDuration2()));
        tvCarNum.setText("电动车编号：" + data.getCode());

        //画线
        String points = data.getPoints();
        if (!TextUtils.isEmpty(points)) {
            List<List<String>> list = new Gson().fromJson(points, new TypeToken<List<List<String>>>() {
            }.getType());
            List<LatLng> latLags = getLatLags(list);
            //构造经纬度的集合
            tencentMap.addPolyline(new PolylineOptions()
                    .addAll(latLags)
                    .width(20).lineCap(true)
                    .color(0xff24CC80));
            if (latLags != null && latLags.size() > 1) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                LatLngBounds bounds = null;
                for (LatLng itemBean : latLags) {
                    builder.include(itemBean);//把你所有的坐标点放进去
                }
                bounds = builder.build();
                tencentMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));//第二个参数：设置经纬度范围和mapView边缘的空隙，单位像素。这个值适用于区域的四个边。

            }
            //然后在起点和终点添加marker
            MarkerOptions options1 = new MarkerOptions(latLags.get(0))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.car_1));
            MarkerOptions options2 = new MarkerOptions(latLags.get(latLags.size() - 1))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.car_2));
            tencentMap.addMarker(options1);
            tencentMap.addMarker(options2);
        }


    }

    private List<LatLng> getLatLags(List<List<String>> list) {

        List<LatLng> mDatas = new ArrayList<>();
        for (List<String> list1 : list) {
            mDatas.add(new LatLng(Double.parseDouble(list1.get(1)), Double.parseDouble(list1.get(0))));
        }
//        mDatas.add(new LatLng(23.126553, 113.390816));
//        mDatas.add(new LatLng(23.130087, 113.393204));
//        mDatas.add(new LatLng(23.133244, 113.39492));
//        mDatas.add(new LatLng(23.136518, 113.396683));
//        mDatas.add(new LatLng(23.137703, 113.398483));

        return mDatas;
    }

    @Override
    public void initMap() {
        FragmentManager fm = getSupportFragmentManager();
        mapFragment = (SupportMapFragmentCompat) fm.findFragmentById(R.id.fragment_map);
        if (mapFragment != null) {
            if (tencentMap == null) {
                tencentMap = mapFragment.getMap();
            }
        }
    }

    @Override
    public void initMarker() {

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
}
