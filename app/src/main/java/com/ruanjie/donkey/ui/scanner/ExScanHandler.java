package com.ruanjie.donkey.ui.scanner;

import android.os.Handler;
import android.os.Message;

import com.google.zxing.Result;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.ui.scanner.contract.ExScanContract;
import com.ruanjie.toolsdk.ui.scanner.manager.CameraManager;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.scanner
 * 文件名:   ScanHandler
 * 创建者:    QJM
 * 创建时间: 2019/8/7 18:35
 * 描述:     TODO
 */


final class ExScanHandler extends Handler {

    ExDecodeThread decodeThread = null;
    private State state;
    private ExScanContract.View view;

    public ExScanHandler(ExScanContract.View view) {
        this.view = view;
        decodeThread = new ExDecodeThread(view);
        decodeThread.start();
        state = State.SUCCESS;
        CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what == R.id.auto_focus) {
            if (state == State.PREVIEW) {
                CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
            }
        } else if (message.what == R.id.restart_preview) {
            restartPreviewAndDecode();
        } else if (message.what == R.id.decode_succeeded) {
            state = State.SUCCESS;
            view.handleDecode((Result) message.obj);// 解析成功，回调
        } else if (message.what == R.id.decode_failed) {
            state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
        }
    }

    public void quitSynchronously() {
        state = State.DONE;
        decodeThread.interrupt();
        CameraManager.get().stopPreview();
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
        removeMessages(R.id.decode);
        removeMessages(R.id.auto_focus);
    }

    private void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
            CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
        }
    }
    private enum State {
        //预览
        PREVIEW,
        //成功
        SUCCESS,
        //完成
        DONE
    }
}
