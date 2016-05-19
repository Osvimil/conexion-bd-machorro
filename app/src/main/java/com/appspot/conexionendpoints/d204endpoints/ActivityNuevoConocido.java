package com.appspot.conexionendpoints.d204endpoints;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;

import android.support.v4.app.NavUtils;

import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;

public class ActivityNuevoConocido extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_frame);

        final ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {

            supportActionBar.setDisplayHomeAsUpEnabled(true);

        }

        final FragmentManager fm = getSupportFragmentManager();

        FragmentNuevoConocido fragment =

                (FragmentNuevoConocido) fm.findFragmentById(R.id.frame);

        if (fragment == null) {

            fragment = new FragmentNuevoConocido();

            fm.beginTransaction().add(R.id.frame, fragment).commit();

        }

    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(this);

            return true;

        } else {

            return super.onOptionsItemSelected(item);

        }

    }

}
