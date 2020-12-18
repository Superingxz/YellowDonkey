package com.ruanjie.donkey.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.noober.background.view.BLEditText;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.ShopSearchGridAdapter;
import com.ruanjie.donkey.adapter.ShopSearchLinearAdapter;
import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.bean.ShopsBean;
import com.ruanjie.donkey.ui.shop.contract.ShopSearchContract;
import com.ruanjie.donkey.ui.shop.fragment.NullFragment;
import com.ruanjie.donkey.ui.shop.fragment.SortFragment;
import com.ruanjie.donkey.ui.shop.presenter.ShopSearchPresenter;
import com.ruanjie.donkey.utils.GridSpacingItemDecoration;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.AppUtil;
import com.softgarden.baselibrary.utils.DisplayUtil;
import com.softgarden.baselibrary.utils.SPUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.baselibrary.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ShopSearchActivity extends ToolbarActivity<ShopSearchPresenter>
        implements ShopSearchContract.View {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    ShopSearchGridAdapter mGridAdapter;
    ShopSearchLinearAdapter mLinearAdapter;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivChangeAdapter)
    ImageView ivChangeAdapter;
    @BindView(R.id.mRootView)
    LinearLayout mRootView;
    @BindView(R.id.tvType1)
    BLTextView tvType1;
    @BindView(R.id.tvType2)
    BLTextView tvType2;
    @BindView(R.id.tvType3)
    BLTextView tvType3;
    @BindView(R.id.etContent)
    BLEditText etContent;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.mSlidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    private String image1 = "http://img5.imgtn.bdimg.com/it/u=1312342535,3076558336&fm=26&gp=0.jpg";
    private String image2 = "http://img4.imgtn.bdimg.com/it/u=1981255082,2115613946&fm=26&gp=0.jpg";
    private String image3 = "http://img1.imgtn.bdimg.com/it/u=2425973259,1645965406&fm=26&gp=0.jpg";
    GridLayoutManager mGridManager;
    LinearLayoutManager mLinearManager;
    String mTitle;
    private String[] mSTitles = {};
    boolean isLinear = true;
    SpaceItemDecoration mLinearDecoration;
    GridSpacingItemDecoration mGridDecoration;
    List<ShopSortBean.ChildBean> mChilds;
    int mId;
    private ArrayList<Fragment> mSFragments = new ArrayList<>();
    int mSortType = -1;//0 1 2

    public static void start(Context context, String jsonStr) {
        Intent starter = new Intent(context, ShopSearchActivity.class);
        starter.putExtra("jsonStr", jsonStr);

        context.startActivity(starter);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_shop_search;
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }


    @Override
    protected void initialize() {
        String mJsonStr = getIntent().getStringExtra("jsonStr");
        if (!TextUtils.isEmpty(mJsonStr)) {
            ShopSortBean shopSortBean = new Gson().fromJson(mJsonStr, ShopSortBean.class);
            mTitle = shopSortBean.getName();
            mId = shopSortBean.getId();
            mChilds = shopSortBean.get_child();
            if (mChilds != null && mChilds.size() > 0) {
                mChilds.add(0, new ShopSortBean.ChildBean(shopSortBean.getId()));

                mSlidingTabLayout.setVisibility(View.VISIBLE);
                initTabLayout(mChilds);
            } else {
                mSlidingTabLayout.setVisibility(View.GONE);
            }

        } else {
            mSlidingTabLayout.setVisibility(View.GONE);
        }

        tvTitle.setText(TextUtils.isEmpty(mTitle) ? "商家列表" : mTitle);


        mGridDecoration = new GridSpacingItemDecoration(2
                , DisplayUtil.dip2px(getContext(), 10)
                , true);
        mLinearDecoration = new SpaceItemDecoration(getContext());

        mGridManager = new GridLayoutManager(getContext(), 2);
        mLinearManager = new LinearLayoutManager(getContext());

        mGridAdapter = new ShopSearchGridAdapter(getContext());
        mLinearAdapter = new ShopSearchLinearAdapter(getContext());

//        mRecyclerView.addItemDecoration(mGridDecoration);
//        mRecyclerView.setLayoutManager(mGridManager);
//        mRecyclerView.setAdapter(mGridAdapter);

        mGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopDetailActivity.start(getContext(), mGridAdapter.getData().get(position).getId());
            }
        });
        mLinearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopDetailActivity.start(getContext(), mLinearAdapter.getData().get(position).getId());

            }
        });

        mRecyclerView.addItemDecoration(mLinearDecoration);
        mRecyclerView.setLayoutManager(mLinearManager);
        mRecyclerView.setAdapter(mLinearAdapter);

        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //搜索
                    AppUtil.hideSoftKeyboard(etContent);

                    getDatas();
                }
                return true;
            }
        });


        setSortViews(0);
    }


    private void initTabLayout(List<ShopSortBean.ChildBean> childs) {
        mSTitles = new String[childs.size()];
        for (int i = 0; i < childs.size(); ++i) {
            mSFragments.add(NullFragment.newInstance());
            if (i == 0) {
                mSTitles[i] = "全部";
            } else {
                mSTitles[i] = childs.get(i).getName();
            }
        }
        MSPagerAdapter mSAdapter = new MSPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSAdapter);

        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mId = mChilds.get(position).getId();
                getDatas();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    private class MSPagerAdapter extends FragmentPagerAdapter {

        public MSPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mSFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mSTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mSFragments.get(position);
        }
    }


    private void setAdapterAndDatas() {
        if (isLinear) {
            mRecyclerView.removeItemDecoration(mLinearDecoration);
            mRecyclerView.addItemDecoration(mGridDecoration);
        } else {
            mRecyclerView.removeItemDecoration(mGridDecoration);
            mRecyclerView.addItemDecoration(mLinearDecoration);
        }
        mRecyclerView.setLayoutManager(isLinear ? mGridManager : mLinearManager);
        mRecyclerView.setAdapter(isLinear ? mGridAdapter : mLinearAdapter);

        if (isLinear) {
            List<ShopsBean> datas = mLinearAdapter.getData();
            mGridAdapter.setNewData(datas);
        } else {
            List<ShopsBean> datas = mGridAdapter.getData();
            mLinearAdapter.setNewData(datas);
        }
        isLinear = !isLinear;
    }


    @OnClick({R.id.ivBack, R.id.ivChangeAdapter, R.id.tvType1
            , R.id.tvType2, R.id.tvType3, R.id.ivSearch})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivChangeAdapter:
                setAdapterAndDatas();
                break;
            case R.id.tvType1:
                setSortViews(0);
                break;
            case R.id.tvType2:
                setSortViews(1);
                break;
            case R.id.tvType3:

                setSortViews(2);
                break;
            case R.id.ivSearch:
                //搜索
                AppUtil.hideSoftKeyboard(etContent);

                getDatas();
                break;
        }
    }

    public void setSortViews(int which) {
        if (mSortType == which)
            return;

        //根据关键词和分类获取数据
        mSortType = which;

        tvType1.setSelected(mSortType == 0 ? true : false);
        tvType2.setSelected(mSortType == 1 ? true : false);
        tvType3.setSelected(mSortType == 2 ? true : false);

        getDatas();

    }

    public void getDatas() {
        String latitudeStr = (String) SPUtil.get("latitude", "");
        String[] split = latitudeStr.split(",");
        if (split != null && split.length == 2) {
            String trim = etContent.getText().toString().trim();

            getPresenter().getShopList(split[1], split[0]
                    , trim
                    , mId
                    , mSortType == 0 ? 0
                            : mSortType == 1 ? 1
                            : 2
                    , 1, 10000);
        }

    }

    @Override
    public ShopSearchPresenter createPresenter() {
        return new ShopSearchPresenter();
    }

    @Override
    public void getShopList(List<ShopsBean> data) {
        //根据关键词和分类获取数据
        if (!isLinear) {
            mGridAdapter.setNewData(data);
        } else {
            mLinearAdapter.setNewData(data);
        }
    }
}
