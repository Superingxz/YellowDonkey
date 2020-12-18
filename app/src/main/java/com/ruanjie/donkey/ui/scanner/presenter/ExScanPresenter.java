package com.ruanjie.donkey.ui.scanner.presenter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.ui.scanner.ExScanActivity;
import com.ruanjie.donkey.ui.scanner.contract.ExScanContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.Hashtable;
import java.util.Vector;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.scanner.presenter
 * 文件名:   ScanPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/8 11:52
 * 描述:     TODO
 */
public class ExScanPresenter extends BasePresenter implements ExScanContract.Model{

    private final ExScanContract.View view;

    private MultiFormatReader multiFormatReader;

    public ExScanPresenter(ExScanContract.View view) {
        this.view = view;
    }

    @Override
    public MultiFormatReader initDecode() {
        multiFormatReader = new MultiFormatReader();
        // 解码的参数
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(2);
        // 可以解析的编码类型
        Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<BarcodeFormat>();

            Vector<BarcodeFormat> PRODUCT_FORMATS = new Vector<BarcodeFormat>(5);
            PRODUCT_FORMATS.add(BarcodeFormat.UPC_A);
            PRODUCT_FORMATS.add(BarcodeFormat.UPC_E);
            PRODUCT_FORMATS.add(BarcodeFormat.EAN_13);
            PRODUCT_FORMATS.add(BarcodeFormat.EAN_8);
            // PRODUCT_FORMATS.add(BarcodeFormat.RSS14);
            Vector<BarcodeFormat> ONE_D_FORMATS = new Vector<BarcodeFormat>(PRODUCT_FORMATS.size() + 4);
            ONE_D_FORMATS.addAll(PRODUCT_FORMATS);
            ONE_D_FORMATS.add(BarcodeFormat.CODE_39);
            ONE_D_FORMATS.add(BarcodeFormat.CODE_93);
            ONE_D_FORMATS.add(BarcodeFormat.CODE_128);
            ONE_D_FORMATS.add(BarcodeFormat.ITF);
            Vector<BarcodeFormat> QR_CODE_FORMATS = new Vector<BarcodeFormat>(1);
            QR_CODE_FORMATS.add(BarcodeFormat.QR_CODE);
            Vector<BarcodeFormat> DATA_MATRIX_FORMATS = new Vector<BarcodeFormat>(1);
            DATA_MATRIX_FORMATS.add(BarcodeFormat.DATA_MATRIX);

            // 这里设置可扫描的类型，我这里选择了都支持
            decodeFormats.addAll(ONE_D_FORMATS);
            decodeFormats.addAll(QR_CODE_FORMATS);
            decodeFormats.addAll(DATA_MATRIX_FORMATS);
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        multiFormatReader.setHints(hints);

        return multiFormatReader;
    }

    @Override
    public void scanExChange(String code) {
        RetrofitClient.getService()
                .scanExChange(code)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((ExScanActivity) mView).scanExChange(data);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
    }


}
