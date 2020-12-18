package com.ruanjie.donkey.ui.customer;

import android.content.Context;
import android.content.Intent;

import com.ruanjie.donkey.R;
import com.softgarden.baselibrary.base.BaseActivity;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.customer
 * 文件名:   CustomerServiceActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/12 21:55
 * 描述:     TODO
 */
public class CustomerServiceActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, CustomerServiceActivity.class));
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initialize() {

    }
}
