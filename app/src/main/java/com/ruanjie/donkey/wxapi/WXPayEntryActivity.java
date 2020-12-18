package com.ruanjie.donkey.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.app.Constants;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);

        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtils.i(TAG, "errCode = " + resp.errCode
                + "\nerrStr = " + resp.errStr
                + "\nopenId = " + resp.openId
                + "\ntransaction = " + resp.transaction
        );

        if (resp.errCode == 0) {//成功
            EventBusUtils.post(MEventBus.PAY_SUCCESS_WECHAT, null);
//            ToastUtil.s("支付成功");
            finish();
        } else {
            finish();
        }
    }
}