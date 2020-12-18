package com.ruanjie.donkey.utils;

import android.view.View;

public abstract class onClickEvent implements View.OnClickListener {
    private static long lastTime;

    public abstract void singleClick(View v);
    private long delay;

    public onClickEvent(long delay) {
        this.delay = delay;
    }

    @Override
    public void onClick(View v) {
        if(onMoreClick(v)){
            return;
        }
        singleClick(v);
    }

    public boolean onMoreClick(View v){
        boolean flag = false;
        long time = System.currentTimeMillis()-lastTime;
        if(time < delay){
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }
}
