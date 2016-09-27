package com.luzkzieniewicz.gmail.pigame;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAnalytics mFirebaseAnalytics;

    public boolean running;
    public int hunger;
    public int mud;
    public int fun;
    public Date date;

    public static final int delay = 1000;

    @Override
    protected void onResume()
    {
        super.onResume();
        timeCalculation();
        running = true;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        running = false;
    }

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

        running = true;

        (new Handler()).postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(running==true)
                {
                    mud = Math.max(0, mud - 1);
                    fun = Math.max(0, fun - 1);
                    hunger = Math.max(0, hunger - 1);

                    paintButtons();
                }
                (new Handler()).postDelayed(this,delay);
            }
        },delay);

    }

    //called at the start or resume of aplication, to give an idle prizes
    public void timeCalculation()
    {
        if(date!=null)
        {
            Date current = new Date();
            long seconds = (current.getTime() - date.getTime())/1000;

            ((TextView) findViewById(R.id.debugTextView2)).setText("" + seconds);
        }
    }

    public void paintButtons()
    {
        if(running) date = new Date();


        ((TextView)findViewById(R.id.debugTextView)).setText("h: " + hunger + " m: " + mud + " f: " + fun);

        if(hunger >90) (findViewById(R.id.hungerButton)).setBackgroundColor(getResources().getColor(R.color.green));
        else if(hunger > 30) (findViewById(R.id.hungerButton)).setBackgroundColor(getResources().getColor(R.color.yellow));
        else (findViewById(R.id.hungerButton)).setBackgroundColor(getResources().getColor(R.color.red));

        if(fun >90) (findViewById(R.id.funButton)).setBackgroundColor(getResources().getColor(R.color.green));
        else if(fun > 30) (findViewById(R.id.funButton)).setBackgroundColor(getResources().getColor(R.color.yellow));
        else (findViewById(R.id.funButton)).setBackgroundColor(getResources().getColor(R.color.red));

        if(mud >90) (findViewById(R.id.mudButton)).setBackgroundColor(getResources().getColor(R.color.green));
        else if(mud > 30) (findViewById(R.id.mudButton)).setBackgroundColor(getResources().getColor(R.color.yellow));
        else (findViewById(R.id.mudButton)).setBackgroundColor(getResources().getColor(R.color.red));
    }

    public void onHungerClick(View view)
    {
        hunger = Math.min(100, hunger+10);
        paintButtons();
    }

    public void onMudClick(View view)
    {
        mud = Math.min(100, mud+10);
        paintButtons();
    }

    public void onFunClick(View view)
    {
        fun = Math.min(100, fun+10);
        paintButtons();
        //startActivity(new Intent(this, FunActivity.class));
    }
}
