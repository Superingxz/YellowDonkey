package com.ruanjie.donkey.ui.scanner;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.CountDownLatch;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.scanner
 * 文件名:   DecodeThread
 * 创建者:    QJM
 * 创建时间: 2019/8/7 18:45
 * 描述:     解码线程
 * */

final class DecodeThread extends Thread{

    private final CountDownLatch handlerInitLatch;
    private Handler handler;
    ScanUnlockActivity activity;

    DecodeThread(ScanUnlockActivity activity) {
        this.activity = activity;
        handlerInitLatch = new CountDownLatch(1);
    }

    Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(activity);
        handlerInitLatch.countDown();
        Looper.loop();
    }
}
