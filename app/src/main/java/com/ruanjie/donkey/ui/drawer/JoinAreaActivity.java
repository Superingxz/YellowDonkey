package com.ruanjie.donkey.ui.drawer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.AreaBean;
import com.ruanjie.donkey.bean.JoinAreaInfoBean;
import com.ruanjie.donkey.bean.ProvinceBean;
import com.ruanjie.donkey.ui.drawer.contract.JoinAreaContract;
import com.ruanjie.donkey.ui.drawer.presenter.JoinAreaPresenter;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.GetJsonDataUtil;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.OtherUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class JoinAreaActivity extends ToolbarActivity<JoinAreaPresenter>
        implements JoinAreaContract.View {

    @BindView(R.id.ivImageTop)
    ImageView ivImageTop;
    @BindView(R.id.etName)
    AppCompatEditText etName;
    @BindView(R.id.etPhone)
    AppCompatEditText etPhone;
    @BindView(R.id.tvArea)
    TextView tvArea;
    @BindView(R.id.llInputLayout)
    LinearLayout llInputLayout;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    public static void start(Context context) {
        Intent starter = new Intent(context, JoinAreaActivity.class);
        // starter.putExtra(F);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("区域代理")
                .setTitleTextColor(Color.BLACK)
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_join_area;
    }

    @Override
    protected void initialize() {
        getPresenter().getJoinAreaInfo();
    }

    private boolean isLoaded = false;

    @OnClick({R.id.tvArea, R.id.tvSubmit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvArea:
                if (isLoaded) {
                    initAreaPicker();
                } else {
                    initJsonData();
                }
                break;
            case R.id.tvSubmit:
                if (OtherUtils.stringIsNull(etName, "请填写姓名")) {
                    return;
                }
                if (OtherUtils.stringIsNull(etPhone, "请填写联系方式")) {
                    return;
                }
                if (OtherUtils.stringIsNull(tvArea, "请选择代理区域")) {
                    return;
                }
                String nameStr = etName.getText().toString().trim();
                String phoneStr = etPhone.getText().toString().trim();
                String areaStr = tvArea.getText().toString().trim();
                getPresenter().joinArea(nameStr, phoneStr, "", areaIdsStr);


                break;
        }
    }

    OptionsPickerView areaPicker;
    String areaStr;
    String areaIdsStr;
    private ArrayList<AreaBean> mProvinceDatas = new ArrayList<>();
    private ArrayList<ArrayList<AreaBean>> mCityDatas = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<AreaBean>>> mAreaDatas = new ArrayList<>();

    private void initAreaPicker() {
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        areaPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                areaStr = mProvinceDatas.get(options1).getName() +
                        mCityDatas.get(options1).get(options2).getName() +
                        mAreaDatas.get(options1).get(options2).get(options3).getName();
                areaIdsStr = mAreaDatas.get(options1).get(options2).get(options3).getId() + "";
                LogUtils.i("省市区", "areaStr = " + areaStr
                        + "\nareaIdsStr = " + areaIdsStr);
                tvArea.setText(areaStr);


            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        LinearLayout mPickerLayout = (LinearLayout) v.findViewById(R.id.mPickerLayout);
                        mPickerLayout.setPadding(0, 0, 0, OtherUtils.getNavigationBarHeight(getContext()));
                        final TextView tvOk = (TextView) v.findViewById(R.id.tvOk);
                        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tvCancel);
//                        tvTitle.setText("请选择地区");
                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                areaPicker.returnData();
                                areaPicker.dismiss();
                            }
                        });

                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                areaPicker.dismiss();
                            }
                        });
                    }
                })
                .isDialog(true)
                .setDividerColor(ContextUtil.getColor(R.color.explainColor))
                .build();
        areaPicker.setPicker(mProvinceDatas, mCityDatas, mAreaDatas);//三级选择器

        Dialog mDialog = areaPicker.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            areaPicker.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }

        areaPicker.show();

    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(getContext(), "province.json");//获取assets目录下的json文件数据

        ArrayList<AreaBean> provinceDatas = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        mProvinceDatas = provinceDatas;

        for (int i = 0; i < provinceDatas.size(); i++) {//遍历省份
            ArrayList<AreaBean> cityDatas = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<AreaBean>> areaDatas = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int j = 0; j < provinceDatas.get(i).get_child().size(); j++) {//遍历该省份的所有城市
                AreaBean cityBean = provinceDatas.get(i).get_child().get(j);
                cityDatas.add(cityBean);
                ArrayList<AreaBean> tempAreaDatas = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (cityBean.get_child() == null
                        || cityBean.get_child().size() == 0) {
                    tempAreaDatas.add(new AreaBean());
                } else {
                    //构造一个list<String> 填充area的集合
                    ArrayList<AreaBean> tempAreaDatas2 = new ArrayList<>();
                    for (int k = 0; k < cityBean.get_child().size(); k++) {
                        tempAreaDatas2.add(cityBean.get_child().get(k));
                    }
                    tempAreaDatas.addAll(tempAreaDatas2);
                }
                areaDatas.add(tempAreaDatas);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            mCityDatas.add(cityDatas);

            /**
             * 添加地区数据
             */
            mAreaDatas.add(areaDatas);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        ToastUtil.s("开始解析");
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    initAreaPicker();
                    break;

                case MSG_LOAD_FAILED:
                    ToastUtil.s("解析失败");
                    break;
            }
        }
    };

    public ArrayList<AreaBean> parseData(String result) {//Gson 解析
        ArrayList<AreaBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AreaBean entity = gson.fromJson(data.optJSONObject(i).toString(), AreaBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    public JoinAreaPresenter createPresenter() {
        return new JoinAreaPresenter();
    }

    @Override
    public void joinArea(String data) {
        ToastUtil.s("提交成功");
        finish();
    }

    @Override
    public void getJoinAreaInfo(JoinAreaInfoBean data) {
        initViews(data);
    }

    private void initViews(JoinAreaInfoBean data) {
        if (data != null) {
            //状态:0=待审核,1=已通过,2=已拒绝
            int status = data.getStatus();

            etName.setText(data.getName());
            etName.setSelection(data.getName().length());
            etPhone.setText(data.getPhone());
            tvArea.setText(data.getArea_name());
            areaIdsStr = data.getId() + "";

            tvSubmit.setText(status == 0 ? "待审核"
                    : status == 1 ? "已通过"
                    : "未通过，重新提交"
            );

            etName.setEnabled(status == 2 ? true : false);
            etPhone.setEnabled(status == 2 ? true : false);
            tvArea.setEnabled(status == 2 ? true : false);
            tvSubmit.setEnabled(status == 2 ? true : false);


        }
    }
}
