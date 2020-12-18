package com.ruanjie.donkey.ui.help;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.HelpListAdapter;
import com.ruanjie.donkey.bean.UseHelpBean;
import com.ruanjie.donkey.ui.help.contract.HelpListContract;
import com.ruanjie.donkey.ui.help.presenter.HelpListPresenter;
import com.softgarden.baselibrary.base.RefreshActivity;

import java.util.List;

import butterknife.BindView;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.help
 * 文件名:   HelpListActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/14 17:32
 * 描述:     TODO
 */
public class HelpListActivity extends RefreshActivity<HelpListPresenter> implements HelpListContract.View {

    @BindView(R.id.rv_help_List)
    RecyclerView rvHelpList;
    private String title;
    private String url;
    private HelpListAdapter adapter;

    public static void start(Context context) {
        // starter.putExtra(F);
        context.startActivity(new Intent(context, HelpListActivity.class));
    }

    @Override
    public HelpListPresenter createPresenter() {
        return new HelpListPresenter(this);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle(R.string.user_guide);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activty_help_list;
    }

    @Override
    protected void initialize() {

    }


    @Override
    public void initHelpList(List<UseHelpBean> bean) {

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHelpList.setLayoutManager(manager);
        adapter = new HelpListAdapter(getContext());
        rvHelpList.setAdapter(adapter);
        setLoadData(adapter,bean);
    }


    @Override
    public void loadData() {
        getPresenter().helpList();
    }
}
