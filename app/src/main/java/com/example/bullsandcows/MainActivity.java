package com.example.bullsandcows;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    MediaPlayer ring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ring= MediaPlayer.create(MainActivity.this,R.raw.alexander);
        ring.start();
        ring.setLooping(true);
    }

    public void Gotonewgame(View view)
    {
        Intent i = new Intent(this,Newgame.class);

        startActivity(i);
    }

    public void About(View view)
    {
        Intent i = new Intent(this,About.class);
        startActivity(i);
    }

    public void Help(View view)
    {
        Intent i = new Intent(this, HelpActivity.class);
        startActivity(i);
    }

    public void StartGameOnline(View view) {
        Intent intent = new Intent(getApplicationContext(), Online_login.class);

        startActivity(intent);
    }
    public void QuitApp(View view) {
        MainActivity.this.finish();
        ring.stop();
        ring.release();
        ring=null;
        System.exit(0);
    }
}