package com.ruanjie.donkey.ui.upload;

import android.content.Context;
import android.content.Intent;
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
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.UploadAdapter;
import com.ruanjie.donkey.bean.HelpAndBackBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.upload.contract.UploadContract;
import com.ruanjie.donkey.ui.upload.presenter.UploadPresenter;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.OtherUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.upload
 * 文件名:   FaultUploadActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/14 4:14
 * 描述:     TODO
 */
public class FaultUploadActivity extends ToolbarActivity<UploadPresenter> implements UploadContract.View {

    @BindView(R.id.et_content)
    AppCompatEditText etContent;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private UploadAdapter mAdapter;
    int currentPhotoCount = 0;

    private int handleCount() {
        return 4 - currentPhotoCount;
    }

    private List<HelpAndBackBean> mDatas = new ArrayList<>();

    private List<String> mUploadImage = new ArrayList<>();

    int uploadCount = 1;

    private List<HelpAndBackBean> mUploadImages;

    @OnClick(R.id.bt_submit)
    void onSubmit() {
        //文字必须有
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
               ToastUtil.s("请输入上传内容");
                    return;
        }
        //有图则先上传图片
        List<HelpAndBackBean> data = mAdapter.getData();
            if (data.size() > 0 && !"add".equals(data.get(0).getPicPath())) {
                    mUploadImages = getUploadDatas(data);
                    getPresenter().uploadImage(OtherUtils.bitmapToString(mUploadImages.get(uploadCount - 1).getPicPath()));
                } else {
                    getPresenter().uploadFault(content, "");
                }
        }


    public static void start(Context context) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, FaultUploadActivity.class));
    }

    @Override
    public UploadPresenter createPresenter() {
        return new UploadPresenter(this);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle(R.string.vehicle_fault);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_fault_upload;
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
        initRecyclerView();

    }
    @Override
    public void initRecyclerView() {
        //默认添加一个“+”图片
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new UploadAdapter(getContext());

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add_photo:
                        HelpAndBackBean helpAndBackBean = mAdapter.getData().get(position);
                        if ("add".equals(helpAndBackBean.getPicPath())) {
                            OtherUtils.selectImages(getActivity(), handleCount());
                        }
                        break;
                    case R.id.iv_delete:
                        int size = mAdapter.getData().size();
                        if (size == 4) {
                            String picPath = mAdapter.getData().get(3).getPicPath();
                            mAdapter.remove(position);
                            if (!"add".equals(picPath)) {
                                mAdapter.addData(new HelpAndBackBean("add"));
                            }
                        } else {
                            mAdapter.remove(position);
                        }

                        currentPhotoCount = mAdapter.getItemCount() - 1;
                        break;
                }
            }

        });
        initDatas();
    }

    @Override
    public void initDatas() {
        List<HelpAndBackBean> data = mAdapter.getData();
        if (data.size() == 0) {
            data.add(new HelpAndBackBean("add"));
        }
        mDatas = data;
        LogUtils.i("onBindVH", "getDatas = " + mDatas.size());

        mAdapter.setNewData(mDatas);

    }

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
            if (mDatas.size() >= 5) {
                mDatas.remove(4);
            }
            mAdapter.setNewData(mDatas);
            currentPhotoCount = mAdapter.getItemCount() - 1;
        }
    }

    @Override
    public void upload(String data) {
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
                    String content = etContent.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.s("请输入上传内容");
                        return;
                    }
                    getPresenter().uploadFault(content, getImages());
                }
            }
        } else {
            mUploadImage.add(data.getUrl());
            //上传完毕
            String content = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                ToastUtil.s("请输入上传内容");
                return;
            }
            getPresenter().uploadFault(content, getImages());
        }
    }



    private String getImages() {
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


    private List<HelpAndBackBean> getUploadDatas(List<HelpAndBackBean> data) {
        List<HelpAndBackBean> mDatas = new ArrayList<>();
        if (data.size() == 4) {
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
