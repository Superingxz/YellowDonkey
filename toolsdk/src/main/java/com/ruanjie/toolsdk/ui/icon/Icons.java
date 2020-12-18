package com.ruanjie.toolsdk.ui.icon;

import com.joanzapata.iconify.Icon;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.toolsdk.ui.icon
 * 文件名:   Icons
 * 创建者:    QJM
 * 创建时间: 2019/7/23 16:12
 * 描述:     TODO
 */
public enum Icons implements Icon {
    icon_scan('\ue602'),
    icon_ali_pay('\ue606'),
    icon_weixin('\ue73b');

    private char character;

    Icons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }}
