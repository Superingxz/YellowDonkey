package com.ruanjie.donkey.ui.main;

import android.os.Bundle;

import com.ruanjie.donkey.ui.map.TencentMapFragment;

/**
 * @author by DELL
 * @date on 2018/2/24
 * @describe
 */

public class MainFragment extends TencentMapFragment {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
