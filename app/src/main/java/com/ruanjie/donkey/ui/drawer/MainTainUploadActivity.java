package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.MaintainUploadAdapter;
import com.ruanjie.donkey.bean.HelpAndBackBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.drawer.contract.MaintainUploadContract;
import com.ruanjie.donkey.ui.drawer.presenter.MaintainUploadPresenter;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.OtherUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainTainUploadActivity extends ToolbarActivity<MaintainUploadPresenter>
        implements MaintainUploadContract.View {


    @BindView(R.id.etContent)
    AppCompatEditText etContent;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvSubmit)
    BLTextView tvSubmit;
    MaintainUploadAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainTainUploadActivity.class);
//        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("维护上传")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_maintain_upload;
    }

    @Override
    protected void initialize() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.i("afterTextChanged", "afterTextChanged = " + s.toString().length());
                if (s.toString().length() > 50) {
                    etContent.setText(s.subSequence(0, 50));
                    etContent.setSelection(etContent.getText().toString().length());
                }
            }
        });

        //默认添加一个“+”图片
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MaintainUploadAdapter(getContext());

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivImage:
                        HelpAndBackBean helpAndBackBean = mAdapter.getData().get(position);
                        if ("add".equals(helpAndBackBean.getPicPath())) {
                            OtherUtils.selectImages(getActivity(), handleCount());
                        } else {

                        }
                        break;
                    case R.id.ivClose:
                        int size = mAdapter.getData().size();
                        if (size == 3) {
                            String picPath = mAdapter.getData().get(2).getPicPath();
                            mAdapter.remove(position);
                            if (!"add".equals(picPath)) {
                                mAdapter.addData(new HelpAndBackBean("add"));
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

    private void getDatas() {
        List<HelpAndBackBean> data = mAdapter.getData();
        if (data.size() == 0) {
            data.add(new HelpAndBackBean("add"));
        }
        mDatas = data;
        LogUtils.i("onBindVH", "getDatas = " + mDatas.size());

        mAdapter.setNewData(mDatas);

    }

    int curPicCount = 0;

    private int handleCount() {
        return 3 - curPicCount;
    }

    List<HelpAndBackBean> mDatas = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i("onActivityResult", "key = " + "value");
        if (requestCode == OtherUtils.IMAGE_BACK && resultCode == RESULT_OK) {
            List<String> mSelected = Matisse.obtainPathResult(data);
//            imagePath = mSelected.get(0);
//            ImageUtil.loadImageNone(image, imagePath);

            for (String str : mSelected) {
                mDatas.add(0, new HelpAndBackBean(str));
            }
            //判断是否已经到达5张图片
            if (mDatas.size() >= 4) {
                mDatas.remove(3);
            }
            mAdapter.setNewData(mDatas);
            curPicCount = mAdapter.getItemCount() - 1;
        }
    }

    @Override
    public MaintainUploadPresenter createPresenter() {
        return new MaintainUploadPresenter();
    }

    @Override
    public void uploadMaintain(String data) {
        ToastUtil.s("上传成功");
        finish();
    }

    @Override
    public void uploadImage(UploadBean data) {
        if (uploadCount < mUploadImages.size()) {
            if (uploadCount == 1) {
                //上传第一张
                mUploadImage.clear();
                mUploadImage.add(data.getUrl());
                if (uploadCount < mUploadImages.size()) {
                    uploadCount++;
                    getPresenter().uploadImage(OtherUtils.bitmapToString(mUploadImages.get(uploadCount - 1).getPicPath()));
                }
            } else {
                mUploadImage.add(data.getUrl());
                if (uploadCount < mUploadImages.size()) {
                    uploadCount++;
                    getPresenter().uploadImage(OtherUtils.bitmapToString(mUploadImages.get(uploadCount - 1).getPicPath()));
                } else {
                    //上传完毕
                    String contentStr = etContent.getText().toString().trim();
                    if (TextUtils.isEmpty(contentStr)) {
                        ToastUtil.s("请输入上传内容");
                        return;
                    }
                    getPresenter().uploadMaintain(contentStr, getImagesStr());
                }
            }
        } else {
            mUploadImage.add(data.getUrl());
            //上传完毕
            String contentStr = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(contentStr)) {
                ToastUtil.s("请输入上传内容");
                return;
            }
            getPresenter().uploadMaintain(contentStr, getImagesStr());
        }

    }

    private String getImagesStr() {
        String tempStr = "";
        if (mUploadImage != null && mUploadImage.size() > 0) {
            for (String temp : mUploadImage) {
                tempStr = tempStr + temp + ",";
            }

            if (!TextUtils.isEmpty(tempStr)) {
                tempStr = tempStr.substring(0, tempStr.length() - 1);
            }
        }
        return tempStr;
    }

    List<String> mUploadImage = new ArrayList<>();
    int uploadCount = 1;
    List<HelpAndBackBean> mUploadImages;

    @OnClick({R.id.tvSubmit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                //文字必须有
                String contentStr = etContent.getText().toString().trim();
                if (TextUtils.isEmpty(contentStr)) {
                    ToastUtil.s("请输入上传内容");
                    return;
                }
                //有图则先上传图片
                List<HelpAndBackBean> data = mAdapter.getData();
                if (data.size() > 0 && !"add".equals(data.get(0).getPicPath())) {
//                    //
                    mUploadImages = getUploadDatas(data);
                    getPresenter().uploadImage(OtherUtils.bitmapToString(mUploadImages.get(uploadCount - 1).getPicPath()));
                } else {
                    getPresenter().uploadMaintain(contentStr, "");
                }

                break;
        }
    }

    private List<HelpAndBackBean> getUploadDatas(List<HelpAndBackBean> data) {
        List<HelpAndBackBean> mDatas = new ArrayList<>();
        if (data.size() == 3) {
            if ("add".equals(data.get(data.size() - 1).getPicPath())) {
                mDatas = data.subList(0, data.size() - 1);
            } else {
                mDatas = data;
            }
        } else {
            mDatas = data.subList(0, data.size() - 1);
        }

        return mDatas;
    }

}
