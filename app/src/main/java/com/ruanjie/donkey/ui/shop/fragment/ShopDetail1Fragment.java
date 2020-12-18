package com.ruanjie.donkey.ui.shop.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.SectionAdapter;
import com.ruanjie.donkey.adapter.ShopTypeAdapter;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.MySection;
import com.ruanjie.donkey.bean.ShopDetailBean;
import com.ruanjie.donkey.bean.ShopTypeBean;
import com.ruanjie.donkey.bean.ShopTypeBean2;
import com.ruanjie.donkey.ui.shop.GoodsDetailActivity;
import com.ruanjie.donkey.ui.shop.ShopOrderActivity;
import com.ruanjie.donkey.ui.shop.contract.ShopDetail1Contract;
import com.ruanjie.donkey.ui.shop.presenter.ShopDetail1Presenter;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.softgarden.baselibrary.base.BaseLazyFragment;
import com.softgarden.baselibrary.dialog.PromptDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopDetail1Fragment extends BaseLazyFragment<ShopDetail1Presenter>
        implements ShopDetail1Contract.View {

    @BindView(R.id.mLeftRecycler)
    RecyclerView mLeftRecycler;
    @BindView(R.id.mRightRecycler)
    RecyclerView mRightRecycler;
    ShopTypeAdapter mTypeAdapter;
    int mLeftType;

    public static ShopDetail1Fragment newInstance(String jsonStr) {
        Bundle args = new Bundle();
        args.putString("jsonStr", jsonStr);
        ShopDetail1Fragment fragment = new ShopDetail1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_shop_detail_1;
    }

    @Override
    protected void initEventAndData() {
        mTypeAdapter = new ShopTypeAdapter(getContext());
        mLeftRecycler.setAdapter(mTypeAdapter);
        mTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mLeftType = position;

                int mPosi = mTypeAdapter.getmSelectPosi();
                if (mPosi != position) {
                    mTypeAdapter.setmSelectPosi(position);
                    mTypeAdapter.notifyDataSetChanged();

                    LinearLayoutManager layoutManager = (LinearLayoutManager) mRightRecycler.getLayoutManager();
                    layoutManager.scrollToPositionWithOffset(mTypeAdapter.getData().get(position).getTypePosi(), 0);
                }

            }
        });


        String jsonStr = getArguments().getString("jsonStr");
        List<ShopDetailBean.GoodsListBean> mDatas
                = new Gson().fromJson(jsonStr, new TypeToken<List<ShopDetailBean.GoodsListBean>>() {
        }.getType());


        initViews(mDatas);


    }

    private void initViews(List<ShopDetailBean.GoodsListBean> mDatas) {
        //重组数据
        if (mDatas != null && mDatas.size() > 0) {
            List<ShopTypeBean> mTypeDatas = new ArrayList<>();
            List<ShopDetailBean.GoodsListBean.ListBean> mTempDatas = new ArrayList<>();
            for (ShopDetailBean.GoodsListBean bean1 : mDatas) {
                mTypeDatas.add(new ShopTypeBean(bean1.getId(), bean1.getName()));
                List<ShopDetailBean.GoodsListBean.ListBean> list = bean1.getList();
                if (list != null && list.size() > 0) {
                    for (ShopDetailBean.GoodsListBean.ListBean bean2 : list) {
                        mTempDatas.add(bean2);
                    }
                }
            }
            List<MySection> mRightDatas = new ArrayList<>();

            //根据上面2个列表进行数据重组(右边数据)
            for (ShopTypeBean typeBean : mTypeDatas) {
                //添加head数据
                mRightDatas.add(new MySection(true, typeBean.getTypeName()));
                //添加所对应的数据
                for (ShopDetailBean.GoodsListBean.ListBean bean3 : mTempDatas) {
                    if (typeBean.getTypeName().equals(bean3.getCategory_name())) {
                        mRightDatas.add(new MySection(bean3));
                    }
                }
            }

            SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content, R.layout.def_section_head, mRightDatas);
            mRightRecycler.setAdapter(sectionAdapter);
            sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.tvExChange:
                            DiaLogUtils.showTipDialog(getContext()
                                    , ""
                                    , "本次兑换共 " + sectionAdapter.getData().get(position).t.getScore()
                                            + "驴币(确认马上扣除) +" + sectionAdapter.getData().get(position).t.getPrice()
                                            + "元 （到店自行支付），兑换成功后需到店出示订单二维码并支付金额部分"
                                    , "取消"
                                    , "确定"
                                    , new PromptDialog.OnButtonClickListener() {
                                        @Override
                                        public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                            if (isPositiveClick) {
                                                getPresenter().buyGoods(sectionAdapter.getData().get(position).t.getId());
                                            }
                                        }
                                    });
                            break;
                    }
                }
            });
            sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    GoodsDetailActivity.start(getContext(),sectionAdapter.getData().get(position).t.getId());

                }
            });
            //左边数据
            List<ShopTypeBean2> mLeftDatas = new ArrayList<>();
            for (int i = 0; i < mRightDatas.size(); ++i) {
                MySection section = mRightDatas.get(i);
                if (section.isHeader) {
                    mLeftDatas.add(new ShopTypeBean2(section.header, i));
                }
            }
            mTypeAdapter.setNewData(mLeftDatas);
        }

    }

    @Override
    public ShopDetail1Presenter createPresenter() {
        return new ShopDetail1Presenter();
    }

    @Override
    public void buyGoods(BuyGoodsBean data) {
        DiaLogUtils.showTipDialog(getContext()
                , ""
                , "兑换成功，请到我的订单查看"
                , ""
                , "确定"
                , new PromptDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                        if (isPositiveClick) {
                            //跳转我的订单模块
                            ShopOrderActivity.start(getContext(),0);
                        }
                    }
                });
    }
}
