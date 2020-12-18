package com.ruanjie.donkey.ui.scanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.mirkowu.statusbarutil.StatusBarUtil;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.UnlockBean;
import com.ruanjie.donkey.listener.OnRxScanerListener;
import com.ruanjie.donkey.ui.drawer.PayDepositActivity;
import com.ruanjie.donkey.ui.main.MainActivity;
import com.ruanjie.donkey.ui.scanner.contract.ScanContract;
import com.ruanjie.donkey.ui.scanner.presenter.ScanPresenter;
import com.ruanjie.donkey.ui.unlock.WaitUnlockActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.toolsdk.ui.scanner.decoding.InactivityTimer;
import com.ruanjie.toolsdk.ui.scanner.manager.CameraManager;
import com.softgarden.baselibrary.base.BaseActivity;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.vondear.rxtool.RxAnimationTool;
import com.vondear.rxtool.RxBeepTool;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.scanner
 * 文件名:   ScanActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/7 15:57
 * 描述:     TODO
 */
public class ScanUnlockActivity extends BaseActivity<ScanPresenter> implements ScanContract.View, TencentLocationListener {

    /**
     * 扫描结果监听
     */
    private static OnRxScanerListener mScanerListener;

    private InactivityTimer inactivityTimer;

    /**
     * 扫描处理
     */
    private ScanHandler handler;


    /**
     * 扫描边界的宽度
     */
    private int mCropWidth = 0;

    /**
     * 扫描边界的高度
     */
    private int mCropHeight = 0;

    /**
     * 是否有预览
     */
    private boolean hasSurface;

    /**
     * 扫描成功后是否震动
     */
    private boolean vibrate = true;

    /**
     * 闪光灯开启状态
     */
    private boolean mFlashing = true;

    private MultiFormatReader multiFormatReader;
    private String code;
    private boolean isChange = false;
    private TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;
    private double longitude;
    private double latitude;

    /**
     * 设置扫描信息回调
     */
    public static void setScanerListener(OnRxScanerListener scanerListener) {
        mScanerListener = scanerListener;
    }


    @BindView(R.id.scan_containter)
    RelativeLayout mContainer = null;
    @BindView(R.id.scan_layout)
    RelativeLayout mScanLayout = null;
    @BindView(R.id.tv_flashlight)
    AppCompatTextView mTvFlashLight = null;
    @BindView(R.id.iv_scan_line)
    AppCompatImageView mIvScanLine = null;
    @BindView(R.id.scan_preview)
    SurfaceView mScanPreview = null;
    @BindView(R.id.logo_toolbar)
    Toolbar logoToolbar;

    @OnClick(R.id.iv_close)
    void onClose(){
        finish();
    }

    @OnClick(R.id.tv_flashlight)
    void onFlashlight() {
        if (mFlashing) {
            mFlashing = false;
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            mFlashing = true;
            // 关闪光灯
            CameraManager.get().offLight();
        }
    }


    public static void start(Context context) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, ScanUnlockActivity.class));
    }
    public static void start(Context context,boolean isChange) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, ScanUnlockActivity.class)
                .putExtra("isChange",isChange));
    }


    @Override
    public ScanPresenter createPresenter() {
        return new ScanPresenter(this);
    }

