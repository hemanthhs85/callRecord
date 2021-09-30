package com.qspiders.callrecord.splash;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qspiders.callrecord.R;
import com.qspiders.callrecord.callList.view.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        try {
            Process root = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }
        permissioncheck();

    }

    private void permissioncheck() {


        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("READ");
        if (!addPermission(permissionsList, Manifest.permission.PROCESS_OUTGOING_CALLS))
            permissionsNeeded.add("PROCESSOUTCALL");
        if (!addPermission(permissionsList, Manifest.permission.READ_CALL_LOG))
            permissionsNeeded.add("CALLLOG");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("WRITE");
        if (!addPermission(permissionsList,Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("READCONTACTS");
        if(!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("phonestate");
        if(!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
            permissionsNeeded.add("recordaudio");
        if(!addPermission(permissionsList, Manifest.permission.CAPTURE_AUDIO_OUTPUT))
            permissionsNeeded.add("audiooutput");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
//                String message = "You need to grant access to " + permissionsNeeded.get(0);
                String message = "You need to grant access to Permissions";

                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (Build.VERSION.SDK_INT >= 23) {
                                    // Marshmallow+
                                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);


                                } else {
                                    // Pre-Marshmallow
                                }

                            }
                        });
                return;
            }

            if (Build.VERSION.SDK_INT >= 23) {
                // Marshmallow+
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);


            } else {
                // Pre-Marshmallow

            }

            return;
        }else {
            // Toast.makeText(this,"Permission",Toast.LENGTH_LONG).show();
            LaunchApp();
        }

        //insertDummyContact();
    }

    public void LaunchApp() {
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 10 seconds
                    sleep(1000);

                    if (getIntent().getStringExtra("loginApi") != null) {
                        Toast.makeText(SplashScreen.this, getIntent().getStringExtra("userName"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        intent.putExtra("loginApi", "loginApi");
                        intent.putExtra("userName",getIntent().getStringExtra("userName"));
                        intent.putExtra("token",getIntent().getStringExtra("token"));
                        finish();
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }



//                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
//                    finish();
//                    startActivity(intent);

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {

        Boolean cond;
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    //  return false;

                    cond = false;
            }
            //  return true;

            cond = true;


        } else {
            // Pre-Marshmallow
            cond = true;
        }

        return cond;

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashScreen.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == 23) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Permission Needed To Run The App", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {


            Map<String, Integer> perms = new HashMap<String, Integer>();
            // Initial
            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.PROCESS_OUTGOING_CALLS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_CALL_LOG, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_CONTACTS,PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_PHONE_STATE,PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.RECORD_AUDIO,PackageManager.PERMISSION_GRANTED);

            //Toast.makeText(SplashScreen.this, " Permissions are jddddd", Toast.LENGTH_SHORT).show();
            // Fill with results
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            // Check for ACCESS_FINE_LOCATION
            if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.PROCESS_OUTGOING_CALLS) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
                // All Permissions Granted
                // Here start the activity
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        if (getIntent().getStringExtra("loginApi") != null) {
                            Toast.makeText(SplashScreen.this, getIntent().getStringExtra("userName"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            intent.putExtra("loginApi", "loginApi");
                            intent.putExtra("userName",getIntent().getStringExtra("userName"));
                            intent.putExtra("token",getIntent().getStringExtra("token"));
                            finish();
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }



//                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
//                        startActivity(i);
//                        finish();

                    }

                }, 1000);

            } else {
                // Permission Denied
                Toast.makeText(SplashScreen.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                        .show();

                finish();
            }

        }
    }
}
