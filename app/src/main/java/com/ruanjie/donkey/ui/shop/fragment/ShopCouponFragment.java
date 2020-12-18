package com.ruanjie.donkey.ui.shop.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.ShopCouponsAdapter;
import com.ruanjie.donkey.adapter.ShopOrderAdapter;
import com.ruanjie.donkey.bean.ShopCouponBean;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.ruanjie.donkey.ui.shop.ShopAppraiceActivity;
import com.ruanjie.donkey.ui.shop.contract.ShopCouponContract;
import com.ruanjie.donkey.ui.shop.presenter.ShopCouponPresenter;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.RefreshFragment;
import com.softgarden.baselibrary.dialog.PromptDialog;

import java.util.List;

public class ShopCouponFragment extends RefreshFragment<ShopCouponPresenter>
        implements ShopCouponContract.View {
    int mWhich;
    ShopCouponsAdapter mAdapter;
    private String image1 = "http://img5.imgtn.bdimg.com/it/u=1312342535,3076558336&fm=26&gp=0.jpg";

    public static ShopCouponFragment newInstance(int which) {
        Bundle args = new Bundle();
        args.putInt("which", which);
        ShopCouponFragment fragment = new ShopCouponFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_shop_order;
    }

    @Override
    protected void initEventAndData() {
        mWhich = getArguments().getInt("which", 0);
        initRecyclerView();
        initRefreshLayout();

        mAdapter = new ShopCouponsAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        //下面2句话开启上拉加载
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ShopOrderBean mBean = mAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tvBtn1:
                        if (mBean.getStatus() == 1) {//核验码
                            DiaLogUtils.showQrCodeDialog(getActivity(), createBitmap(mBean.getTicket()), mBean.getTicket());

                        } else {//删除订单
                            DiaLogUtils.showTipDialog(getContext(), ""
                                    , "确定删除该订单吗？"
                                    , "取消", "确定", new PromptDialog.OnButtonClickListener() {
                                        @Override
                                        public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                            if (isPositiveClick) {
                                                getPresenter().deleteOrder(mBean.getId());
                                            }
                                        }
                                    });
                        }
                        break;
                    case R.id.tvBtn2:
                        if (mBean.getStatus() == 1) {//确认使用
                            DiaLogUtils.showTipDialog(getContext(), ""
                                    , "确认使用吗？"
                                    , "取消", "确定", new PromptDialog.OnButtonClickListener() {
                                        @Override
                                        public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                            if (isPositiveClick) {
                                                getPresenter().submitOrder(mBean.getId());
                                            }
                                        }
                                    });
                        }
                        break;
                }

            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DiaLogUtils.showTipDialog(getContext()
                        , "注意事项"
                        , mAdapter.getData().get(position).getGoodsList().get(0).getContent()
                        , "确定", "", new PromptDialog.OnButtonClickListener() {
                            @Override
                            public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                if (isPositiveClick) {
                                    dialog.dismiss();
                                }
                            }
                        });
            }
        });
    }
    public static Bitmap createBitmap(String str){
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e){
            e.printStackTrace();
        } catch (IllegalArgumentException iae){ // ?
            return null;
        }
        return bitmap;
    }
    @Override
    protected void lazyLoad() {
        getDatas();
    }

    private void getDatas() {
        getPresenter().getCouponList(2, mWhich == 0 ? 1 :2
                , mPage, PAGE_COUNT);
    }

    @Override
    public ShopCouponPresenter createPresenter() {
        return new ShopCouponPresenter();
    }

    @Override
    public void getCouponList(List<ShopOrderBean> data) {
        if (mPage == 1) {
            setLoadData(mAdapter, data);
        } else {
            setLoadMore(mAdapter, data);
        }
    }

    @Override
    public void submitOrder(String data) {
        EventBusUtils.post(MEventBus.SUBMIT_ORDER_SUCCESS, null);
    }

    @Override
    public void deleteOrder(String data) {
        EventBusUtils.post(MEventBus.DELETE_ORDER_SUCCESS, null);
    }

    @Override
    public void mEventBus(EventBusBean busBean) {
        switch (busBean.getCode()) {
            case MEventBus.SUBMIT_ORDER_SUCCESS:
                onRefresh();
                break;
            case MEventBus.DELETE_ORDER_SUCCESS:
                if (mWhich == 1) onRefresh();
                break;

        }
    }
}
