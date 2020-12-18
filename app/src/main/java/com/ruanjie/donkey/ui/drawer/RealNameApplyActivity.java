package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.drawer.contract.RealNameApplyContract;
import com.ruanjie.donkey.ui.drawer.presenter.RealNameApplyPresenter;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.OtherUtils;
import com.ruanjie.donkey.utils.SPManager;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.zhihu.matisse.Matisse;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RealNameApplyActivity extends ToolbarActivity<RealNameApplyPresenter>
        implements RealNameApplyContract.View {

    @BindView(R.id.etName)
    AppCompatEditText etName;
    @BindView(R.id.etCardNumber)
    AppCompatEditText etCardNumber;
    @BindView(R.id.ivFront)
    ImageView ivFront;
    @BindView(R.id.ivTake1)
    ImageView ivTake1;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivTake2)
    ImageView ivTake2;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.mRootView)
    LinearLayout mRootView;
    @BindView(R.id.ivFace)
    ImageView ivFace;
    @BindView(R.id.ivTake3)
    ImageView ivTake3;

    public static void start(Context context, int type) {
        Intent starter = new Intent(context, RealNameApplyActivity.class);
        starter.putExtra("type", type);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("实名认证")
                .setTitleTextColor(Color.BLACK)
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_real_name_apply;
    }

    String[] mImages;
    int mType;

    @Override
    protected void initialize() {
        mImages = new String[3];
        mImages[0] = "";
        mImages[1] = "";
        mImages[2] = "";
        mType = getIntent().getIntExtra("type", 0);
        //0=未申请,正常模式
        // 1=已通过,要赋值，都无法点击无法输入
        // 2=未通过,要赋值，可以点击可以输入
        // 3=审核中,要赋值，都无法点击无法输入
        LoginBean mLoginBean = SPManager.getLoginBean();
        if (mType != 0) {
            //赋值
            etName.setText(mLoginBean.getUsername());
            etName.setSelection(mLoginBean.getUsername().length());
            etCardNumber.setText(mLoginBean.getIdcard());
            etCardNumber.setSelection(mLoginBean.getIdcard().length());

            mImages[0] = mLoginBean.getIdcard_font();
            mImages[1] = mLoginBean.getIdcard_back();
            mImages[2] = mLoginBean.getIdcard_hand();

            ImageUtil.loadImage(ivFront, mLoginBean.getIdcard_font());
            ivTake1.setVisibility(View.GONE);
            ImageUtil.loadImage(ivBack, mLoginBean.getIdcard_back());
            ivTake2.setVisibility(View.GONE);

            tvSubmit.setText(mLoginBean.getIs_realname() == 1 ? getString(R.string.userinfo_has_real_name)
                    : mLoginBean.getIs_realname() == 2 ? getString(R.string.userinfo_no_real_name)
                    : mLoginBean.getIs_realname() == 3 ? getString(R.string.userinfo_real_name_ing)
                    : getString(R.string.userinfo_no_apply));


            if (mType == 2) {
                etName.setEnabled(true);
                etCardNumber.setEnabled(true);
                tvSubmit.setEnabled(true);
            } else {
                etName.setEnabled(false);
                etCardNumber.setEnabled(false);
                tvSubmit.setEnabled(false);
            }
        }
    }

    int which = 1;

    @OnClick({R.id.ivFront, R.id.ivBack, R.id.tvSubmit, R.id.ivFace})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFront:
                if (mType == 1 || mType == 3) {
                    ShowBigImageActivity.start(getContext(), mImages[0], 0);
                } else {
                    which = 1;
                    OtherUtils.selectImages(this, 1);
                }
                break;
            case R.id.ivBack:
                if (mType == 1 || mType == 3) {
                    ShowBigImageActivity.start(getContext(), mImages[1], 0);
                } else {
                    which = 2;
                    OtherUtils.selectImages(this, 1);
                }
                break;
            case R.id.ivFace:
                if (mType == 1 || mType == 3) {
                    ShowBigImageActivity.start(getContext(), mImages[2], 0);
                } else {
                    which = 3;
                    OtherUtils.selectImages(this, 1);
                }
                break;
            case R.id.tvSubmit:
                String nameStr = etName.getText().toString().trim();
                String cardNumStr = etCardNumber.getText().toString().trim();
                String image1 = mImages[0];
                String image2 = mImages[1];
                String image3 = mImages[2];
                LogUtils.i("上传图片", "name = " + nameStr
                        + "\ncardNum = " + cardNumStr
                        + "\nimage1 = " + image1
                        + "\nimage2 = " + image2);
                if (TextUtils.isEmpty(nameStr)) {
                    ToastUtil.s("请输入真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(cardNumStr)) {
                    ToastUtil.s("请输入身份证号");
                    return;
                }
                if (TextUtils.isEmpty(image1)) {
                    ToastUtil.s("请选择人像面");
                    return;
                }
                if (TextUtils.isEmpty(image2)) {
                    ToastUtil.s("请选择国徽面");
                    return;
                }

                if (TextUtils.isEmpty(image3)) {
                    ToastUtil.s("请选择手持人像面");
                    return;
                }
                getPresenter().realNameApply(nameStr, cardNumStr, image1, image2, image3);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            List<String> mSelected = Matisse.obtainPathResult(data);
            LogUtils.i("Matisse", "mSelected = " + mSelected.get(0));
            if (requestCode == OtherUtils.IMAGE_BACK) {
                switch (which) {
                    case 1:
                        mImages[0] = "";
                        ImageUtil.loadImage(ivFront, R.mipmap.real_name_front);
                        ivTake1.setVisibility(View.VISIBLE);
                        getPresenter().uploadImage(1, OtherUtils.bitmapToString(mSelected.get(0)));
                        break;
                    case 2:
                        mImages[1] = "";
                        ImageUtil.loadImage(ivBack, R.mipmap.real_name_back);
                        ivTake2.setVisibility(View.VISIBLE);
                        getPresenter().uploadImage(2, OtherUtils.bitmapToString(mSelected.get(0)));
                        break;
                    case 3:
                        mImages[2] = "";
                        ImageUtil.loadImage(ivFace, R.mipmap.real_name_face);
                        ivTake3.setVisibility(View.VISIBLE);
                        getPresenter().uploadImage(3, OtherUtils.bitmapToString(mSelected.get(0)));
                        break;
                }
            }
        }
    }

    @Override
    public RealNameApplyPresenter createPresenter() {
        return new RealNameApplyPresenter();
    }


    @Override
    public void uploadImage(int which, UploadBean data) {
        switch (which) {
            case 1:
                mImages[0] = data.getUrl();
                ImageUtil.loadImage(ivFront, data.getUrl());
                ivTake1.setVisibility(View.GONE);

                break;
            case 2:
                mImages[1] = data.getUrl();
                ImageUtil.loadImage(ivBack, data.getUrl());
                ivTake2.setVisibility(View.GONE);
                break;
            case 3:
                mImages[2] = data.getUrl();
                ImageUtil.loadImage(ivFace, data.getUrl());
                ivTake3.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void realNameApply(String data) {

        LoginBean loginBean = SPManager.getLoginBean();
        loginBean.setIs_realname(3);
        SPManager.setLoginBean(loginBean);
        EventBusUtils.post(MEventBus.REFRESH_USERINFO, null);
        ToastUtil.s("提交成功");
        finish();
    }
}
