package com.machinecontroler.myroomapplication;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private TextView txtName;
    private TextView txtAddress;
    private TextView txtPhone;
    private TextView txtLandmark;
    private TextView txtFragnanceusage;
    private TextView txtRefilldate;
    private TextView txtRefillMonth;
    private Intent in;
    private String name = null;
    private String clientAddress = null;
    private String clientPhone = null;
    private String clientLm = null;
    private String fm = null;
    private String refillfm = null;
        private ShareActionProvider mShareActionProvider;
    private String mForecast;
    private String mail;
    private TextView clEmail;
    private final String CATEGORY_PREFERENCE = "UserCategory";

    private LinearLayout llCall;
    //private ShareActionProvider mShareActionProvider;
    private int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
  private String mn=null;
    public DetailActivityFragment() {
        setHasOptionsMenu(true);

    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        txtName = (TextView) v.findViewById(R.id.client_Name);
        txtAddress = (TextView) v.findViewById(R.id.client_Address);
        txtPhone = (TextView) v.findViewById(R.id.client_Phoneno);
        txtLandmark = (TextView) v.findViewById(R.id.client_Landmark);
        txtFragnanceusage = (TextView) v.findViewById(R.id.client_fragusage);
        txtRefilldate = (TextView) v.findViewById(R.id.client_refill);
        clEmail = (TextView) v.findViewById(R.id.client_emailAddress);
        llCall = (LinearLayout) v.findViewById(R.id.phonecall);
        txtRefillMonth = (TextView) v.findViewById(R.id.client_refillMonth);

      DisplayCall();
        llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                clientPhone = in.getStringExtra("phonenumber");
                callIntent.setData(Uri.parse("tel:91"+clientAddress));

                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

//        SharedPreferences savenb = getContext().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editNtb = savenb.edit();
//        editNtb.putString("id", id);
//        editNtb.putString("title", title);
//        editNtb.commit();


        in = getActivity().getIntent();
  if(in !=null ) {
      name = in.getStringExtra("name");
      clientAddress = in.getStringExtra("address");
      clientPhone = in.getStringExtra("phonenumber");
      clientLm = in.getStringExtra("lm");
      fm = in.getStringExtra("fm");
      refillfm = in.getStringExtra("rd");
      mail = in.getStringExtra("email");
      mn = in.getStringExtra("month");
//      SharedPreferences savenb = getContext().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
//      SharedPreferences.Editor editNtb = savenb.edit();
//      editNtb.putString("name", name);
//      editNtb.putString("clientaddress", title);
//      editNtb.putString("title", title);
//      editNtb.putString("title", title);
//      editNtb.putString("title", title);
//      editNtb.putString("title", title);
//      editNtb.putString("title", title);

//      editNtb.commit();

      txtName.setText(name.toUpperCase());
      txtAddress.setText(clientAddress.toString());
      txtPhone.setText(clientPhone.toString());
      txtLandmark.setText(clientLm.toString());
      txtFragnanceusage.setText(fm.toString());
      txtRefilldate.setText(refillfm.toString());
      clEmail.setText(mail);
      txtRefillMonth.setText(mn);
  }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);

        MenuItem shareItem = menu.findItem(R.id.action_shareprovider);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(setupShareIntent());
        }
        else {
            Toast.makeText(getContext()," Share Provider is null !",Toast.LENGTH_SHORT).show();
        }
    }


    private Intent setupShareIntent() {
        String textToShare = "Name :" + name + "\n" + "Email ID :" + mail + "\n" + "Address :" + clientAddress + "\n" + "Mobile Number" + clientPhone + "\n" + "Client Nearest Landmark" + clientLm + "\n";
        Intent mShareIntent = new Intent(Intent.ACTION_SEND);
        mShareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
        return mShareIntent;
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int items = item.getItemId();

        switch (items) {

            case R.id.action_edit:
                Intent inDelete = new Intent(getActivity(), ClientEditActivity.class);

           //     ActivityOptions.makeCustomAnimation(getContext(), R.anim.anim, R.anim.animate1).toBundle();
                startActivity(inDelete);
                break;
        }


        return super.onOptionsItemSelected(item);


    }

    public void DisplayCall() {
        if (isReadStorageAllowed()) {
            //If permission is already having then showing the toast

            //Existing the method with return
            return;
        }
        checkAndRequestPermissions();

    }

    private boolean checkAndRequestPermissions() {
//        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
        int locationPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

        int locationFinelocation = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (locationFinelocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

        int resultFineLocation = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

}
