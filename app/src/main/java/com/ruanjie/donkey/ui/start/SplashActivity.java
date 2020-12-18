package com.ruanjie.donkey.ui.start;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.ui.main.MainActivity;
import com.ruanjie.donkey.utils.SPManager;
import com.softgarden.baselibrary.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SplashActivity extends BaseActivity {

    public static void start(Context context) {
//	    starter.putExtra( );
        context.startActivity(new Intent(context, SplashActivity.class));
    }

    @Override
    protected Object getLayoutId() {
//        StatusBarUtil.setTransparent(getActivity());
        return R.layout.activity_splash;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initialize() {
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(aLong -> {
                    boolean isFirstLaunch = SPManager.isFirstLaunch();
                    if (isFirstLaunch) {
                        MainActivity.start(getContext());
                    } else {
                        MainActivity.start(getContext());
                    }
                    finish();
                });
    }

    @Override
    protected void onDestroy() {
        getWindow().setBackgroundDrawable(null);//释放资源
        super.onDestroy();
    }
}
