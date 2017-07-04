package com.machinecontroler.myroomapplication;

/**
 * Created by Richie on 24-09-2016.
 */

public class Login {




//    public boolean getMailAddress() {
//        return mailAddress;
//    }
//
//
//
//    public void setMailAddress(boolean mailAddress) {
//        this.mailAddress = mailAddress;
//    }

    private boolean mailAddress;
   private String password;
    private String username;
  private  String phone;

    public void setMailAddress(boolean mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isMailAddress() {
        return mailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    private String useremail;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//
//   @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeLong(positionid);
//
//    }
//
//
//
//    private Login(Parcel in){
//        positionid = in.readLong();
//    }
//
//
//    public Login(String username,long id){
//
//        this.positionid= id;
//
//    }
//    public final Creator<Login> CREATOR = new Creator<Login>() {
//        @Override
//        public Login createFromParcel(Parcel parcel) {
//            return new Login(parcel);
//        }
//
//        @Override
//        public Login[] newArray(int i) {
//            return new Login[i];
//        }
//
//    };


}
