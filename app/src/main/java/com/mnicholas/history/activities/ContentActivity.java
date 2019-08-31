package com.mnicholas.history.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.mnicholas.history.R;

import com.google.android.gms.ads.InterstitialAd;



public class ContentActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd; // Рекламный блок

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