//    @Nullable
//    @Override
//    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
//        return null;
//                /*builder.setBackButton(0).addLeftImage(R.mipmap.logo_icon,null)
//                .addLeftImage(R.mipmap.little_yellow_donkey_white_icon,null)
//                .addRightImage(R.mipmap.close_white_icon, v -> {
//                        finish();
//                });*/
//    }

    @Override
    protected Object getLayoutId() {
        StatusBarUtil.setTransparent(getActivity());
        return R.layout.activity_scan_unlock;
    }

    /*protected void preMethod() {
        //去掉标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .init();

    }*/

    @Override
    protected void initialize() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        StatusBarUtil.setStatusBarPadding(getContext(),logoToolbar);
        //权限初始化
        initPermission();
        //扫描动画初始化
        initScanerAnimation();
        //初始化 CameraManager
        CameraManager.init(getContext());

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        isChange = getIntent().getBooleanExtra("isChange",false);

        initLocation();
    }

    private void initPermission() {
        //请求Camera权限 与 文件读写 权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void initScanerAnimation() {
        RxAnimationTool.ScaleUpDowm(mIvScanLine);
    }

    @Override
    public int getCropWidth() {
        return mCropWidth;
    }

    @Override
    public void setCropWidth(int cropWidth) {
        mCropWidth = cropWidth;
        CameraManager.FRAME_WIDTH = mCropWidth;
    }

    @Override
    public int getCropHeight() {
        return mCropHeight;
    }

    @Override
    public void setCropHeight(int cropHeight) {
        this.mCropHeight = cropHeight;
        CameraManager.FRAME_HEIGHT = mCropHeight;
    }


    @Override
    public void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            Point point = CameraManager.get().getCameraResolution();
            AtomicInteger width = new AtomicInteger(point.y);
            AtomicInteger height = new AtomicInteger(point.x);
            int cropWidth = mScanLayout.getWidth() * width.get() / mContainer.getWidth();
            int cropHeight = mScanLayout.getHeight() * height.get() / mContainer.getHeight();
            setCropWidth(cropWidth);
            setCropHeight(cropHeight);
        } catch (IOException | RuntimeException ioe) {
            return;
        }
        if (handler == null) {
            handler = new ScanHandler(ScanUnlockActivity.this);
        }
    }

    private void initLocation() {
        locationManager = TencentLocationManager.getInstance(getContext());
        locationRequest = TencentLocationRequest.create();
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = mScanPreview.getHolder();
        if (hasSurface) {
            //Camera初始化
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (!hasSurface) {
                        hasSurface = true;
                        initCamera(holder);
                    }
                }
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    hasSurface = false;

                }
            });

            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
//            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        mScanerListener = null;
        super.onDestroy();
    }

    @Override
    public void handleDecode(Result result) {
        inactivityTimer.onActivity();
        //扫描成功之后的振动与声音提示
        RxBeepTool.playBeep(getActivity(), vibrate);
        if (result.getText().contains("code=")){
            code = result.getText().substring(result.getText().indexOf("=")+1);
        }else {
            code = result.getText();
        }
        Log.v("二维码/条形码 扫描结果", code);
        if (mScanerListener == null) {
            locationManager.requestLocationUpdates(locationRequest, this);
        } else {
            mScanerListener.onSuccess("From to Camera", result);
        }
    }

    @Override
    public void unlockSuccess(UnlockBean bean) {
        int type = bean.getTpye();
        if (type == 0){
            WaitUnlockActivity.start(getContext(), code);
        }else if (type == 1){
            DiaLogUtils.showTipDialog(getContext(), "系统提示您已被加入黑名单"
                    , "用车需缴纳押金"
                    , "取消"
                    , "去支付"
                    , new PromptDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                            if (isPositiveClick) {
                                PayDepositActivity.start(getContext(),"0.1");
                                finish();
                            }else {
                                if (handler != null) {
                                    // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
                                    handler.sendEmptyMessage(R.id.restart_preview);
                                }
                            }
                        }
                    });
        }else if(type == 2){
            DiaLogUtils.showSureDialog(getContext(), "此车电量已低于10%",
                    "请使用其他电动车", "我知道了", (dialog) -> {
                        if (handler != null) {
                            // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
                            handler.sendEmptyMessage(R.id.restart_preview);
                        }
                    });
        }else if (type == 3){
            DiaLogUtils.showSureDialog(getContext(), "此车已为上一位用户保留",
                    "请使用其他电动车", "我知道了", (dialog) -> {
                        if (handler != null) {
                            // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
                            handler.sendEmptyMessage(R.id.restart_preview);
                        }
                    });
        }
    }

    /*@Override
    public void unlockFail() {
//        EventBus.getDefault().post(new PositionEvent(2));
        MainActivity.startUseingVehicle(getContext(),true);
        finish();
    }
*/

    @Override
    public void showError(Throwable t) {
        super.showError(t);
        if (t.getMessage().equals("您已经在用车中")){
            MainActivity.start(getContext(),true,true);
            finish();
        }
    }

    @Override
    public void transVehicleSuccess() {
        getPresenter().unlock(code);
    }
    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        longitude = tencentLocation.getLongitude();
        latitude = tencentLocation.getLatitude();
        locationManager.removeUpdates(this);

        if (isChange) {
            getPresenter().transVehicle(code,String.valueOf(longitude),String.valueOf(latitude));
        }else {
            getPresenter().unlock(code);
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

}
