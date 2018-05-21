package com.example.b17albbe.b17albbe_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PresidentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_president);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String inName = intent.getStringExtra("NAME");
        String inLocation = intent.getStringExtra("LOCATION");
        String inDate = intent.getStringExtra("DATE");
        String inAchievement = intent.getStringExtra("ACHIEVEMENT");
        String inServed = intent.getStringExtra("SERVED");

        President president=new President(inName,inLocation,inDate,inAchievement,inServed);
        Toast.makeText(getApplicationContext(), president.info() , Toast.LENGTH_LONG).show();
        TextView textID = (TextView)findViewById(R.id.textID);

        textID.setText(president.toString());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
