package com.example.bullsandcows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OnlineGameActivity extends AppCompatActivity {



    String sec1;
    String sec2="";

    ArrayList<Integer> Player1 = new ArrayList<Integer>();
    ArrayList<Integer> Player2 = new ArrayList<Integer>();

    ArrayAdapter arrayAdapter1;
    ArrayAdapter arrayAdapter2;
    ArrayList<String> messages1=new ArrayList<>();
    ArrayList<String> messages2=new ArrayList<>();

    StringBuilder secret1 = new
            StringBuilder("");
    StringBuilder guess1 = new
            StringBuilder("");
    StringBuilder output1=new StringBuilder();

    StringBuilder guess = new
            StringBuilder();

    StringBuilder secret2 = new
            StringBuilder("");
    StringBuilder guess2 = new
            StringBuilder("");
    StringBuilder output2=new StringBuilder();

    TextView tvPlayer1, tvPlayer2;
    TextView turn;
    NumberPicker pos1;
    NumberPicker pos2;
    NumberPicker pos3;
    NumberPicker pos4;
    TextView oppo_tv_output;
    TextView tvDisplay;
    TextView your_tv_output;
    TextView locked;

    ListView listView1;
    ListView listView2;

    Button delbtn;
    Button confirm;
    Button send;
    Button calc;
    Button lock;
    //TextView ans;
    int check1 = 0, check2 = 0, check3 = 0;
    int min = 1, max = 9;
    int rand1 = 0, rand2 = 0, rand3 = 0, rand4 = 0;
    int check4 = 0, count = 0, trial = 1;
    int countb=0;
    int countc=0;

    EditText sendtext;

    String playerSession = "";
    String userName = "";
    String otherPlayer = "";
    String loginUID = "";
    String requestType = "";

    int gamestate=0;
    int activePlayer=1;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);

        initial1();


        userName = getIntent().getExtras().get("user_name").toString();
        loginUID = getIntent().getExtras().get("login_uid").toString();
        otherPlayer = getIntent().getExtras().get("other_player").toString();
        requestType = getIntent().getExtras().get("request_type").toString();
        playerSession = getIntent().getExtras().get("player_session").toString();

        Intent intent = getIntent();


        /////////////////Lock Button Function ////////////////////////

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sec1.isEmpty())
                {
                    Toast.makeText(OnlineGameActivity.this,"Lock Your Number",Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> messageData = new HashMap<>();
                    messageData.put("Sender", userName);
                    messageData.put("reciever", otherPlayer);
                    messageData.put("message", sec1);


                    myRef.child("Playing").child(playerSession).child("lock").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            int count;
                            if (snapshot.exists()) {
                                count = (int) (snapshot.getChildrenCount() + 1);
                            } else {
                                count = 1;
                            }
                            myRef.child("Playing").child(playerSession).child("lock").child(String.valueOf(count)).setValue(messageData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        confirm.setText("CONFIRMED");
                                        if(requestType.equals("from"))
                                        {
                                            turn.setText("Your Turn");
                                        }
                                        else
                                        {
                                            turn.setText("Opponent Turn");
                                        }
                                        numberview();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(OnlineGameActivity.this, "Error in Locking number" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        myRef.child("Playing").child(playerSession).child("lock").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                //messages1.clear();
                //messages2.clear();
                if(snapshot.exists())
                {

                    for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                        if (dataSnapshot.child("reciever").getValue().toString().equals(userName)) {
                            String x = dataSnapshot.child("message").getValue().toString();
                                sec2 = x;
                                System.out.println(sec2);
                            //if (!dataSnapshot.child("Sender").getValue().toString().equals(userName)) {
                                //sec2 =  + message;
                            //}
                            //messages2.add(message);
                        }

                        /*if (dataSnapshot.child("Sender").getValue().toString().equals(userName))
                        {
                            String message = dataSnapshot.child("message").getValue().toString();
                            /*if (!dataSnapshot.child("reciever").getValue().toString().equals(userName)) {
                                message = ">>>" + message;
                            }*/
                           // messages1.add(message);
                        //}

                    }

                    //arrayAdapter1 =new ArrayAdapter(OnlineGameActivity.this, R.layout.listview_textlayout,messages1);
                    //listView1.setAdapter(arrayAdapter1);

                    //myRef.child("Playing").child(playerSession).child("game").child(String.valueOf(count)).removeValue();


                    //arrayAdapter2 =new ArrayAdapter(OnlineGameActivity.this, R.layout.listview_textlayout,messages2);
                    //listView2.setAdapter(arrayAdapter2);

                }
                else
                {
                    Toast.makeText(OnlineGameActivity.this,"Confirm your secret before GUESS",Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        myRef.child("Playing").child(playerSession).child("lock").removeValue();

        /////////////  Send button Function ///////////////////////

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendtext.getText().toString().isEmpty())
                {
                    Toast.makeText(OnlineGameActivity.this,"Write a message",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Map<String,Object> messageData=new HashMap<>();
                    messageData.put("Sender",userName);
                    messageData.put("reciever",otherPlayer);
                    messageData.put("message",sendtext.getText().toString());


                    myRef.child("Playing").child(playerSession).child("game").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            int count;
                            if(snapshot.exists())
                            {
                                count=(int) (snapshot.getChildrenCount()+1);
                            }
                            else
                            {
                                count=1;
                            }
                            myRef.child("Playing").child(playerSession).child("game").child(String.valueOf(count)).setValue(messageData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            sendtext.setText("");
                                        }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(OnlineGameActivity.this,"Error in sending Message"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        messages1.clear();

        myRef.child("Playing").child(playerSession).child("game").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                messages1.clear();
                messages2.clear();
                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                        if (dataSnapshot.child("reciever").getValue().toString().equals(userName))
                        {
                            String message = dataSnapshot.child("message").getValue().toString();
                            if (!dataSnapshot.child("Sender").getValue().toString().equals(userName)) {
                                message = ">>>" + message;
                            }
                            messages2.add(0,message);
                        }

                        if (dataSnapshot.child("Sender").getValue().toString().equals(userName))
                        {
                            String message = dataSnapshot.child("message").getValue().toString();
                            /*if (!dataSnapshot.child("reciever").getValue().toString().equals(userName)) {
                                message = ">>>" + message;
                            }*/
                            messages1.add(0,message);
                        }

                    }

                    arrayAdapter1 =new ArrayAdapter(OnlineGameActivity.this, R.layout.listview_textlayout,messages1);
                    listView1.setAdapter(arrayAdapter1);

                    myRef.child("Playing").child(playerSession).child("game").child(String.valueOf(count)).removeValue();


                    arrayAdapter2 =new ArrayAdapter(OnlineGameActivity.this, R.layout.listview_textlayout,messages2);
                    listView2.setAdapter(arrayAdapter2);

                }
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




        //myRef.child("Playing").child(playerSession).removeValue();

        //tvPlayer1 = (TextView) findViewById(R.id.tvPlayer1);

        /*boolean flag=false;
        if(requestType.equals("From")){

            Selectsecret1();
            tvPlayer1.setText(userName + "\'s turn");
            //tvPlayer2.setText(otherPlayer +" turn");
            myRef.child("playing").child(playerSession).child("turn").setValue(userName);
            //setEnableClick(true);
        }else{
            Selectsecret2();
            //System.out.println(sec2);
            tvPlayer1.setText(otherPlayer + "\'s turn");
            myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);
            //setEnableClick(false);
        }

        myRef.child("playing").child(playerSession).child("turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    String value = (String) dataSnapshot.getValue();
                    if(value.equals(userName)) {
                        tvPlayer1.setText(userName+ "\'s turn");
                        //setEnableClick(true);
                        activePlayer = 1;
                    }else if(value.equals(otherPlayer)){
                        tvPlayer1.setText(otherPlayer + "\'s turn");
                        //setEnableClick(true);
                        activePlayer = 2;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myRef.child("playing").child(playerSession).child("game")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            Player1.clear();
                            Player2.clear();
                            activePlayer = 2;
                            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                            if(map != null){
                                String value = "";
                                String firstPlayer = userName;
                                for(String key:map.keySet()){
                                    value = (String) map.get(key);
                                    if(value.equals(userName)){
                                        //activePlayer = myGameSign.equals("X")?1:2;
                                        activePlayer = 2;
                                    }else{
                                        //activePlayer = myGameSign.equals("X")?2:1;
                                        activePlayer = 1;
                                    }
                                    firstPlayer = value;
                                    String[] splitID = key.split(":");
                                    OtherPlayer();
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });*/

    }

    public void Deletedata(View view)
    {

        //myRef.child("Playing").child(playerSession).child("lock").removeValue();
        ArrayList<String> m1=new ArrayList<>();
        myRef.child("Playing").child(playerSession).removeValue();
        arrayAdapter1 =new ArrayAdapter(OnlineGameActivity.this, R.layout.listview_textlayout,m1);
        listView1.setAdapter(arrayAdapter1);

        arrayAdapter2 =new ArrayAdapter(OnlineGameActivity.this, R.layout.listview_textlayout,m1);
        listView2.setAdapter(arrayAdapter2);
        initial1();

    }

    public void numberview()
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
    }


    public void initial1()
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
        locked=(TextView) findViewById(R.id.locked);
        locked.setText("Locking");
        sendtext=(EditText)findViewById(R.id.sendtext) ;
        send=(Button)findViewById(R.id.send);
        //calc=(Button)findViewById(R.id.calc);
        listView1=(ListView)findViewById(R.id.list_view1);
        listView2=(ListView)findViewById(R.id.list_view2);
        delbtn=(Button)findViewById(R.id.delete_btn);
        lock = (Button) findViewById(R.id.lock_btn);
        confirm=(Button) findViewById(R.id.confirm);
        lock.setText("Lock");
        turn=(TextView)findViewById(R.id.turn);
        tvDisplay.setText("SCOREBOARD");
        turn.setText("Turn");
        count=0;
        trial=1;
        confirm.setText("Confirm");
    }
    public void Clear() {
        pos1.setValue(1);
        pos2.setValue(1);
        pos3.setValue(1);
        pos4.setValue(1);
        secret1 = new
                StringBuilder();
        guess1 = new
                StringBuilder();
        output1=new StringBuilder();

        secret2 = new
                StringBuilder();
        guess2 = new
                StringBuilder();
        output2=new StringBuilder();
        guess = new
                StringBuilder();

        your_tv_output.setText(output1);
        oppo_tv_output.setText(output2);
        count=0;
        trial=1;
        lock.setText("Lock");
        confirm.setText("Confirm");
        initial1();
    }


    public void click(View view) {

        if(trial==1) {
            secret1=new StringBuilder();
            check1 = pos1.getValue();
            if (check1 != 0)     //size as per your requirement
            {
                pos1.requestFocus();
            }
            check2 = pos2.getValue();
            check3 = pos3.getValue();
            check4 = pos4.getValue();
            // Secret Key generation
            secret1.append(check1);
            secret1.append(check2);
            secret1.append(check3);
            secret1.append(check4);
            sec1 = secret1.toString();
            pos1.requestFocus();
            trial = trial+1;
            lock.setText("GUESS");
            locked.setText("Your Secret " + secret1);
        }
        else
        {
            if(sec2.isEmpty())
            {
                Toast.makeText(OnlineGameActivity.this,"Secret is not confirmed by opponent",Toast.LENGTH_SHORT).show();
                return;
                //Intent in=new Intent(this,OnlineGameActivity.class);
                //startActivity(in);
            }
            else {
                check1 = pos1.getValue();
                if (check1 != 0)     //size as per your requirement
                {
                    pos1.requestFocus();
                }
                check2 = pos2.getValue();
                check3 = pos3.getValue();
                check4 = pos4.getValue();

                guess = new
                        StringBuilder();
                //STORING SECRET IN STRING
                //if(secans.length()==0)
                //    secans=secret.toString();
                //STORING GUESS IN STRING
                guess.append(check1);
                guess.append(check2);
                guess.append(check3);
                guess.append(check4);

                checkwin();
                //      to set cursor in first editText
                pos1.requestFocus();

                if (countb == 4) {

                    return;
                    //openDialogwin();
                    //Clear();
                    //recreate();
                }
            }
        }
    }


    /*public void Selectsecret1() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_connecttofriend, null);
        b.setView(dialogView);

        final EditText secnum = (EditText) dialogView.findViewById(R.id.Secretnumber);

        b.setTitle("Please Lock Your Secret Number");
        b.setPositiveButton("Lock", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sec1=secnum.getText().toString();
                myRef.child("playing").child(playerSession).child("turn").child(userName).setValue(sec1);
               // System.out.println(sec1);

            }
        });
        b.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), OnlineGameActivity.class);
                startActivity(intent);
                finish();
            }
        }).setCancelable(false);
        b.show();
    }

    public void Selectsecret2() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_connecttofriend, null);
        b.setView(dialogView);

        final EditText secnum = (EditText) dialogView.findViewById(R.id.Secretnumber);

        b.setTitle("Please Lock Your Secret Number");
        b.setPositiveButton("Lock", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sec2=secnum.getText().toString();
                myRef.child("playing").child(playerSession).child("turn").child(otherPlayer).setValue(sec2);
            }
        });
        b.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), OnlineGameActivity.class);
                startActivity(intent);
                finish();
            }
        }).setCancelable(false);
        b.show();
    }

    void ShowAlert(String Title){
        AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.TransparentDialog);
        b.setTitle(Title)
                .setMessage("Start a new game?")
                .setNegativeButton("Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), OnlineGameActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }


    void OtherPlayer()
    {
        Selectsecret2();
    }*/


    public void Calc(View view)
    {
        check1 = pos1.getValue();
        if (check1 != 0)     //size as per your requirement
        {
            pos1.requestFocus();
        }
        check2 = pos2.getValue();
        check3 = pos3.getValue();
        check4 = pos4.getValue();

        guess = new StringBuilder();

        //STORING SECRET IN STRING
        //if(secans.length()==0)
        //    secans=secret.toString();
        //STORING GUESS IN STRING
        guess.append(check1);
        guess.append(check2);
        guess.append(check3);
        guess.append(check4);
        //System.out.println(secret);
        //System.out.println(guess);
        /*if (guess_checker.contains(guess.toString()) == true) {
            Toast.makeText(getApplicationContext(),"You Have Entered Already Guessed Number",Toast.LENGTH_SHORT).show();
            return;
        }
        guess_checker.add(guess.toString());
        countcow.clear();*/
        checkwin();
        //      to set cursor in first editText
        pos1.requestFocus();
        //count++;
        trial++;
        //trial = trial - count;
        if (countb == 4) {
            openDialogwin();
            Clear();
            //recreate();
        }
    }

    public void checkwin() {

        countb=0;
        countc=0;
        System.out.println(sec2);
        System.out.println(guess);
        int countBull=0;
        int countCow =0;

        int[] arr1 = new int[10];
        int[] arr2 = new int[10];

        //CHECKING NUMBER OF BULLS AND COWS
        for(int i=0; i<sec2.length(); i++){
            char c1 = sec2.charAt(i);
            char c2 = guess.charAt(i);

            if(c1==c2)
                countBull++;
            else{
                arr1[c1-'0']++;
                arr2[c2-'0']++;
            }
        }

        for(int i=0; i<10; i++){
            countCow += Math.min(arr1[i], arr2[i]);
        }


        //UPDATING THE VALUES OF TEXTVIEWS WHICH WOULD BE SEEN BY THE USERS
        sendtext.setText(guess + " "+countBull+"B "+countCow+"C");
        countc=countCow;
        countb=countBull;
        guess.delete(0,guess.length());
    }

    public void openDialogwin()
    {
        View v= LayoutInflater.from(OnlineGameActivity.this).inflate(R.layout.win_dialog,null);
        TextView answer=(TextView)v.findViewById(R.id.answer);

        TextView ttrial=(TextView)v.findViewById(R.id.totaltrial);

        //Windialog win = new Windialog();
        //win.show(getSupportFragmentManager(),"win Dialog");
        AlertDialog.Builder builder=new AlertDialog.Builder(OnlineGameActivity.this);

        builder.setView(v)
                .setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Clear();
            }
        }).setCancelable(false);

        answer.setText("Answer is = "+sec2);
        int trial1=trial;
        ttrial.setText("Total Trials "+trial1 );

        AlertDialog alert=builder.create();
        alert.show();
    }
}