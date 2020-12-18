package com.ruanjie.donkey.ui.billing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.LockBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.ParkingAreaBean;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.ruanjie.donkey.ui.billing.contract.UseTimingContract;
import com.ruanjie.donkey.ui.billing.presenter.UseTimingPresenter;
import com.ruanjie.donkey.ui.main.MainActivity;
import com.ruanjie.donkey.ui.scanner.ScanUnlockActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.SPManager;
import com.ruanjie.donkey.utils.TimeUtils;
import com.ruanjie.toolsdk.config.ToolSdk;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.softgarden.baselibrary.utils.L;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxTextTool;
import com.vondear.rxtool.view.RxToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.billing
 * 文件名:   UseTimingActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/10 23:34
 * 描述:     TODO
 */
public class UseTimingActivity extends ToolbarActivity<UseTimingPresenter> implements UseTimingContract.View, TencentLocationListener {

    @BindView(R.id.tv_vehicle_code)
    AppCompatTextView tvVehicleCode;
    @BindView(R.id.tv_residual_electricity)
    AppCompatTextView tvResidualElectricity;
    @BindView(R.id.tv_range_voyage)
    AppCompatTextView tvRangeVoyage;
    @BindView(R.id.tv_driving_time)
    AppCompatTextView tvDrivingTime;
    @BindView(R.id.tv_price)
    AppCompatTextView tvPrice;
    @BindView(R.id.tv_driving_distance)
    AppCompatTextView tvDrivingDistance;
    @BindView(R.id.bt_vehicle_retention)
    AppCompatButton btVehicleRetention;
    private String code;

    private TimeRunnable mTimeRunnable;
    private int time = 0;
    private int status;
    private boolean isEnd = true;
    private TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;
    private LocationSource.OnLocationChangedListener mChangedListener;
    private double latitude;
    private double longitude;
    private boolean isChange;
    private int userId;
    private LoginBean loginBean;
    private ReRunnable mReRunnable;
    private Double price;
    private double minutePrice;

    @OnClick(R.id.bt_vehicle_retention)
    void onVehicleRetention(){
        if (status == 2) {
            getPresenter().pauseUseVehicle();
        }else if (status == 3) {
            getPresenter().continueUseVehicle();
        }
    }

    @OnClick(R.id.bt_exchange_vehicle)
    void onExchangeVehicle(){
        locationManager.requestLocationUpdates(locationRequest, this);
        isChange = false;
    }
    @OnClick(R.id.bt_end_use_vehicle)
    void onEndUseVehicle(){
        locationManager.requestLocationUpdates(locationRequest, this);
        isChange = true;
    }

