package com.ruanjie.donkey.ui.shop;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.noober.background.view.BLEditText;
import com.noober.background.view.BLTextView;
import com.noober.background.view.BLView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.PublishAdImageAdapter;
import com.ruanjie.donkey.bean.PublishAdImageBean;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.bean.UploadBean2;
import com.ruanjie.donkey.ui.drawer.ShowBigImageActivity;
import com.ruanjie.donkey.ui.shop.contract.ShopAppraiceContract;
import com.ruanjie.donkey.ui.shop.presenter.ShopAppraicePresenter;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.OtherUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ruanjie.donkey.utils.OtherUtils.IMAGE_BACK;

public class ShopAppraiceActivity extends ToolbarActivity<ShopAppraicePresenter>
        implements ShopAppraiceContract.View {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.vSelect)
    BLView vSelect;
    @BindView(R.id.llSelectAll)
    LinearLayout llSelectAll;
    @BindView(R.id.mRatingBar)
    ScaleRatingBar mRatingBar;
    @BindView(R.id.etContent)
    BLEditText etContent;
    @BindView(R.id.tvSubmit)
    BLTextView tvSubmit;
    @BindView(R.id.mRootView)
    LinearLayout mRootView;
    ShopOrderBean mBean;

    public static void start(Context context, String jsonStr) {
        Intent starter = new Intent(context, ShopAppraiceActivity.class);
        starter.putExtra("jsonStr", jsonStr);
//        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_shop_appraice;
    }

    @Override
    protected void initialize() {
        String jsonStr = getIntent().getStringExtra("jsonStr");
        mBean = new Gson().fromJson(jsonStr, ShopOrderBean.class);
        if (mBean != null) {
            ImageUtil.loadImage(ivImage, mBean.getStoreInfo().getLogo());
            tvName.setText(mBean.getStoreInfo().getName());
        }

        initImages();

        mRecyclerView.requestFocus();


    }

    PublishAdImageAdapter mAdapter;

    private void initImages() {
        //默认添加一个“+”图片
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PublishAdImageAdapter(getContext());

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivImage:
                        PublishAdImageBean publishAdImageBean = mAdapter.getData().get(position);
                        if ("add".equals(publishAdImageBean.getPicPath())) {
                            OtherUtils.selectImages(getActivity(), handleCount());
                        } else {
                            ShowBigImageActivity.start(getContext(), publishAdImageBean.getPicPath(), 0);
                        }
                        break;
                    case R.id.ivClose:
                        int size = mAdapter.getData().size();
                        if (size == 3) {
                            String picPath = mAdapter.getData().get(2).getPicPath();
                            mAdapter.remove(position);
                            if (!"add".equals(picPath)) {
                                mAdapter.addData(new PublishAdImageBean("add"));
                            }
                        } else {
                            mAdapter.remove(position);
                        }
                        curPicCount = mAdapter.getItemCount() - 1;
                        break;
                }
            }
        });
        getDatas();
    }


    int curPicCount = 0;

    List<PublishAdImageBean> mDatas = new ArrayList<>();

    private int handleCount() {
        return 3 - curPicCount;
    }

    private void getDatas() {
        List<PublishAdImageBean> data = mAdapter.getData();
        if (data.size() == 0) {
            data.add(new PublishAdImageBean("add"));
        }
        mDatas = data;
        LogUtils.i("onBindVH", "getDatas = " + mDatas.size());

        mAdapter.setNewData(mDatas);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i("onActivityResult", "key = " + "value");
        if (requestCode == IMAGE_BACK && resultCode == RESULT_OK) {
            List<String> mSelected = Matisse.obtainPathResult(data);
            L.i("Matisse", "mSelected: " + mSelected);
//            imagePath = mSelected.get(0);
//            ImageUtil.loadImageNone(image, imagePath);

            for (String str : mSelected) {
                mDatas.add(0, new PublishAdImageBean(str));
            }

            if (mDatas.size() >= 4) {
                mDatas.remove(3);
            }
            mAdapter.setNewData(mDatas);
            curPicCount = mAdapter.getItemCount() - 1;

        }
    }


    @OnClick({R.id.ivBack, R.id.llSelectAll, R.id.vSelect, R.id.tvSubmit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.vSelect:
            case R.id.llSelectAll:
                boolean isSelected = llSelectAll.isSelected();
                llSelectAll.setSelected(!isSelected);
                break;
            case R.id.tvSubmit:
                int rating = ((int) mRatingBar.getRating());
                int isSelect = llSelectAll.isSelected() ? 1 : 0;
                String trimStr = etContent.getText().toString().trim();
                //提交
                //先上传头像
                List<PublishAdImageBean> tempDatas = mAdapter.getData();
                if (tempDatas != null && tempDatas.size() > 1) {
                    List<String> mDatas = new ArrayList<>();
                    String tempStr = "";
                    for (PublishAdImageBean bean : tempDatas) {
                        if (!"add".equals(bean.getPicPath())) {
                            mDatas.add(bean.getPicPath());
//                            mDatas.add(OtherUtils.bitmapToString(bean.getPicPath()));
                            tempStr = tempStr + OtherUtils.bitmapToString(bean.getPicPath()) + ",";
                        }
                    }
                    if (!TextUtils.isEmpty(tempStr)) {
                        tempStr = tempStr.substring(0, tempStr.length() - 1);
                    }
//                    getPresenter().uploadImage(new Gson().toJson(mDatas));
                    getPresenter().uploadImage2(tempStr);


                } else {
                    getPresenter().appraiceGoods(mBean.getId(), rating, trimStr, isSelect, "");
                }

                break;
        }
    }

    @Override
    public ShopAppraicePresenter createPresenter() {
        return new ShopAppraicePresenter();
    }

    @Override
    public void appraiceGoods(String data) {
        ToastUtil.s("提交成功");
        EventBusUtils.post(MEventBus.APPRAICE_ORDER_SUCCESS, null);
        finish();
    }

    @Override
    public void uploadImage2(UploadBean2 content) {
        int rating = ((int) mRatingBar.getRating());
        int isSelect = llSelectAll.isSelected() ? 1 : 0;
        String trimStr = etContent.getText().toString().trim();
        String tempStr = "";
        for (String str : content.getUrl()) {
            tempStr += str + ",";

        }
        if (!TextUtils.isEmpty(tempStr)) {
            tempStr = tempStr.substring(0, tempStr.length() - 1);
        }
        getPresenter().appraiceGoods(mBean.getId(), rating, trimStr, isSelect, tempStr);

    }
}
