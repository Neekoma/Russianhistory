package com.mnicholas.history.activities;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.mnicholas.history.R;



public class ContentActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView.loadUrl(getIntent().getStringExtra("URL"));
        getSupportActionBar().setTitle(getIntent().getStringExtra("TITLE"));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorGrey));
    }
}
