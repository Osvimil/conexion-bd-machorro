package com.appspot.conexionendpoints.d204endpoints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;


public class ActivityConocidos extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_frame);

        final FragmentManager fm = getSupportFragmentManager();

        FragmentConocidos fragment =

                (FragmentConocidos) fm.findFragmentById(R.id.frame);

        if (fragment == null) {

            fragment = new FragmentConocidos();

            fm.beginTransaction().add(R.id.frame, fragment).commit();

        }

    }

}
