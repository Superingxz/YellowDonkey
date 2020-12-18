package com.ruanjie.donkey.ui.billing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.allen.library.SuperTextView;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.orhanobut.dialogplus.DialogPlus;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.app.Constants;
import com.ruanjie.donkey.bean.AuthResult;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.ruanjie.donkey.ui.billing.contract.PayArrearsContract;
import com.ruanjie.donkey.ui.billing.presenter.PayArrearsPresenter;
import com.ruanjie.donkey.ui.main.MainActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
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

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.billing
 * 文件名:   PayArrearsActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/16 13:01
 * 描述:     TODO
 */
public class PayArrearsActivity extends ToolbarActivity<PayArrearsPresenter> implements PayArrearsContract.View {

    @BindView(R.id.tv_arrears)
    AppCompatTextView tvArrears;
    @BindView(R.id.v_wechat_pay)
    View vWechatPay;
    @BindView(R.id.wechat_pay_layout)
    LinearLayoutCompat wechatPayLayout;
    @BindView(R.id.v_ali_pay)
    View vAliPay;
    @BindView(R.id.ali_pay_layout)
    LinearLayoutCompat aliPayLayout;
    @BindView(R.id.v_balance)
    View vBalance;
    @BindView(R.id.my_balance_layout)
    LinearLayoutCompat myBalanceLayout;
    /*@BindView(R.id.v_bank_pay)
    View vBank;*/
    /*@BindView(R.id.bank_pay_layout)
    LinearLayoutCompat bankPayLayout;*/
    @BindView(R.id.stv_conpon)
    SuperTextView stvConpon;
    @BindView(R.id.tv_pay)
    AppCompatTextView tvPay;

    private int mSelectPay = 1;
    private String mArrears;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private DialogPlus mCouponsDialog;
    private int mCouponId;

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
    };



    @OnClick(R.id.wechat_pay_layout)
    void onWechatPay(){
        setSelectPay(1);
    }

    @OnClick(R.id.ali_pay_layout)
    void onAliPay(){
        setSelectPay(2);
    }

    @OnClick(R.id.my_balance_layout)
    void onMyBalance(){
        setSelectPay(3);
    }
    /*@OnClick(R.id.bank_pay_layout)
    void onBankPay(){
        setSelectPay(4);
    }*/
    @OnClick(R.id.stv_conpon)
    void onConpon(){
        if (mCouponsDialog == null) {
            getPresenter().getCoupons();
        } else {
            mCouponsDialog.show();
        }
    }

    @OnClick(R.id.tv_pay)
    void onPay(){
        getPresenter().payArrears(mSelectPay == 1 ? 1
                : mSelectPay == 2 ? 2
                : mSelectPay == 3 ? 9
                : 3,mCouponId);
    }

    public static void start(Context context, String arrears) {
        context.startActivity(new Intent(context, PayArrearsActivity.class).putExtra("arrears", arrears));
    }

    @Override
    public PayArrearsPresenter createPresenter() {
        return new PayArrearsPresenter(this);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle(R.string.pay_arrears)
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 2);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_pay_arrears;
    }

    @Override
    protected void initialize() {
        mArrears = getIntent().getStringExtra("arrears");
        tvArrears.setText(mArrears);
        setSelectPay(1);
    }

    private void setSelectPay(int select) {
        mSelectPay = select;
        vWechatPay.setSelected(mSelectPay == 1 ? true : false);
        vAliPay.setSelected(mSelectPay == 2 ? true : false);
        vBalance.setSelected(mSelectPay == 3 ? true : false);
//        vBank.setSelected(mSelectPay == 4 ? true : false);
    }

    @Override
    public void payArrears(RechargeBean data) {
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

    @Override
    public void getCoupons(List<CouponBean> data) {
        mCouponsDialog = DiaLogUtils.showCouponsDialog(getActivity(), data, new DiaLogUtils.OnDialogCouponSelectListener() {
            @Override
            public void onOkClick(String couponMoney, int couponId) {
                mCouponId = couponId;
                stvConpon.setRightString("抵扣￥" + couponMoney);
                mCouponsDialog.dismiss();
            }
        });
        mCouponsDialog.show();
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

    @Override
    public void onEventBus(EventBusBean busBean) {
        super.onEventBus(busBean);
        switch (busBean.getCode()) {
            case MEventBus.PAY_SUCCESS_WECHAT:
                break;
            case MEventBus.REFRESH_WALLET:
                MainActivity.start(getContext(),false,false);
                finish();
                break;
        }
    }
}
