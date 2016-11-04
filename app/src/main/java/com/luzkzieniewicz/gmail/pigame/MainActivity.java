package com.luzkzieniewicz.gmail.pigame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        deserialize(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        running = false;
        serialize(this);
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
        paintButtons();

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
                    serialize(getApplicationContext());
                    paintButtons();
                }
                (new Handler()).postDelayed(this,delay);
            }
        },delay);

    }

    //called at the start or resume of aplication, to give an idle prizes
    public void timeCalculation()
    {
        deserialize(this);
        if(date!=null)
        {
            Date current = new Date();
            long seconds = (current.getTime() - date.getTime())/1000;

            ((TextView) findViewById(R.id.debugTextView2)).setText("" + seconds);
        }
    }

    public void paintButtons()
    {
        if(running)
        {
            if(!deserialize(this))date = new Date();
        }


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

        serialize(this);
    }

    public void onHungerClick(View view)
    {
        hunger = Math.min(100, hunger+10);
        serialize(this);
        paintButtons();
    }

    public void onMudClick(View view)
    {
        mud = Math.min(100, mud+10);
        serialize(this);
        paintButtons();
    }

    public void onFunClick(View view)
    {
        //fun = Math.min(100, fun+10);
        //serialize(this);
        //paintButtons();
        startActivity(new Intent(this, FunActivity.class));
    }

    public boolean serialize(Context c)
    {
        FileOutputStream fos = null;
        try
        {
            fos = c.openFileOutput("save", Context.MODE_PRIVATE);

            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(date);
            int[] tab = new int[3];
            tab[0] = hunger;
            tab[1] = mud;
            tab[2] = fun;
            os.writeObject(tab);
            os.close();
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deserialize(Context c)
    {
        try
        {
            FileInputStream fis = c.openFileInput("save");
            ObjectInputStream is = new ObjectInputStream(fis);
            date = (Date) is.readObject();
            int[] tab =(int[]) is.readObject();
            hunger = tab[0];
            fun = tab[1];
            mud = tab[2];
            is.close();
            fis.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
