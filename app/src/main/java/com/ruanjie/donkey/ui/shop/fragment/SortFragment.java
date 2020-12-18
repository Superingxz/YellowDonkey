package com.ruanjie.donkey.ui.shop.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.noober.background.view.BLEditText;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.ShopBottomAdapter;
import com.ruanjie.donkey.adapter.ShopRightAdapter;
import com.ruanjie.donkey.adapter.ShopSortTypeAdapter;
import com.ruanjie.donkey.adapter.ShopTopAdapter;
import com.ruanjie.donkey.bean.ShopBean;
import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.bean.ShopSortRightBean;
import com.ruanjie.donkey.bean.ShopSortRightBean2;
import com.ruanjie.donkey.bean.ShopSortTypeBean;
import com.ruanjie.donkey.bean.ShopsBean;
import com.ruanjie.donkey.ui.shop.ShopDetailActivity;
import com.ruanjie.donkey.ui.shop.contract.SortContract;
import com.ruanjie.donkey.ui.shop.presenter.SortPresenter;
import com.ruanjie.donkey.utils.GridSpacingItemDecoration;
import com.softgarden.baselibrary.base.RefreshFragment;
import com.softgarden.baselibrary.utils.AppUtil;
import com.softgarden.baselibrary.utils.DisplayUtil;
import com.softgarden.baselibrary.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SortFragment extends RefreshFragment<SortPresenter>
        implements SortContract.View {

    @BindView(R.id.etSearch)
    BLEditText etSearch;
    @BindView(R.id.mTypeRecycler)
    RecyclerView mTypeRecycler;
    @BindView(R.id.tvRightType1)
    BLTextView tvRightType1;
    @BindView(R.id.tvRightType2)
    BLTextView tvRightType2;
    @BindView(R.id.tvRightType3)
    BLTextView tvRightType3;
    @BindView(R.id.mRightRecycler)
    RecyclerView mRightRecycler;
    int mLeftType;
    ShopSortTypeAdapter mTypeAdapter;
    @BindView(R.id.mTopRecycler)
    RecyclerView mTopRecycler;
    @BindView(R.id.mBootomRecycler)
    RecyclerView mBottomRecycler;
    @BindView(R.id.ivArrow)
    ImageView ivArrow;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
    @BindView(R.id.llTop)
    LinearLayout llTop;
    ShopTopAdapter mTopAdapter;
    ShopBottomAdapter mBottomAdapter;
    ShopRightAdapter mAdapter;

    public static SortFragment newInstance() {
        Bundle args = new Bundle();

        SortFragment fragment = new SortFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_shop_sort;
    }

    @Override
    protected void initEventAndData() {
        mTypeAdapter = new ShopSortTypeAdapter(getContext());
        mTypeRecycler.setAdapter(mTypeAdapter);
        mTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mLeftType = position;

                int mPosi = mTypeAdapter.getmSelectPosi();
                if (mPosi != position) {
                    mTypeAdapter.setmSelectPosi(position);
                    mTypeAdapter.notifyDataSetChanged();

                    //更新右边列表数据
                    ShopSortBean shopSortBean = mTypeAdapter.getData().get(position);
                    type1 = shopSortBean.getId();
                    mMiddleSelectPosi = 0;
                    etSearch.setText("");
                    initRightTopDatas(shopSortBean.get_child());
                }

            }
        });

        mTopAdapter = new ShopTopAdapter(getContext());
        mTopRecycler.addItemDecoration(new GridSpacingItemDecoration(4
                , DisplayUtil.dip2px(getContext(), 10)
                , true));
        mTopRecycler.setAdapter(mTopAdapter);

        mTopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int mPosi = mTopAdapter.getmSelectPosi();
                if (mPosi != position) {
                    setTopSelect(position);
                    setBottomSelect(position);
                    setBottomVisible(false);

                    //加载数据
                    type1 = mTopAdapter.getData().get(position).getId();
                    getShopsDatas();
                }
            }
        });

        mBottomAdapter = new ShopBottomAdapter(getContext());
        mBottomRecycler.addItemDecoration(new GridSpacingItemDecoration(3
                , DisplayUtil.dip2px(getContext(), 10)
                , true));
        mBottomRecycler.setAdapter(mBottomAdapter);

        mBottomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int mPosi = mBottomAdapter.getmSelectPosi();
                if (mPosi != position) {
                    setTopSelect(position);
                    setBottomSelect(position);
                    setBottomVisible(false);
                    //加载数据

                    type1 = mTopAdapter.getData().get(position).getId();
                    getShopsDatas();
                }
            }
        });

        mAdapter = new ShopRightAdapter(getContext());
        mRightRecycler.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopsBean shopsBean = mAdapter.getData().get(position);
                ShopDetailActivity.start(getContext(), shopsBean.getId());
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //搜索
                    AppUtil.hideSoftKeyboard(etSearch);

                    getShopsDatas();
                }
                return true;
            }
        });

        getPresenter().getShopSorts(0);
    }

    @Override
    protected void lazyLoad() {

    }


    @OnClick({R.id.ivArrow, R.id.llBottom, R.id.tvRightType1, R.id.tvRightType2, R.id.tvRightType3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivArrow:
                setBottomVisible(!mArrowSelect);
                break;
            case R.id.tvRightType1:
                setMiddleSelect(0);
                break;
            case R.id.tvRightType2:
                setMiddleSelect(1);
                break;
            case R.id.tvRightType3:
                setMiddleSelect(2);
                break;
        }
    }

    int mTopSelectPosi;//topData
    boolean mArrowSelect;
    int mMiddleSelectPosi;//综合 销量 好评
    int mBottomSelectPosi;//bottomData
    int type1;

    public void setTopSelect(int which) {
        mTopSelectPosi = which;
        mTopAdapter.setmSelectPosi(which);
        mTopAdapter.notifyDataSetChanged();
    }


    public void setMiddleSelect(int which) {
        mMiddleSelectPosi = which;

        tvRightType1.setSelected(which == 0 ? true : false);
        tvRightType2.setSelected(which == 1 ? true : false);
        tvRightType3.setSelected(which == 2 ? true : false);

        //加载数据
        getShopsDatas();

    }

    public void setTopVisible(boolean isShow) {
        llTop.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setBottomVisible(boolean isShow) {
        llBottom.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mArrowSelect = isShow;
    }

    public void setBottomSelect(int which) {
        mBottomSelectPosi = which;
        mBottomAdapter.setmSelectPosi(which);
        mBottomAdapter.notifyDataSetChanged();
    }

    @Override
    public SortPresenter createPresenter() {
        return new SortPresenter();
    }

    @Override
    public void getShopSorts(List<ShopSortBean> data) {
        initViews(data);
    }

    @Override
    public void getShopList(List<ShopsBean> data) {
        mAdapter.setNewData(data);
    }

    private void initViews(List<ShopSortBean> data) {
        //左边分类数据
        initLeftDatas(data);
    }

    private void initLeftDatas(List<ShopSortBean> data) {

        if (data != null && data.size() > 0) {

            mTypeAdapter.setNewData(data);

            //刷新右边top分类数据
            List<ShopSortBean.ChildBean> tempDatas = data.get(0).get_child();
            type1 = data.get(0).getId();
            initRightTopDatas(tempDatas);
        }
    }

    public void initRightTopDatas(List<ShopSortBean.ChildBean> data) {
        //先初始化
        setTopSelect(-1);
        setBottomVisible(false);
        setBottomSelect(-1);
        setMiddleSelect(0);

        //
        if (data == null || data.size() == 0) {
            setTopVisible(false);
        } else {
            setTopVisible(true);
            if (data.size() > 4) {
                mTopAdapter.setNewData(data.subList(0, 4));
            } else {
                mTopAdapter.setNewData(data);
            }
        }

        mBottomAdapter.setNewData(data);

        //加载商店数据
        getShopsDatas();
    }

    public void getShopsDatas() {
        String latitudeStr = (String) SPUtil.get("latitude", "");
        String[] split = latitudeStr.split(",");
        if (split != null && split.length == 2) {
            String trim = etSearch.getText().toString().trim();
            getPresenter().getShopList(split[1], split[0], trim, type1, mMiddleSelectPosi, 1, 10000);
        }

    }
}