    @Override
    public UseTimingPresenter createPresenter() {
        return new UseTimingPresenter(this);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, UseTimingActivity.class));
    }

   /* public static void start(Context context,String code,int time) {
        context.startActivity(new Intent(context, UseTimingActivity.class)
        .putExtra("code",code).putExtra("time",time));
    }*/
    public static void start(Context context,String code) {
        context.startActivity(new Intent(context, UseTimingActivity.class)
        .putExtra("code",code));
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("使用计时中").setTitleTextSize(18);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_use_timing;
    }

    @Override
    protected void initialize() {
         code = getIntent().getStringExtra("code");
//         time = getIntent().getIntExtra("time",0);

        getPresenter().vehicleDetail(code);

        initLocation();
    }

    private void initLocation() {
        locationManager = TencentLocationManager.getInstance(getContext());
        locationRequest = TencentLocationRequest.create();
    }

    @Override
    public void vehicleDetail(VehicleDetailBean bean) {
        if (bean != null) {
            userId = bean.getUser_id();
            tvVehicleCode.setText(bean.getCode());
            RxTextTool.getBuilder(String.valueOf(bean.getEnegy())).setBold().setProportion(2).append("%").into(tvResidualElectricity);
            RxTextTool.getBuilder(String.valueOf(bean.getMileage())).setBold().setProportion(2).append("km").into(tvRangeVoyage);
            time = bean.getDuration();
            price = Double.valueOf(bean.getPrice_minute());
            if (mTimeRunnable == null) {
                mTimeRunnable = new TimeRunnable();
                ToolSdk.getHandler().postDelayed(mTimeRunnable, 0);
            }
            minutePrice = (((time + 59) / 60 + 4) / 5) * price;
            RxTextTool.getBuilder(String.valueOf(minutePrice)).setBold().setProportion(2).append("元").into(tvPrice);
            RxTextTool.getBuilder(bean.getCurMileage()).setBold().setProportion(2).append("公里").into(tvDrivingDistance);
            status = bean.getStatus();
        }
        loginBean = SPManager.getLoginBean();
        if (userId != loginBean.getUser_id()){
            MainActivity.start(getContext(),false,false);
           /* Bundle bundle = new Bundle();
            bundle.putBoolean("status",false);
            RxActivityTool.skipActivityAndFinishAll(getContext(),MainActivity.class,bundle);*/
        }
    }

    @Override
    public void isParkingArea(ParkingAreaBean data,boolean isEnd) {
        if (isEnd) {
            if (data.getIsInside() == 1) {
                getPresenter().lockVehicle(String.valueOf(longitude),String.valueOf(latitude));
            } else if (data.getIsInside() == 0) {
                DiaLogUtils.showTipDialog(getContext(), "提示"
                        , "目前不在停车区域，还车需要调度费"
                        , "取消"
                        , "确定"
                        , new PromptDialog.OnButtonClickListener() {
                            @Override
                            public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                if (isPositiveClick) {
                                    L.e("locationTag请求接口拿到位置"+longitude+":"+latitude);
                                    getPresenter().lockVehicle(String.valueOf(longitude),String.valueOf(latitude));
                                }
                            }
                 });
            }
        }else {
            if (data.getIsInside() == 1) {
                ScanUnlockActivity.start(getContext(),true);
            } else if (data.getIsInside() == 0) {
                DiaLogUtils.showTipDialog(getContext(), "提示"
                        , "目前不在停车区域，还车需要调度费"
                        , "取消"
                        , "确定"
                        , new PromptDialog.OnButtonClickListener() {
                            @Override
                            public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                if (isPositiveClick) {
                                  ScanUnlockActivity.start(getContext(),true);
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void pauseSuccess() {
        RxToast.showToast("保留成功");
        btVehicleRetention.setText("再次启用");
        btVehicleRetention.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.shape_radius_yellow));
        getPresenter().vehicleDetail(code);
    }
    @Override
    public void retainSuccess() {
        btVehicleRetention.setText("车辆保留");
        btVehicleRetention.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.shape_radius_black));
        getPresenter().vehicleDetail(code);
    }


    @Override
    public void lockVehicleSuccess(LockBean data) {
        RxToast.success("结束成功");
        if (data.getTotal_price() == 0){
            RxToast.showToast("骑行不足一分钟不需要付款");
            MainActivity.start(getContext(),false,true);
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("arrears",String.valueOf(data.getTotal_price()));
            RxActivityTool.skipActivityAndFinishAll(getContext(),PayArrearsActivity.class,bundle);
        }
//        PayArrearsActivity.start(getContext(),String.valueOf(data.getTotal_price()));
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
     longitude = tencentLocation.getLongitude();
     latitude = tencentLocation.getLatitude();

        locationManager.removeUpdates(this);

        if (isChange) {
            getPresenter().isParkingArea(String.valueOf(longitude), String.valueOf(latitude), true);
        }else {
            getPresenter().isParkingArea(String.valueOf(longitude),String.valueOf(latitude),false);
        }
      }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    private class TimeRunnable implements Runnable {
        @Override
        public void run() {
            time++;
//            tvDrivingTime.setText(TimeUtils.formattedTime(time));
            RxTextTool.getBuilder(TimeUtils.formattedTime(time)).setBold().into(tvDrivingTime);
            ToolSdk.getHandler().postDelayed(this, 1000);
        }
    }
    private class ReRunnable implements Runnable {
        @Override
        public void run() {
            getPresenter().vehicleDetail(code);
            ToolSdk.getHandler().postDelayed(this,30000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReRunnable == null) {
            mReRunnable = new ReRunnable();
            ToolSdk.getHandler().postDelayed(mReRunnable,0);
        }

        /*if (mRunnable == null) {
            mRunnable = new TimeRunnable();
            ToolSdk.getHandler().postDelayed(mRunnable, 0);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTimeRunnable != null || mReRunnable != null){
            ToolSdk.getHandler().removeCallbacks(mTimeRunnable);
            ToolSdk.getHandler().removeCallbacks(mReRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        if (mTimeRunnable != null && mReRunnable != null){
            ToolSdk.getHandler().removeCallbacks(mTimeRunnable);
            ToolSdk.getHandler().removeCallbacks(mReRunnable);
            mTimeRunnable = null;
            mReRunnable = null;
        }
        super.onDestroy();

    }


}
