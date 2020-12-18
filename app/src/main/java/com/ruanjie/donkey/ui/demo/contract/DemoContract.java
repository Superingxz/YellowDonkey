package com.ruanjie.donkey.ui.demo.contract;

import com.ruanjie.donkey.bean.ImageBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;

/**
 * @author Moligy
 * @date 2019/7/22.
 */
public interface DemoContract {
    //这里可以继承IBaseRefreshDispaly
    interface Display extends IBaseDisplay {
        void getData(List<ImageBean> imageBeans);
    }

    interface Presenter extends IBasePresenter{
        void getData();
    }
}
