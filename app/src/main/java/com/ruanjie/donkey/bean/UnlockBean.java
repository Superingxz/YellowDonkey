package com.ruanjie.donkey.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   UnlockBean
 * 创建者:    QJM
 * 创建时间: 2019/8/10 21:49
 * 描述:     TODO
 */
public class UnlockBean implements Parcelable {


    /**
     * tpye : 0
     */

    @SerializedName("type")
    private int mType;

    protected UnlockBean(Parcel in) {
        mType = in.readInt();
    }

    public static final Creator<UnlockBean> CREATOR = new Creator<UnlockBean>() {
        @Override
        public UnlockBean createFromParcel(Parcel in) {
            return new UnlockBean(in);
        }

        @Override
        public UnlockBean[] newArray(int size) {
            return new UnlockBean[size];
        }
    };

    public int getTpye() {
        return mType;
    }

    public void setTpye(int tpye) {
        this.mType = tpye;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mType);
    }
}
