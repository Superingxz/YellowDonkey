package com.ruanjie.donkey.ui.scanner;

import android.os.Handler;
import android.os.Looper;

import com.ruanjie.donkey.ui.scanner.contract.ExScanContract;
import com.ruanjie.donkey.ui.scanner.contract.ScanContract;

import java.util.concurrent.CountDownLatch;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.scanner
 * 文件名:   DecodeThread
 * 创建者:    QJM
 * 创建时间: 2019/8/7 18:45
 * 描述:     TODO*/


final class ExDecodeThread extends Thread{

    private final CountDownLatch handlerInitLatch;
    private Handler handler;
    private ExScanContract.View view;
    ExDecodeThread(ExScanContract.View view) {
        this.view = view;
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
        handler = new ExDecodeHandler(view);
        handlerInitLatch.countDown();
        Looper.loop();
    }
}
