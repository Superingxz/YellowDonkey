package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.ui.drawer.fragment.AllBalanceFragment;
import com.ruanjie.donkey.ui.drawer.fragment.CurrentMonthFragment;
import com.ruanjie.donkey.ui.drawer.fragment.CurrentTodayFragment;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.OtherUtils;
import com.ruanjie.donkey.utils.TimeUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class JoinCityResultActivity extends ToolbarActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvYear)
    TextView tvYear;
    @BindView(R.id.tvMonthAndDay)
    TextView tvMonthAndDay;
    @BindView(R.id.llTime)
    LinearLayout llTime;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    String[] mTitles;
    String mTimeStr1, mTimeStr2;
    String mYearStr;
    String mMonthStr;
    String mMonthDayStr;
    int mType = 1;//1当日 2当月 3总金额

    public static void start(Context context) {
        Intent starter = new Intent(context, JoinCityResultActivity.class);
        // starter.putExtra(F);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_join_city_result;
    }

    @Override
    protected void initialize() {

        //获取当前时间
        getTimeDatas(System.currentTimeMillis(), 1);

        initTabDatas();
    }

    private void getTimeDatas(long l, int type) {
        if (type == 3) {
            llTime.setVisibility(View.INVISIBLE);
        }else{
            llTime.setVisibility(View.VISIBLE);

            mTimeStr1 = TimeUtils.time2Str(l, "yyyy-MM-dd");
            mTimeStr2 = TimeUtils.time2Str(l, "yyyy-MM");

            mYearStr = TimeUtils.time2Str(l, "yyyy年");
            mMonthStr = TimeUtils.time2Str(l, "MM月");
            mMonthDayStr = TimeUtils.time2Str(l, "MM月dd日");
            LogUtils.i("当前时间", "mTimeStr = " + mTimeStr1);
            tvYear.setText(mYearStr);
            tvMonthAndDay.setText((type == 1 ? mMonthDayStr : mMonthStr));
        }
    }

    public String getDateStr(int type) {
        if (type == 1) {
            return mTimeStr1;
        } else {
            return mTimeStr2;
        }
    }

    private void initTabDatas() {
        mTitles = new String[3];
        mTitles[0] = "当日";
        mTitles[1] = "当月";
        mTitles[2] = "总金额";
        mFragments.add(CurrentTodayFragment.newInstance());
        mFragments.add(CurrentMonthFragment.newInstance());
        mFragments.add(AllBalanceFragment.newInstance());

        MyPagerAdapter mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mType = (position == 0 ? 1
                        : position == 1 ? 2
                        : 3
                );
                switch (position) {
                    case 0:
                        getTimeDatas(System.currentTimeMillis(), 1);
                        break;
                    case 1:
                        getTimeDatas(System.currentTimeMillis(), 2);
                        break;
                    case 2:
                        getTimeDatas(System.currentTimeMillis(), 3);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.getTabAt(0).select();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    TimePickerView mTimePiker1, mTimePiker2;

    @OnClick({R.id.ivBack, R.id.llTime})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.llTime:
                switch (mType) {
                    case 1:
                        if (mTimePiker1 == null) {
                            initTimePiker1();
                        }
                        mTimePiker1.show();
                        break;
                    case 2:
                        if (mTimePiker2 == null) {
                            initTimePiker2();
                        }
                        mTimePiker2.show();
                        break;
                    case 3:
                        break;
                }
                break;
        }
    }

    private void initTimePiker1() {

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
        mTimePiker1 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                getTimeDatas(date.getTime(), mType);
                EventBusUtils.post(MEventBus.REFRESH_CURRENT_TODAY_DATA, null);
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
                        tvTitle.setText("选择要跳转的日期");

                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTimePiker1.returnData();
                                mTimePiker1.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTimePiker1.dismiss();
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

    private void initTimePiker2() {
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
        mTimePiker2 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                getTimeDatas(date.getTime(), mType);

                EventBusUtils.post(MEventBus.REFRESH_CURRENT_MONTH_DATA, null);
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
                        tvTitle.setText("选择要跳转的日期");


                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTimePiker2.returnData();
                                mTimePiker2.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTimePiker2.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(17)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "")
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
