package com.machinecontroler.myroomapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richie on 25-05-2017.
 */

public class Stock implements Parcelable{


    String stock_id;
    String stock_item_type;

    public Stock(String stock_id, String stock_item_type) {
        this.stock_id = stock_id;
        this.stock_item_type = stock_item_type;
    }

    protected Stock(Parcel in) {
        stock_id = in.readString();
        stock_item_type = in.readString();
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getStock_item_type() {
        return stock_item_type;
    }

    public void setStock_item_type(String stock_item_type) {
        this.stock_item_type = stock_item_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stock_id);
        dest.writeString(stock_item_type);
    }
}
