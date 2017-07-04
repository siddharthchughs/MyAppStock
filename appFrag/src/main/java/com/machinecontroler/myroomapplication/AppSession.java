package com.machinecontroler.myroomapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by SK-Accocoate on 09/26/2015.
 */
public class AppSession {

    public static int value;
    private SharedPreferences prefs;

    private static AppSession session;

    public static AppSession getInstance(Context cntx) {
        if (session == null)
            session = new AppSession(cntx);
        return session;
    }

    public AppSession(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUserName(String username) {
        prefs.edit().putString("name", username).commit();
    }

    public String getUserName() {
        String username = prefs.getString("name", "");
        return username;
    }

    public void setPassword(String password) {
        prefs.edit().putString("password", password).commit();
    }

    public String getPassword() {
        String password = prefs.getString("password", "");
        return password;
    }

    public void setDevicetokn(String devicetokn) {
        prefs.edit().putString("devicetokn", devicetokn).commit();
    }

    public String getDeviceToken() {
        String devicetokn = prefs.getString("devicetokn", "");
        return devicetokn;
    }

    public void setMainToken(String mainToken) {
        prefs.edit().putString("main_token", mainToken).commit();
    }

    public String getMainToken() {
        String mainToken = prefs.getString("main_token", "");
        return mainToken;
    }

    public void setFcmToken(String devicetokn) {
        prefs.edit().putString("device_token", devicetokn).commit();
    }

    public String getFcmToken() {
        String devicetokn = prefs.getString("device_token", "");
        return devicetokn;
    }


    public int getUserId() {
        int id = prefs.getInt("id", 0);
        return id;
    }

    public void setUserId(int id) {
        prefs.edit().putInt("id", id).commit();
    }


    public void setMobileNumber(String mibile) {
        prefs.edit().putString("mobile", mibile).commit();
    }

    public String getMobileNumber() {
        String mibile = prefs.getString("mobile", "");
        return mibile;
    }

    public void setFirstName(String firstName) {
        prefs.edit().putString("name", firstName).commit();
    }

    public String getFirstName() {
        String firstName = prefs.getString("name", "");
        return firstName;
    }

    public void setLastName(String lastName) {
        prefs.edit().putString("lastName", lastName).commit();
    }

    public String getLastName() {
        String lastName = prefs.getString("lastName", "");
        return lastName;
    }

    public void setImageUrl(String url) {
        prefs.edit().putString("url", url).commit();
    }

    public String getImageUrl() {
        String url = prefs.getString("url", "");
        return url;
    }

    public void clear() {
        prefs.edit().clear().commit();

    }

    public void setHasLoging(boolean value) {
        prefs.edit().putBoolean("hasLoging", value).commit();
    }

    public boolean getHasLoging() {
        boolean hasLoging = prefs.getBoolean("hasLoging", false);
        return hasLoging;
    }

    public void setSkipped(boolean value) {
        prefs.edit().putBoolean("skipped", value).commit();
    }

    public boolean getSkipped() {
        boolean skip = prefs.getBoolean("skipped", false);
        return skip;
    }
    public void set_sign_in(boolean value) {
        prefs.edit().putBoolean("keep_sign_in", value).commit();
    }

    public boolean get_sign_in() {
        boolean skip = prefs.getBoolean("keep_sign_in", false);
        return skip;
    }


    public void setEmail(String email) {
        prefs.edit().putString("email", email).commit();
    }


    public void setPhone(String mobile){
        prefs.edit().putString("phone", mobile).commit();
    }


    public String getEmail() {
        String username = prefs.getString("email", "");
        return username;
    }


}