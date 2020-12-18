package com.ruanjie.toolsdk.ui.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.toolsdk.ui.icon
 * 文件名:   FontModule
 * 创建者:    QJM
 * 创建时间: 2019/7/23 16:15
 * 描述:     TODO
 */
public class FontModule implements IconFontDescriptor {

    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return Icons.values();
    }
}
