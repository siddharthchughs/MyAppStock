package com.machinecontroler.myroomapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richie on 21-06-2017.
 */

public class UpdateStock implements Parcelable{

    private String fragname;
    private String quantity;
    private String date;

    public String getFragname() {
        return fragname;
    }

    public void setFragname(String fragname) {
        this.fragname = fragname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UpdateStock(String fragname, String quantity,String dt) {
        this.fragname = fragname;
        this.quantity = quantity;
        this.date = dt;
    }


    protected UpdateStock(Parcel in) {
        fragname = in.readString();
        quantity = in.readString();
        date = in.readString();
    }

    public static final Creator<UpdateStock> CREATOR = new Creator<UpdateStock>() {
        @Override
        public UpdateStock createFromParcel(Parcel in) {
            return new UpdateStock(in);
        }

        @Override
        public UpdateStock[] newArray(int size) {
            return new UpdateStock[size];
        }
    };

    public String getFrag() {
        return fragname;
    }

    public void setFrag(String frag) {
        this.fragname = frag;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fragname);
        dest.writeString(quantity);
        dest.writeString(date);
    }
}
