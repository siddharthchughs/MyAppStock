package com.machinecontroler.myroomapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richie on 12-05-2017.
 */

public class Supervisor implements Parcelable{

    String supervisor_id;
    String supervisor_name;
    String supervisor_ph;
    String supervisor_mail;

    public Supervisor(String supervisor_id, String supervisor_name, String supervisor_ph, String supervisor_mail) {
        this.supervisor_id = supervisor_id;
        this.supervisor_name = supervisor_name;
        this.supervisor_ph = supervisor_ph;
        this.supervisor_mail = supervisor_mail;
    }

    protected Supervisor(Parcel in) {
        supervisor_id = in.readString();
        supervisor_name = in.readString();
        supervisor_ph = in.readString();
        supervisor_mail = in.readString();
    }

    public static final Creator<Supervisor> CREATOR = new Creator<Supervisor>() {
        @Override
        public Supervisor createFromParcel(Parcel in) {
            return new Supervisor(in);
        }

        @Override
        public Supervisor[] newArray(int size) {
            return new Supervisor[size];
        }
    };

    public String getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public String getSupervisor_name() {
        return supervisor_name;
    }

    public void setSupervisor_name(String supervisor_name) {
        this.supervisor_name = supervisor_name;
    }

    public String getSupervisor_ph() {
        return supervisor_ph;
    }

    public void setSupervisor_ph(String supervisor_ph) {
        this.supervisor_ph = supervisor_ph;
    }

    public String getSupervisor_mail() {
        return supervisor_mail;
    }

    public void setSupervisor_mail(String supervisor_mail) {
        this.supervisor_mail = supervisor_mail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(supervisor_id);
        dest.writeString(supervisor_name);
        dest.writeString(supervisor_ph);
        dest.writeString(supervisor_mail);
    }
}
