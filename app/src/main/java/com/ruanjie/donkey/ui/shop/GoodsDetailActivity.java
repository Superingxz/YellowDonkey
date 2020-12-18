package com.ruanjie.donkey.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.ui.shop.contract.GoodsDetailContract;
import com.ruanjie.donkey.ui.shop.presenter.GoodsDetailPresenter;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import butterknife.BindView;
import butterknife.OnClick;


public class GoodsDetailActivity extends ToolbarActivity<GoodsDetailPresenter>
        implements GoodsDetailContract.View {
    int mGoodsId;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice1)
    TextView tvPrice1;
    @BindView(R.id.tvPrice2)
    TextView tvPrice2;
    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.tvDetail)
    TextView tvDetail;
    @BindView(R.id.tvExChange)
    TextView tvExChange;
    @BindView(R.id.mRootView)
    LinearLayout mRootView;
    @BindView(R.id.mMZBanner)
    MZBannerView mMZBanner;

    public static void start(Context context, int goodsId) {
        Intent starter = new Intent(context, GoodsDetailActivity.class);
        starter.putExtra("goodsId", goodsId);

        context.startActivity(starter);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @Override
    protected void initialize() {
        mGoodsId = getIntent().getIntExtra("goodsId", 0);
        initBanner();
        getPresenter().getGoodsDetail(mGoodsId);
    }

    @Override
    public GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter();
    }

    @Override
    public void getGoodsDetail(GoodsDetailBean data) {

        initViews(data);
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

    private void initBanner() {

        mMZBanner.setDelayedTime(2000);
        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int posi) {

            }
        });

    }
    GoodsDetailBean mGoodsDetailBean;

    private void initViews(GoodsDetailBean data) {
        mGoodsDetailBean = data;
        // 设置数据
        mMZBanner.setPages(data.getImages(), new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        tvName.setText(data.getName());
        tvPrice1.setText(data.getScore() + "驴币 + ￥" + data.getPrice());
        tvPrice2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tvPrice2.setText(data.getOrigin_price());

        tvCount.setText("已售 " + data.getSales() + " 剩余" + data.getStock() + " 件");
        tvDetail.setText(data.getContent());

    }

    public static class BannerViewHolder implements MZViewHolder<String> {
        private RoundedImageView ivImage;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
            ivImage = (RoundedImageView) view.findViewById(R.id.ivImage);
            ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            ImageUtil.loadImage(ivImage, data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();//开始轮播
    }


    @OnClick({R.id.ivBack, R.id.tvExChange})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvExChange:
                if(mGoodsDetailBean!=null){
                    DiaLogUtils.showTipDialog(getContext()
                            , ""
                            , "本次兑换共 " + mGoodsDetailBean.getScore()
                                    + "驴币(确认马上扣除) +" + mGoodsDetailBean.getPrice()
                                    + "元 （到店自行支付），兑换成功后需到店出示订单二维码并支付金额部分"
                            , "取消"
                            , "确定"
                            , new PromptDialog.OnButtonClickListener() {
                                @Override
                                public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                    if (isPositiveClick) {
                                        getPresenter().buyGoods(mGoodsDetailBean.getId());
                                    }
                                }
                            });
                }
                break;
        }
    }
}
