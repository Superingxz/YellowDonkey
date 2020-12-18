package com.ruanjie.donkey.ui.drawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.allen.library.SuperTextView;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.orhanobut.dialogplus.DialogPlus;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.app.Constants;
import com.ruanjie.donkey.bean.AuthResult;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.ruanjie.donkey.ui.drawer.contract.RechargeContract;
import com.ruanjie.donkey.ui.drawer.presenter.RechargePresenter;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.dialog.EditDialog;
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

public class RechargeActivity extends ToolbarActivity<RechargePresenter>
        implements RechargeContract.View {

    @BindView(R.id.tv200)
    TextView tv200;
    @BindView(R.id.tv100)
    TextView tv100;
    @BindView(R.id.tv50)
    TextView tv50;
    @BindView(R.id.tv20)
    TextView tv20;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.stvConpon)
    SuperTextView stvConpon;
    @BindView(R.id.vWechat)
    View vWechat;
    @BindView(R.id.llWechat)
    LinearLayout llWechat;
    @BindView(R.id.vAli)
    View vAli;
    @BindView(R.id.llAli)
    LinearLayout llAli;
    /*@BindView(R.id.vBank)
    View vBank;
    @BindView(R.id.llBank)
    LinearLayout llBank;*/
    @BindView(R.id.tvPay)
    TextView tvPay;
    int mSelectMoney = 1;
    int mSelectPay = 1;
    DialogPlus mCouponsDia;
    @BindView(R.id.tvOther)
    TextView tvOther;


    public static void start(Context context) {
        Intent starter = new Intent(context, RechargeActivity.class);
//        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("充值")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initialize() {
        setSelectMoney(1);
        setSelectPay(1);

    }

    private void setSelectPay(int select) {
        mSelectPay = select;
        vWechat.setSelected(mSelectPay == 1 ? true : false);
        vAli.setSelected(mSelectPay == 2 ? true : false);
//        vBank.setSelected(mSelectPay == 3 ? true : false);
    }

    private void setSelectMoney(int select) {
        mSelectMoney = select;
        if (select != 0) {
            mOtherMoney = "其他金额";
            tvOther.setText(mOtherMoney);
        }
        tv200.setSelected(mSelectMoney == 1 ? true : false);
        tv100.setSelected(mSelectMoney == 2 ? true : false);
        tv50.setSelected(mSelectMoney == 3 ? true : false);
        tv20.setSelected(mSelectMoney == 4 ? true : false);
        tv10.setSelected(mSelectMoney == 5 ? true : false);
        tv5.setSelected(mSelectMoney == 6 ? true : false);
        tvOther.setSelected(mSelectMoney == 0 ? true : false);
    }

    String mOtherMoney;

    @OnClick({R.id.tv200, R.id.tv100, R.id.tv50
            , R.id.tv20, R.id.tv10, R.id.tv5
            , R.id.llWechat, R.id.llAli, /*R.id.llBank
            ,*/ R.id.stvConpon, R.id.tvPay, R.id.tvOther})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOther:

                DiaLogUtils.showEditDialog(getContext(), "其他金额", "请输入1~200金额", "取消", "确定", new EditDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(EditDialog dialog, boolean isPositiveClick, String content) {
                        if (isPositiveClick) {
                            if (TextUtils.isEmpty(content)) {
                                ToastUtil.s("请输入金额");
                                return;
                            }
//                            int contentInt = Integer.parseInt(content);

//                            getPresenter().recharge(contentInt + "", mSelectPay, mCouponId);
                            mOtherMoney = Integer.parseInt(content)+"";
                            tvOther.setText("￥" + mOtherMoney);
                            setSelectMoney(0);
                        }
                    }
                });
                break;
            case R.id.stvConpon:
                if (mCouponsDia == null) {
                    getPresenter().getCoupons(3, 0, 0, 1, 1000);
                } else {
                    mCouponsDia.show();
                }
                break;
            case R.id.tv200:
                setSelectMoney(1);
                break;
            case R.id.tv100:
                setSelectMoney(2);
                break;
            case R.id.tv50:
                setSelectMoney(3);
                break;
            case R.id.tv20:
                setSelectMoney(4);
                break;
            case R.id.tv10:
                setSelectMoney(5);
                break;
            case R.id.tv5:
                setSelectMoney(6);
                break;
            case R.id.llWechat:
                setSelectPay(1);
                break;
            case R.id.llAli:
                setSelectPay(2);
                break;
           /* case R.id.llBank:
                setSelectPay(3);
                break;*/
            case R.id.tvPay:
                //判断所选选项是否为空
                if ("其他金额".equals(mOtherMoney)) {
                    getPresenter().recharge(getSelectMonetStr(), mSelectPay, mCouponId);
                } else {
                    getPresenter().recharge(mOtherMoney, mSelectPay, mCouponId);
                }

                break;
        }
    }

    private String getSelectMonetStr() {
        String tempStr = "200";
        tempStr = "" + (mSelectMoney == 1 ? 200
                : mSelectMoney == 2 ? 100
                : mSelectMoney == 3 ? 50
                : mSelectMoney == 4 ? 20
                : mSelectMoney == 5 ? 10
                : 5
        );
//        tempStr = "0.01";
        return tempStr;
    }


    @Override
    public RechargePresenter createPresenter() {
        return new RechargePresenter();
    }

    @Override
    public void recharge(RechargeBean data) {
        switch (data.getPay_type()) {
            case "1"://微信
                wechatPay(data);
                break;
            case "2"://支付宝
                aLiPay(data);
                break;
           /* case "3"://银联
                break;*/
        }
    }

    int mCouponId;

    @Override
    public void getCoupons(List<CouponBean> data) {
        mCouponsDia = DiaLogUtils.showCouponsDialog(getActivity(), data, new DiaLogUtils.OnDialogCouponSelectListener() {
            @Override
            public void onOkClick(String couponMoney, int couponId) {
                mCouponId = couponId;
                stvConpon.setRightString("赠送￥" + couponMoney);
                mCouponsDia.dismiss();
            }
        });
        mCouponsDia.show();
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
                finish();
                break;
        }
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
                        EventBusUtils.post(MEventBus.PAY_SUCCESS, null);
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

}
