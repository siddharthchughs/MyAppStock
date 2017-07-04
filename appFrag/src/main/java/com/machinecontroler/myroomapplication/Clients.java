package com.machinecontroler.myroomapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richie on 26-04-2017.
 */

class Clients implements Parcelable{


    String clientID;
    String clientName;
    String clientAddress;
    String clientUsing_Fragnance;
    String clientPhoneNumber;
    String clientEmailid;
    String clientMachinenumber;
    String  date;
    String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    String lmark;

    public String getLmark() {
        return lmark;
    }

    public void setLmark(String lmark) {
        this.lmark = lmark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClientMachinenumber() {
        return clientMachinenumber;
    }

    public void setClientMachinenumber(String clientMachinenumber) {
        this.clientMachinenumber = clientMachinenumber;
    }

    public Clients(String clname,String claddrs,String phoneno,String clemail,String clpmachine,String clufrag,String dt,String lmk,String mn){

//      this.clientID = clid;
      this.clientName = clname;
      this.clientAddress= claddrs;
      this.clientUsing_Fragnance  = clufrag;
      this.clientMachinenumber= clpmachine;
      this.clientPhoneNumber= phoneno;
      this.clientEmailid = clemail;
      this.date = dt;
        this.lmark = lmk;
        this.month = mn;

    }





    protected Clients(Parcel in) {
        clientID = in.readString();
        clientName = in.readString();
        clientAddress = in.readString();
        clientUsing_Fragnance = in.readString();
        clientPhoneNumber = in.readString();
        clientEmailid = in.readString();
     date = in.readString();
        lmark = in.readString();
        month = in.readString();
    }

    public static final Creator<Clients> CREATOR = new Creator<Clients>() {
        @Override
        public Clients createFromParcel(Parcel in) {
            return new Clients(in);
        }

        @Override
        public Clients[] newArray(int size) {
            return new Clients[size];
        }
    };

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientUsing_Fragnance() {
        return clientUsing_Fragnance;
    }

    public void setClientUsing_Fragnance(String clientUsing_Fragnance) {
        this.clientUsing_Fragnance = clientUsing_Fragnance;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientEmailid() {
        return clientEmailid;
    }

    public void setClientEmailid(String clientEmailid) {
        this.clientEmailid = clientEmailid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(clientID);
        dest.writeString(clientName);
        dest.writeString(clientAddress);
        dest.writeString(clientUsing_Fragnance);
        dest.writeString(clientPhoneNumber);
        dest.writeString(clientEmailid);
        dest.writeString(date);
        dest.writeString(lmark);
        dest.writeString(month);
    }
}
