package com.ruanjie.donkey.ui.unlock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.ruanjie.donkey.ui.main.MainActivity;
import com.ruanjie.donkey.ui.unlock.contract.WaitUnlockContract;
import com.ruanjie.donkey.ui.unlock.presenter.WaitUnlockPresenter;
import com.ruanjie.donkey.utils.TimeUtils;
import com.ruanjie.toolsdk.config.ToolSdk;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.L;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.roundprogressbar.RxRoundProgressBar;

import butterknife.BindView;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.unlock
 * 文件名:   WaitUnlockActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/10 17:18
 * 描述:     TODO
 */
public class WaitUnlockActivity extends ToolbarActivity<WaitUnlockPresenter> implements WaitUnlockContract.View {

    @BindView(R.id.yellow_progress_bar)
    RxRoundProgressBar mYellowProgressBar;
    @BindView(R.id.tv_timer)
    AppCompatTextView tvTimer;

    private int time = 0;
    private int mProgress = 0;
    private int mProgressMax = 100;
    private Thread loadingThread;
    private TimeRunnable mRunnable;
    private String code;

    @SuppressLint("HandlerLeak")
    Handler progressBarHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mYellowProgressBar.setProgress(mProgress);
        }
    };



    public static void start(Context context) {
        context.startActivity(new Intent(context, WaitUnlockActivity.class));
    }
    public static void start(Context context,String code) {
        context.startActivity(new Intent(context, WaitUnlockActivity.class)
        .putExtra("code",code));
    }


    @Override
    public WaitUnlockPresenter createPresenter() {
        return new WaitUnlockPresenter(this);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle(R.string.wait_unlock)
                .setTitleTextSize(18);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_wait;
    }

    @Override
    protected void initialize() {
        code = getIntent().getStringExtra("code");
        if (mRunnable == null) {
            mRunnable = new TimeRunnable();
            ToolSdk.getHandler().postDelayed(mRunnable, 0);
        }
    }

    @Override
    public void showProgress() {
        mYellowProgressBar.setMax(mProgressMax);
        loadingThread = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    while (!loadingThread.isInterrupted()) {
                       /* while (mProgress < mYellowProgressBar.getMax()) {
                            mProgress += mYellowProgressBar.getMax() * 0.01;
                            if (mProgress < mYellowProgressBar.getMax()) {
                                Message message = new Message();
                                message.what = 101;
                                progressBarHandler.sendMessage(message);
                            }
                            Thread.sleep(10);
                        }*/
                        while (mProgress < mProgressMax) {
                            mProgress += mProgressMax * 0.01;
//                            mProgress++;
                            Message message = new Message();
                            message.what = 101;
                            progressBarHandler.sendMessage(message);
                            Thread.sleep(1);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        loadingThread.start();
    }

    @Override
    public void unlockSuccess(VehicleDetailBean bean) {
            RxToast.success("解锁成功");
//        EventBus.getDefault().post(new PositionEvent(bean.getStatus()));
        if (bean.getStatus() == 2) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("status", true);
            RxActivityTool.skipActivityAndFinishAll(getContext(),MainActivity.class,bundle);
          /*  MainActivity.start(getContext(), true, true);
            finish();*/
        }
        }

    @Override
    public void unlockFail() {
        RxActivityTool.finishActivity();
    }

    private class TimeRunnable implements Runnable {
        @Override
        public void run() {
            tvTimer.setText(TimeUtils.formatTimer(time++));
            ToolSdk.getHandler().postDelayed(this, 1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.e("解锁结果",code);
        getPresenter().unlock(code);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ToolSdk.getHandler().removeCallbacks(mRunnable);
        progressBarHandler.removeCallbacks(loadingThread);
    }

    @Override
    protected void onDestroy() {
        ToolSdk.getHandler().removeCallbacks(mRunnable);
        progressBarHandler.removeCallbacks(loadingThread);
        super.onDestroy();
    }
}
