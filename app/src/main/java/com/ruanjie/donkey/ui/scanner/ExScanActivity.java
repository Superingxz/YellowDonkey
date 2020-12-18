package com.ruanjie.donkey.ui.scanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.mirkowu.statusbarutil.StatusBarUtil;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.listener.OnRxScanerListener;
import com.ruanjie.donkey.ui.scanner.contract.ExScanContract;
import com.ruanjie.donkey.ui.scanner.presenter.ExScanPresenter;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.toolsdk.ui.scanner.PlanarYUVLuminanceSource;
import com.ruanjie.toolsdk.ui.scanner.decoding.InactivityTimer;
import com.ruanjie.toolsdk.ui.scanner.manager.CameraManager;
import com.softgarden.baselibrary.base.BaseActivity;
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
public class ExScanActivity extends BaseActivity<ExScanPresenter> implements ExScanContract.View {

    /**
     * 扫描结果监听
     */
    private static OnRxScanerListener mScanerListener;

    private InactivityTimer inactivityTimer;

    /**
     * 扫描处理
     */
    private ExScanHandler handler;


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
    SurfaceView mScanPreview;

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
        Intent starter = new Intent(context, ExScanActivity.class);
        // starter.putExtra(F);
        context.startActivity(starter);
    }

    @Override
    public ExScanPresenter createPresenter() {
        return new ExScanPresenter(this);
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


  /*  protected void preMethod() {
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

        //界面控件初始化
        multiFormatReader = getPresenter().initDecode();
        //权限初始化
        initPermission();
        //扫描动画初始化
        initScanerAnimation();
        //初始化 CameraManager
        CameraManager.init(getActivity());

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

    }

    private void initPermission() {
        //请求Camera权限 与 文件读写 权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
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
    public void scanExChange(String data) {
        finish();
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
            handler = new ExScanHandler(this);
        }
    }


    @Override
    public void showError(Throwable t) {
        super.showError(t);
        finish();
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
            handler.removeCallbacksAndMessages(null);
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
        LogUtils.i("扫描结果", "code = " + code);
        if (mScanerListener == null) {
//            RxToast.success(code);
            getPresenter().scanExChange(code);
        } else {
            mScanerListener.onSuccess("From to Camera", result);
        }
    }

    @Override
    public void decode(byte[] data, int width, int height) {
        long start = System.currentTimeMillis();
        Result rawResult = null;

        //modify here
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotatedData[x * height + height - y - 1] = data[x + y * width];
            }
        }
        // Here we are swapping, that's the difference to #11
        int tmp = width;
        width = height;
        height = tmp;

        PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, width, height);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            rawResult = multiFormatReader.decodeWithState(bitmap);
        } catch (ReaderException e) {
            // continue
        } finally {
            multiFormatReader.reset();
        }

        if (rawResult != null) {
            long end = System.currentTimeMillis();
            Log.d(TAG, "Found barcode (" + (end - start) + " ms):\n" + rawResult.toString());
            Message message = Message.obtain(handler, R.id.decode_succeeded, rawResult);
            Bundle bundle = new Bundle();
            bundle.putParcelable("barcode_bitmap", source.renderCroppedGreyscaleBitmap());
            message.setData(bundle);
            //Log.d(TAG, "Sending decode succeeded message...");
            message.sendToTarget();
        } else {
            Message message = Message.obtain(handler, R.id.decode_failed);
            message.sendToTarget();
        }
    }


}
