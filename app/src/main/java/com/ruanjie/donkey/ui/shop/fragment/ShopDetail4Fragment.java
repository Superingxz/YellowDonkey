package com.ruanjie.donkey.ui.shop.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.AppriceImageAdapter;
import com.ruanjie.donkey.bean.ShopDetail4Bean;
import com.ruanjie.donkey.ui.shop.ShowAddressActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.GridSpacingItemDecoration;
import com.softgarden.baselibrary.base.BaseLazyFragment;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.softgarden.baselibrary.utils.DisplayUtil;
import com.vondear.rxtool.RxDeviceTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopDetail4Fragment extends BaseLazyFragment {

    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.ivPhone)
    ImageView ivPhone;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    AppriceImageAdapter mAdapter;

    ShopDetail4Bean shopDetail4Bean;

    public static ShopDetail4Fragment newInstance(String jsonStr) {
        Bundle args = new Bundle();
        args.putString("jsonStr", jsonStr);

        ShopDetail4Fragment fragment = new ShopDetail4Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_shop_detail_4;
    }

    @Override
    protected void initEventAndData() {
        String jsonStr = getArguments().getString("jsonStr");
        shopDetail4Bean = new Gson().fromJson(jsonStr, ShopDetail4Bean.class);
        if (shopDetail4Bean != null) {
            tvAddress.setText(shopDetail4Bean.getAddress());
            tvDesc.setText(shopDetail4Bean.getIntro());

            mAdapter = new AppriceImageAdapter(getContext());
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3
                    , DisplayUtil.dip2px(getContext(), 10)
                    , true));
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setNewData(shopDetail4Bean.getImages());
        }

    }

    @Override
    protected void lazyLoad() {

    }


    @OnClick({R.id.ivPhone,R.id.tvAddress})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPhone:
                if (shopDetail4Bean != null) {
                    DiaLogUtils.showTipDialog(getContext(), "即将拨通客服电话"
                            , shopDetail4Bean.getTel()
                            , "取消"
                            , "立即拨打"
                            , new PromptDialog.OnButtonClickListener() {
                                @Override
                                public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                    if (isPositiveClick) {
                                        RxDeviceTool.callPhone(getActivity(), shopDetail4Bean.getTel());
                                    }
                                }
                            });
                }

                break;
            case R.id.tvAddress:
                ShowAddressActivity.start(getContext(),shopDetail4Bean.getLat(),shopDetail4Bean.getLng());
                break;
        }
    }

}
