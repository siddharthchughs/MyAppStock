package com.machinecontroler.myroomapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richie on 25-05-2017.
 */

public class Fragnance implements Parcelable {

    String name;
    String quantity;


    public Fragnance(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    protected Fragnance(Parcel in) {
        name = in.readString();
        quantity = in.readString();
    }

    public static final Creator<Fragnance> CREATOR = new Creator<Fragnance>() {
        @Override
        public Fragnance createFromParcel(Parcel in) {
            return new Fragnance(in);
        }

        @Override
        public Fragnance[] newArray(int size) {
            return new Fragnance[size];
        }
    };


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                dest.writeString(name);
        dest.writeString(quantity);
    }
}
