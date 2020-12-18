package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.BottomListBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.ui.drawer.contract.UserInfoContract;
import com.ruanjie.donkey.ui.drawer.presenter.UserInfoPresenter;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.OtherUtils;
import com.ruanjie.donkey.utils.SPManager;
import com.ruanjie.donkey.widget.MBottomListDialog;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.NetworkUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.zhihu.matisse.Matisse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends ToolbarActivity<UserInfoPresenter>
        implements UserInfoContract.View {

    @BindView(R.id.civHead)
    CircleImageView civHead;
    @BindView(R.id.stvNickname)
    SuperTextView stvNickname;
    @BindView(R.id.stvName)
    SuperTextView stvName;
    @BindView(R.id.stvGender)
    SuperTextView stvGender;
    @BindView(R.id.stvBirthday)
    SuperTextView stvBirthday;
    @BindView(R.id.stvRealName)
    SuperTextView stvRealName;
    @BindView(R.id.stvBindPhone)
    SuperTextView stvBindPhone;
    @BindView(R.id.stvVip)
    SuperTextView stvVip;
    @BindView(R.id.stvBarCode)
    SuperTextView stvBarCode;
    LoginBean mLoginBean;
    TimePickerView birthdayTimePiker;
    @BindView(R.id.mRootView)
    LinearLayout mRootView;
    @BindView(R.id.ivBarCode)
    ImageView ivBarCode;
    @BindView(R.id.llBarCode)
    LinearLayout llBarCode;

    public static void start(Context context) {
        Intent starter = new Intent(context, UserInfoActivity.class);
        // starter.putExtra(F);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle(getString(R.string.userinfo_title))
                .setTitleTextColor(Color.BLACK)
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initialize() {
//        StatusBarUtil.setStatusBarLightMode(this);

        getPresenter().getUserInfo();
    }

    private void initViews() {
        mLoginBean = SPManager.getLoginBean();
        LogUtils.logBean("用户信息", mLoginBean);

        ImageUtil.loadImage(civHead, mLoginBean.getAvatar(), R.mipmap.userinfo_head);
        stvNickname.setRightString(mLoginBean.getNickname());
        stvName.setRightString(mLoginBean.getUsername());
        stvGender.setRightString(mLoginBean.getSex() == 1 ? getString(R.string.userinfo_man)
                : mLoginBean.getSex() == 2 ? getString(R.string.userinfo_woman)
                : getString(R.string.userinfo_none));
        stvBirthday.setRightString(mLoginBean.getBirthday());
        //0=未申请,1=已通过,2=未通过,3=审核中
        stvRealName.setRightString(mLoginBean.getIs_realname() == 1 ? getString(R.string.userinfo_has_real_name)
                : mLoginBean.getIs_realname() == 2 ? getString(R.string.userinfo_no_real_name)
                : mLoginBean.getIs_realname() == 3 ? getString(R.string.userinfo_real_name_ing)
                : getString(R.string.userinfo_no_apply)
        );

        stvBindPhone.setRightString(getEncrryptPhone(mLoginBean.getPhone()));
        if (mLoginBean.getIs_maintenance() == 1) {
            stvVip.setRightString(getString(R.string.userinfo_type_1));
        } else {
            if (mLoginBean.getUser_type() == 1) {
                stvVip.setRightString(getString(R.string.userinfo_type_2));
            } else {
                stvVip.setRightString(getString(R.string.userinfo_type_3));
            }
        }

        String qrcode = mLoginBean.getQrcode();
        if (TextUtils.isEmpty(qrcode)) {
            llBarCode.setVisibility(View.GONE);
        } else {
            llBarCode.setVisibility(View.VISIBLE);
            ImageUtil.loadImage(ivBarCode, qrcode);
        }

    }

    private String getEncrryptPhone(String phone) {
        String tempStr = "";
        tempStr += phone.substring(0, 3);
        tempStr += "****";
        tempStr += phone.substring(7, 11);
        return tempStr;
    }

    @OnClick({R.id.civHead, R.id.stvNickname, R.id.stvName
            , R.id.stvGender, R.id.stvBirthday, R.id.stvRealName
            , R.id.stvBindPhone, R.id.stvVip, R.id.llBarCode})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civHead:
//                showImageSelectDialog();
                OtherUtils.selectImages(getActivity(), 1);
                break;
            case R.id.stvNickname:
                ChangeNicknameActivity.start(getContext(), mLoginBean.getNickname());
                break;
            case R.id.stvName:
                break;
            case R.id.stvGender:
                showGenderSelectDialog();
                break;
            case R.id.stvBirthday:
                if (!NetworkUtil.isConnected(getContext())) {
                    NetworkUtil.showNoNetWorkDialog(getContext());
                    return;
                }
                if (birthdayTimePiker == null) {
                    initBirthdayPicker();
                }
                birthdayTimePiker.show();

                break;
            case R.id.stvRealName:
                if (mLoginBean != null) {
                    //:0=未申请,1=已通过,2=未通过,3=审核中
                    int isRealname = mLoginBean.getIs_realname();
                    RealNameApplyActivity.start(getContext(), isRealname);
                }
                break;
            case R.id.stvBindPhone:
                ChangePhoneActivity.start(getContext());
                break;
            case R.id.stvVip:
                break;
            case R.id.llBarCode:
                ShowBigImageActivity.start(getContext(), mLoginBean.getQrcode(), 0);
                break;
        }
    }

    private void showImageSelectDialog() {
        MBottomListDialog blDia = new MBottomListDialog();
//        blDia.hideCancelBtn();
//        blDia.setTitle("");
        List<BottomListBean> mData = new ArrayList<>();
        mData.add(new BottomListBean(getString(R.string.userinfo_take_picture), ContextUtil.getColor(R.color.text_yellow)));
        mData.add(new BottomListBean(getString(R.string.userinfo_album), ContextUtil.getColor(R.color.text_black)));

        blDia.setData(mData);
        blDia.setOnItemClickListener(new MBottomListDialog.OnItemClickListener<BottomListBean>() {
            @Override
            public void onItemClick(MBottomListDialog dialog, BottomListBean data, int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }
        });
        blDia.show(getActivity());
    }

    private void showGenderSelectDialog() {
        MBottomListDialog blDia = new MBottomListDialog();
        blDia.useRoundBackground();
//        blDia.hideCancelBtn();
//        blDia.setTitle("");
        List<BottomListBean> mData = new ArrayList<>();
        mData.add(new BottomListBean("男", ContextUtil.getColor(R.color.text_yellow)));
        mData.add(new BottomListBean("女", ContextUtil.getColor(R.color.text_black)));

        blDia.setData(mData);
        blDia.setOnItemClickListener(new MBottomListDialog.OnItemClickListener<BottomListBean>() {
            @Override
            public void onItemClick(MBottomListDialog dialog, BottomListBean data, int position) {
                getPresenter().changeUserInfo(1, position == 0 ? 1 : 2, "", "");
            }
        });
        blDia.show(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        LogUtils.i("onActivityResult", "key = " + "value");
        if (resultCode == RESULT_OK) {
            List<String> mSelected = Matisse.obtainPathResult(data);
            LogUtils.i("Matisse", "mSelected = " + mSelected.get(0));
            if (requestCode == OtherUtils.IMAGE_BACK) {
                getPresenter().uploadImage(OtherUtils.bitmapToString(mSelected.get(0)));
//                List<String> mDatas = new ArrayList<>();mDatas.add(mSelected.get(0));
//                getPresenter().uploadImage2(mDatas);
            }
        }
    }

    @Override
    public UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    public void uploadImage(UploadBean data) {
        getPresenter().changeHead(data.getUrl());
    }

    @Override
    public void uploadImage2(String data) {

    }

    @Override
    public void changeHead(UploadBean data) {
        mLoginBean.setAvatar(data.getUrl());
        SPManager.setLoginBean(mLoginBean);

        EventBusUtils.post(MEventBus.REFRESH_USERINFO, null);
    }

    @Override
    public void changeUserInfo(int type, int sex, String nickname, String birthday) {
        switch (type) {
            case 1:
                mLoginBean.setSex(sex);
                break;
            case 2:
                mLoginBean.setBirthday(birthday);
                break;
        }
        SPManager.setLoginBean(mLoginBean);
        ToastUtil.s("修改成功");
        EventBusUtils.post(MEventBus.REFRESH_USERINFO, null);
    }

    @Override
    public void getUserInfo(LoginBean data) {
        SPManager.setLoginBean(data);
        EventBusUtils.post(MEventBus.REFRESH_USERINFO, null);
    }

    @Override
    public void mEventBus(EventBusBean busBean) {
        switch (busBean.getCode()) {
            case MEventBus.REFRESH_USERINFO:
                initViews();
                break;
        }
    }

    private void initBirthdayPicker() {
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        //获取今天的日期
        int year = endDate.get(Calendar.YEAR);
        int month = endDate.get(Calendar.MONTH);
        int day = endDate.get(Calendar.DAY_OF_MONTH);
        endDate.set(year, month, day);
        //时间选择器 ，自定义布局
        birthdayTimePiker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                getPresenter().changeUserInfo(2, -1, "", getTime(date));
//                birthdayStr = getTime(date);
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
                /*.animGravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        LinearLayout mRootView = (LinearLayout) v.findViewById(R.id.mRootView);
                        mRootView.setPadding(0, 0, 0, OtherUtils.getNavigationBarHeight(getContext()));

                        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                        TextView tvOk = (TextView) v.findViewById(R.id.tvOk);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tvCancel);
                        tvTitle.setText("选择出生日期");


                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                birthdayTimePiker.returnData();
                                birthdayTimePiker.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                birthdayTimePiker.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(17)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(ContextUtil.getColor(R.color.explainColor))
                .build();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
