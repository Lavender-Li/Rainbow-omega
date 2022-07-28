package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Userguide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userguide);
    }

    public void startDetection(View view){
        startActivity(new Intent(this, ColorDetectionActivity.class));
    }

    public void startFeedback(View view){
        startActivity(new Intent(this, Feedback.class));
    }

    public void startMain(View view){
        startActivity(new Intent(this, MainPage.class));
    }
}