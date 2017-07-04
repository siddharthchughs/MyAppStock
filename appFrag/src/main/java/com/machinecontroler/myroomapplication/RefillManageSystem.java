package com.machinecontroler.myroomapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richie on 23-05-2017.
 */

public class RefillManageSystem implements Parcelable{
    
    String msid;
    String ms_title;


    public RefillManageSystem(String msid, String ms_title) {
        this.msid = msid;
        this.ms_title = ms_title;
    }

    protected RefillManageSystem(Parcel in) {
        msid = in.readString();
        ms_title = in.readString();
    }

    public static final Creator<RefillManageSystem> CREATOR = new Creator<RefillManageSystem>() {
        @Override
        public RefillManageSystem createFromParcel(Parcel in) {
            return new RefillManageSystem(in);
        }

        @Override
        public RefillManageSystem[] newArray(int size) {
            return new RefillManageSystem[size];
        }
    };

    public String getMsid() {
        return msid;
    }

    public void setMsid(String msid) {
        this.msid = msid;
    }

    public String getMs_title() {
        return ms_title;
    }

    public void setMs_title(String ms_title) {
        this.ms_title = ms_title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msid);
        dest.writeString(ms_title);
    }
}
