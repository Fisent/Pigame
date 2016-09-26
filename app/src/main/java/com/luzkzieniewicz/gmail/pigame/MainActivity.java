package com.luzkzieniewicz.gmail.pigame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //TODO
        //change the number when app will be added
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void onHungerClick(View view)
    {

    }

    public void onMudClick(View view)
    {

    }

    public void onFunClick(View view)
    {
        startActivity(new Intent(this, FunActivity.class));
    }
}
