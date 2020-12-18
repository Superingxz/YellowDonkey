package com.ruanjie.donkey.ui.message.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.MessageListBean;
import com.ruanjie.donkey.ui.message.MyMessageActivity;
import com.ruanjie.donkey.ui.message.contract.MyMessageContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.message.presenter
 * 文件名:   MyMessagePresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/13 1:00
 * 描述:     TODO
 */
public class MyMessagePresenter extends BasePresenter implements MyMessageContract.Model {


    @Override
    public void getMessageList(int type, int page, int pageSize) {
        RetrofitClient.getService()
                .getMessageList(type, page, pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<MessageListBean>>() {
                    @Override
                    public void onSuccess(List<MessageListBean> data) {
                        ((MyMessageActivity) mView).getMessageList(data);
                    }
                });
    }

    @Override
    public void deleteMessage(int id) {
        RetrofitClient.getService()
                .deleteMessage(id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((MyMessageActivity) mView).deleteMessage(data);
                    }
                });
    }

    @Override
    public void getMessageDetail(int id) {
        RetrofitClient.getService()
                .getMessageDetail(id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<MessageListBean>() {
                    @Override
                    public void onSuccess(MessageListBean data) {
                        ((MyMessageActivity) mView).getMessageDetail(data);
                    }
                });
    }


}
