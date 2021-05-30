package com.example.bullsandcows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class OnlineGameActivity extends AppCompatActivity {

    TextView tvPlayer1, tvPlayer2;
    NumberPicker pos1;
    NumberPicker pos2;
    NumberPicker pos3;
    NumberPicker pos4;
    TextView oppo_tv_output;
    TextView tvDisplay;
    TextView your_tv_output;
    TextView locked;
    //TextView ans;
    int check1 = 0, check2 = 0, check3 = 0;
    int min = 1, max = 9;
    int rand1 = 0, rand2 = 0, rand3 = 0, rand4 = 0;
    int check4 = 0, count = 0, trial = 1;


    String playerSession = "";
    String userName = "";
    String otherPlayer = "";
    String loginUID = "";
    String requestType = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);

        userName = getIntent().getExtras().get("user_name").toString();
        loginUID = getIntent().getExtras().get("login_uid").toString();
        otherPlayer = getIntent().getExtras().get("other_player").toString();
        requestType = getIntent().getExtras().get("request_type").toString();
        playerSession = getIntent().getExtras().get("player_session").toString();

        /*if(requestType.equals("From")){
            tvPlayer1.setText("Your turn");
            tvPlayer2.setText("Your turn");
            myRef.child("playing").child(playerSession).child("turn").setValue(userName);
            //setEnableClick(true);
        }else{
            tvPlayer1.setText(otherPlayer + "\'s turn");
            tvPlayer2.setText(otherPlayer + "\'s turn");
            myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);
            //setEnableClick(false);
        }

        /*myRef.child("playing").child(playerSession).child("turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    String value = (String) dataSnapshot.getValue();
                    if(value.equals(userName)) {
                        tvPlayer1.setText("Your turn");
                        tvPlayer2.setText("Your turn");
                        //activePlayer = 1;
                    }else if(value.equals(otherPlayer)){
                        tvPlayer1.setText(otherPlayer + "\'s turn");
                        tvPlayer2.setText(otherPlayer + "\'s turn");
                        //activePlayer = 2;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        initial();
    }

    public void initial()
    {
        Random random = new Random();
        rand1 = random.nextInt(max - min + 1) + min;
        rand2 = random.nextInt(10);
        rand3 = random.nextInt(10);
        rand4 = random.nextInt(10);

        pos1 = (NumberPicker) findViewById(R.id.editText1);
        pos2 = (NumberPicker) findViewById(R.id.editText2);
        pos3 = (NumberPicker) findViewById(R.id.editText3);
        pos4 = (NumberPicker) findViewById(R.id.editText4);
        pos1.setMaxValue(9);
        pos1.setMinValue(0);
        pos1.setValue(1);
        pos2.setMaxValue(9);
        pos2.setMinValue(0);
        pos2.setValue(1);
        pos2.setMaxValue(9);
        pos2.setMinValue(0);
        pos3.setValue(1);
        pos3.setMaxValue(9);
        pos3.setMinValue(0);
        pos3.setValue(1);
        pos4.setMaxValue(9);
        pos4.setMinValue(0);
        pos4.setValue(1);
        tvDisplay = findViewById(R.id.tvDisplay);
        your_tv_output=findViewById(R.id.your_tv_output);
        oppo_tv_output =findViewById(R.id.oppo_tv_output);
        locked=(TextView) findViewById(R.id.locked);
        locked.setText("Locking");
        Button b1 = (Button) findViewById(R.id.lock_btn);

        tvDisplay.setText("Guess Number");
    }
    public void Clear() {
        pos1.setValue(1);
        pos2.setValue(1);
        pos3.setValue(1);
        pos4.setValue(1);
        secret = new
                StringBuilder();
        guess = new
                StringBuilder();
        output=new StringBuilder();
        your_tv_output.setText(output);
        oppo_tv_output.setText(output);
        count=0;
        trial=1;
        initial();
    }
    StringBuilder secret = new
            StringBuilder("");
    StringBuilder guess = new
            StringBuilder("");
    StringBuilder output=new StringBuilder();

    public void click(View view) {
        if(trial==0)
            return;
        check1 = pos1.getValue();
        if (check1 != 0)     //size as per your requirement
        {
            pos1.requestFocus();
        }
        check2 = pos2.getValue();
        check3 = pos3.getValue();
        check4 = pos4.getValue();
        // Secret Key generation
        secret.append(rand1);
        secret.append(rand2);
        secret.append(rand3);
        secret.append(rand4);
        pos1.requestFocus();
        trial=trial-1;
        locked.setText("Locked "+secret);
    }

}