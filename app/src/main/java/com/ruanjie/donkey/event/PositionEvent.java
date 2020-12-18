package com.ruanjie.donkey.event;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.event
 * 文件名:   PositionEvent
 * 创建者:    QJM
 * 创建时间: 2019/8/16 18:46
 * 描述:     TODO
 */
public class PositionEvent {
    private int status;

    public PositionEvent(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
