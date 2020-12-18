package com.ruanjie.donkey.ui.help.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.UseHelpBean;
import com.ruanjie.donkey.ui.help.contract.HelpListContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.help.presenter
 * 文件名:   HelpListPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/14 22:25
 * 描述:     TODO
 */
public class HelpListPresenter extends BasePresenter implements HelpListContract.Model {

    private final HelpListContract.View view;

    public HelpListPresenter(HelpListContract.View view) {
        this.view = view;
    }

    @Override
    public void helpList() {
        getParams().put("page","1");
        RetrofitClient.getService()
                .getUseHelpData(getParams())
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<List<UseHelpBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<UseHelpBean> data) {
                        view.initHelpList(data);
                    }
                });
    }
}
