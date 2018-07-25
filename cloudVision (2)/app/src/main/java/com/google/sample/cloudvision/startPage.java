package com.google.sample.cloudvision;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

/**
 * Created by yuhu on 11/28/16.
 */

public class startPage extends AppCompatActivity
{

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        Button playBtn = (Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent playList = new Intent(startPage.this, XMLWriter.class);
                Intent playList = new Intent(startPage.this, MainActivity.class);
                startPage.this.startActivity(playList);
            }
        });
    }

}
