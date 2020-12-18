package com.ruanjie.donkey.ui.message.contract;

import com.ruanjie.donkey.bean.MessageListBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.message.contract
 * 文件名:   MyMessageContract
 * 创建者:    QJM
 * 创建时间: 2019/8/13 1:03
 * 描述:     TODO
 */
public interface MyMessageContract {

    interface View extends IBaseDisplay {
        void getMessageList(List<MessageListBean> data);

        void deleteMessage(String data);

        void getMessageDetail(MessageListBean data);
    }

    interface Model extends IBasePresenter {
        void getMessageList(int type, int page, int pageSize);

        void deleteMessage(int id);

        void getMessageDetail(int id);

    }


}
