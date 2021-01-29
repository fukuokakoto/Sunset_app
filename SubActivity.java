package com.nifcloud.user;

//import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import android.support.v7.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        int imageId = intent.getIntExtra("IMAGEID", 0);

        ImageView imageView = findViewById(R.id.image_view);
        imageView.setImageResource(imageId);
    }
}