package com.ruanjie.donkey.ui.shop.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.ShopCouponAdapter;
import com.ruanjie.donkey.adapter.ShopTypeAdapter;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.ShopCouponBean;
import com.ruanjie.donkey.ui.drawer.MyCouponsActivity;
import com.ruanjie.donkey.ui.shop.ShopCouponActivity;
import com.ruanjie.donkey.ui.shop.contract.ShopDetail2Contract;
import com.ruanjie.donkey.ui.shop.presenter.ShopDetail2Presenter;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.softgarden.baselibrary.base.BaseLazyFragment;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.softgarden.baselibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopDetail2Fragment extends BaseLazyFragment<ShopDetail2Presenter>
        implements ShopDetail2Contract.View {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    ShopCouponAdapter mCouponAdapter;
    private String image1 = "http://img5.imgtn.bdimg.com/it/u=1312342535,3076558336&fm=26&gp=0.jpg";
    private String image2 = "http://img4.imgtn.bdimg.com/it/u=1981255082,2115613946&fm=26&gp=0.jpg";
    private String image3 = "http://img1.imgtn.bdimg.com/it/u=2425973259,1645965406&fm=26&gp=0.jpg";
    int mShopId;

    public static ShopDetail2Fragment newInstance(int shopId) {
        Bundle args = new Bundle();
        args.putInt("shopId", shopId);

        ShopDetail2Fragment fragment = new ShopDetail2Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_shop_detail_2;
    }

    @Override
    protected void initEventAndData() {
        mShopId = getArguments().getInt("shopId");

        mCouponAdapter = new ShopCouponAdapter(getContext());
        mRecyclerView.setAdapter(mCouponAdapter);
        mCouponAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ShopCouponBean shopCouponBean = mCouponAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tvExChange:
                        DiaLogUtils.showTipDialog(getContext()
                                , ""
                                , "本次兑换需" + shopCouponBean.getScore() + "驴币，确认兑换吗?"
                                , "取消"
                                , "确定"
                                , new PromptDialog.OnButtonClickListener() {
                                    @Override
                                    public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                        if (isPositiveClick) {
                                            getPresenter().exChangeCoupon(shopCouponBean.getId());
                                        }
                                    }
                                });
                        break;
                }
            }
        });

        getPresenter().getCouponList(mShopId);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public ShopDetail2Presenter createPresenter() {
        return new ShopDetail2Presenter();
    }

    @Override
    public void getCouponList(List<ShopCouponBean> data) {
        mCouponAdapter.setNewData(data);

    }

    @Override
    public void exChangeCoupon(BuyGoodsBean data) {
        DiaLogUtils.showTipDialog(getContext()
                , ""
                , "兑换成功，请到我的现金券查看"
                , ""
                , "确定"
                , new PromptDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                        if (isPositiveClick) {
                            ShopCouponActivity.start(getContext(),0);

                        }
                    }
                });
    }
}
