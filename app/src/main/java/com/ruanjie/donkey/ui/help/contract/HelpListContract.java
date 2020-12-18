package com.ruanjie.donkey.ui.help.contract;

import com.ruanjie.donkey.bean.UseHelpBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.help.contract
 * 文件名:   HelpListContract
 * 创建者:    QJM
 * 创建时间: 2019/8/14 22:25
 * 描述:     TODO
 */
public interface HelpListContract {

    interface View extends IBaseDisplay {
        void initHelpList(List<UseHelpBean> bean);
    }

    interface Model extends IBasePresenter {
        void helpList();
    }
}
