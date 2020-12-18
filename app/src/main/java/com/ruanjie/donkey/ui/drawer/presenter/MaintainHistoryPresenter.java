package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.MainTainBean;
import com.ruanjie.donkey.bean.TravelDetailBean;
import com.ruanjie.donkey.bean.TravelStatisticsBean;
import com.ruanjie.donkey.ui.drawer.MaintainHistoryActivity;
import com.ruanjie.donkey.ui.drawer.MyTravelActivity;
import com.ruanjie.donkey.ui.drawer.contract.MaintainHistoryContract;
import com.ruanjie.donkey.ui.drawer.contract.MyTravelContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   LoginPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/29 11:28
 * 描述:     TODO
 */
public class MaintainHistoryPresenter extends BasePresenter
        implements MaintainHistoryContract.Model {

    @Override
    public void getMainHistorys(int page, int pageSize) {
        RetrofitClient.getService()
                .getMainHistorys(page,pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<MainTainBean>>() {
                    @Override
                    public void onSuccess(List<MainTainBean> data) {
                        ((MaintainHistoryActivity) mView).getMainHistorys(data);
                    }
                });
    }
}
