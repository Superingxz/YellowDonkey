package com.ruanjie.donkey.ui.scanner.contract;

import android.view.SurfaceHolder;

import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.scanner.contract
 * 文件名:   ScanContract
 * 创建者:    QJM
 * 创建时间: 2019/8/8 11:23
 * 描述:     TODO
 */
public interface ExScanContract {
    interface View extends IBaseDisplay {
        void initScanerAnimation();
        void initCamera(SurfaceHolder surfaceHolder);
        void handleDecode(Result result);
        void decode(byte[] data, int width, int height);
        int getCropWidth();
        int getCropHeight();
        void setCropWidth(int cropWidth);
        void setCropHeight(int cropHeight);
        void scanExChange(String data);

    }

    interface Model extends IBasePresenter {
//        void handleDecode(Result result);
//        void decode(byte[] data, int width, int height);
        MultiFormatReader initDecode();

        void scanExChange(String code);


    }
}
