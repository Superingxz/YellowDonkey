package com.ruanjie.donkey.ui.scanner;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.ui.scanner.contract.ExScanContract;
import com.ruanjie.donkey.ui.scanner.contract.ScanContract;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.scanner
 * 文件名:   DecodeHandler
 * 创建者:    QJM
 * 创建时间: 2019/8/7 18:46
 * 描述:     TODO
 */

final class ExDecodeHandler extends Handler{

    private ExScanContract.View view;

    ExDecodeHandler(ExScanContract.View view) {
        this.view = view;
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what == R.id.decode) {
            view.decode((byte[]) message.obj, message.arg1, message.arg2);
        } else if (message.what == R.id.quit) {
            Looper.myLooper().quit();
        }
    }

}
