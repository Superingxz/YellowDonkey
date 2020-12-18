package com.ruanjie.donkey.ui.shop.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.ui.shop.ShopCoinActivity;
import com.ruanjie.donkey.ui.shop.ShopCouponActivity;
import com.ruanjie.donkey.ui.shop.ShopOrderActivity;
import com.softgarden.baselibrary.base.RefreshFragment;
import com.softgarden.baselibrary.base.ToolbarFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class NullFragment extends ToolbarFragment {


    public static NullFragment newInstance() {
        Bundle args = new Bundle();

        NullFragment fragment = new NullFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_null;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void lazyLoad() {

    }


    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }
}
