package com.ruanjie.donkey.ui.drawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.app.Constants;
import com.ruanjie.donkey.bean.AuthResult;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.PriceBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.ruanjie.donkey.ui.billing.UseTimingActivity;
import com.ruanjie.donkey.ui.drawer.contract.PayDepositContract;
import com.ruanjie.donkey.ui.drawer.presenter.PayDepositPresenter;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxfeature.module.alipay.PayResult;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PayDepositActivity extends ToolbarActivity<PayDepositPresenter>
        implements PayDepositContract.View {

    LoginBean mLoginBean;
    @BindView(R.id.tvDeposit)
    TextView tvDeposit;
    @BindView(R.id.vWechat)
    View vWechat;
    @BindView(R.id.llWechat)
    LinearLayout llWechat;
    @BindView(R.id.vAli)
    View vAli;
    @BindView(R.id.llAli)
    LinearLayout llAli;
    @BindView(R.id.vBalance)
    View vBalance;
    @BindView(R.id.llBalance)
    LinearLayout llBalance;
    /*@BindView(R.id.vBank)
    View vBank;*/
    /*@BindView(R.id.llBank)
    LinearLayout llBank;*/
    @BindView(R.id.tvPay)
    TextView tvPay;
    String mDeposit;

    public static void start(Context context, String depositStr) {
        Intent starter = new Intent(context, PayDepositActivity.class);
        starter.putExtra("depositStr", depositStr);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("押金交纳")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_pay_deposit;
    }

    @Override
    protected void initialize() {
//        mDeposit = getIntent().getStringExtra("depositStr");

//        tvDeposit.setText(mDeposit);
        getPresenter().payPrice("deposit");
        setSelectPay(1);

    }

    int mSelectPay = 1;

    private void setSelectPay(int select) {
        mSelectPay = select;
        vWechat.setSelected(mSelectPay == 1 ? true : false);
        vAli.setSelected(mSelectPay == 2 ? true : false);
        vBalance.setSelected(mSelectPay == 3 ? true : false);
//        vBank.setSelected(mSelectPay == 4 ? true : false);
    }

    @OnClick({R.id.llWechat, R.id.llAli, /*R.id.llBank
            ,*/ R.id.llBalance, R.id.tvPay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llWechat:
                setSelectPay(1);
                break;
            case R.id.llAli:
                setSelectPay(2);
                break;
            case R.id.llBalance:
                setSelectPay(3);
                break;
           /* case R.id.llBank:
                setSelectPay(4);
                break;*/
            case R.id.tvPay:
                getPresenter().payDeposit(mSelectPay == 1 ? 1
                        : mSelectPay == 2 ? 2
                        : mSelectPay == 3 ? 9
                        : 3
                );
                break;
        }
    }

    @Override
    public PayDepositPresenter createPresenter() {
        return new PayDepositPresenter();
    }

    //设置金额
    @Override
    public void payPrice(PriceBean data) {
        tvDeposit.setText(data.getContent());
    }

    @Override
    public void payDeposit(RechargeBean data) {
        switch (data.getPay_type()) {
            case "1"://微信
                wechatPay(data);
                break;
            case "2"://支付宝
                aLiPay(data);
                break;
            /*case "3"://银联
                break;*/
            case "9"://余额支付
                EventBusUtils.post(MEventBus.REFRESH_WALLET, null);
                finish();
                break;
        }
    }

    private void wechatPay(RechargeBean data) {
        LogUtils.logBean("wechatPay", data);
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APPID);
        api.registerApp(Constants.WEIXIN_APPID);
        PayReq request = new PayReq();
        request.appId = data.getWechat().getAppid();
        request.partnerId = data.getWechat().getPartnerid();
        request.prepayId = data.getWechat().getPrepayid();
        request.packageValue = data.getWechat().getPackageX();
        request.nonceStr = data.getWechat().getNoncestr();
        request.timeStamp = data.getWechat().getTimestamp() + "";
        request.sign = data.getWechat().getSign();

        api.sendReq(request);
    }

    private void aLiPay(RechargeBean data) {
        String orderInfo = data.getAlipay();   // 订单信息
        if (TextUtils.isEmpty(orderInfo)) {
            ToastUtil.s("支付出错");
        }
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        ToastUtil.s("支付成功");
                        EventBusUtils.post(MEventBus.REFRESH_WALLET, null);
                        finish();
//                        showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.s("支付失败");

//                        showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtil.s("Authentication success");
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtil.s("Authentication failed");
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onEventBus(EventBusBean busBean) {
        super.onEventBus(busBean);
        switch (busBean.getCode()) {
            case MEventBus.PAY_SUCCESS_WECHAT:
                UseTimingActivity.start(this);
                finish();
                break;
        }
    }
}
