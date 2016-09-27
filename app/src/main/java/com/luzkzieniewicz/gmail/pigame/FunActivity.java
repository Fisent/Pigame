package com.luzkzieniewicz.gmail.pigame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FunActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun);
    }

    public void onStartButton(View view)
    {
        startActivity(new Intent(this,GameActivity.class));
    }
}
