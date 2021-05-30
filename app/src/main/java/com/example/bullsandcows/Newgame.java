package com.example.bullsandcows;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class Newgame extends AppCompatActivity {

    // NumberPicker for four numbers enter by user
    NumberPicker pos1;
    NumberPicker pos2;
    NumberPicker pos3;
    NumberPicker pos4;
    TextView hint;
    int hintcounter = 0;
    TextView c;
    TextView b;
    TextView t;
    TextView trials;
    TextView tvDisplay;
    TextView tv_output;
    //TextView ans;
    int check1 = 0, check2 = 0, check3 = 0;
    int min = 1, max = 9;
    int rand1 = 0, rand2 = 0, rand3 = 0, rand4 = 0;
    int check4 = 0, countc = 0, countb = 0, count = 0, trial = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame);
        //Initializing the game
        initial();
    }

    public void initial() {
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
        hint = (TextView) findViewById(R.id.hint);
        trials = (TextView) findViewById(R.id.textView_trials);
        c = findViewById(R.id.textView_cows);
        b = findViewById(R.id.textView_Bulls);
        t = findViewById(R.id.totaltrial);
        tvDisplay = findViewById(R.id.tvDisplay);
        tv_output = findViewById(R.id.tv_output);
        Button b1 = (Button) findViewById(R.id.button1);

        trials.setText("Trails Left : " + trial);
        c.setText("Cows : " + countc + "");
        b.setText("Bulls : " + countb + "");
        tvDisplay.setText("GUESS NUMBER");
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
        output = new StringBuilder();
        guess_checker = new HashSet<String>();
        tv_output.setText(output);
        count = 0;
        countb = 0;
        countc = 0;
        trial = 10;
        hintcounter = 0;
        b.setText("Bulls : " + countb + "");
        c.setText("Cows : " + countc + "");
        trials.setText("Trails Left : " + trial);
        hint.setText("HINT");
        initial();
    }

    StringBuilder secret = new
            StringBuilder("");
    StringBuilder guess = new
            StringBuilder("");
    StringBuilder output = new StringBuilder();
    HashSet<String> guess_checker = new HashSet<String>();

    public void Reset(View view) {
        secret = new
                StringBuilder();
        guess = new
                StringBuilder();
        output = new StringBuilder();
        guess_checker = new HashSet<>();
        Clear();
        tv_output.setText(output);
        count = 0;
        countb = 0;
        countc = 0;
        trial = 10;
        hintcounter = 0;
        b.setText("Bulls : " + countb + "");
        c.setText("Cows : " + countc + "");
        trials.setText("Trails Left : " + trial);
        hint.setText("HINT");
        initial();

    }
    //FUNCTION TO GIVE HINT RANDOMLY WITHOUT BIASING
    public void Hint(View view) {
        if (hintcounter == 1)
            return;

        Random rand = new Random();
        int r = (rand.nextInt(4)) % 4;
        if (r == 0) {
            hint.setText("Bull " + rand1 + "\nat " + 1);
        }
        if (r == 1) {
            hint.setText("Bull " + rand2 + "\nat" + 2);
        }
        if (r == 2) {
            hint.setText("Bull " + rand3 + "\nat " + 3);
        }
        if (r == 3) {
            hint.setText("Bull " + rand4 + "\nat " + 4);
        }
        hintcounter++;
    }

    String secans="";
    public void click(View view) {
        check1 = pos1.getValue();
        if (check1 != 0)     //size as per your requirement
        {
            pos1.requestFocus();
        }
        check2 = pos2.getValue();
        check3 = pos3.getValue();
        check4 = pos4.getValue();

        secret = new
                StringBuilder();
        guess = new
                StringBuilder();
        //STORING SECRET IN STRING
        secret.append(rand1);
        secret.append(rand2);
        secret.append(rand3);
        secret.append(rand4);
        if(secans.length()==0)
            secans=secret.toString();
        //STORING GUESS IN STRING
        guess.append(check1);
        guess.append(check2);
        guess.append(check3);
        guess.append(check4);
        //System.out.println(secret);
        //System.out.println(guess);
        if (guess_checker.contains(guess.toString()) == true) {
            Toast.makeText(getApplicationContext(),"You Have Entered Already Guessed Number",Toast.LENGTH_SHORT).show();
            return;
        }
        guess_checker.add(guess.toString());
        countcow.clear();
        checkwin();
        //      to set cursor in first editText
        pos1.requestFocus();
        count++;
        trial = 10;
        trial = trial - count;
        trials.setText("Trails Left: " + "" + trial);
        if (countb == 4) {

            openDialogwin();
            Clear();
            //recreate();
        }
        if (count == 10) {
            openDialoglose();
            Clear();
            //recreate();
        }
        if (count > 10) {
            Clear();
            Reset(view);
        }
    }



    public void openDialogwin()
    {
        View v= LayoutInflater.from(Newgame.this).inflate(R.layout.win_dialog,null);
        TextView answer=(TextView)v.findViewById(R.id.answer);

        TextView ttrial=(TextView)v.findViewById(R.id.totaltrial);

        //Windialog win = new Windialog();
        //win.show(getSupportFragmentManager(),"win Dialog");
        AlertDialog.Builder builder=new AlertDialog.Builder(Newgame.this);

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

        answer.setText("Answer is = "+secans);

        ttrial.setText("Total Trials "+trial );

        AlertDialog alert=builder.create();
        alert.show();
    }
    public void openDialoglose()
    {
        //Losedialog lose = new Losedialog();
        //lose.show(getSupportFragmentManager(),"lose Dialog");

        View v= LayoutInflater.from(Newgame.this).inflate(R.layout.lose_dialog,null);
        TextView answer=(TextView)v.findViewById(R.id.answer);

        AlertDialog.Builder builder=new AlertDialog.Builder(Newgame.this);

        builder.setView(v)
                .setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false);

        answer.setText("Answer is = "+secans);

        AlertDialog alert=builder.create();
        alert.show();
    }
    ArrayList<Integer> countcow = new ArrayList<Integer>();

    /*public int count_cow(int check) {
        int cc = 0;
        if ((check == rand1) && (!(countcow.contains(rand1)))) {
            cc++;
            countcow.add(rand1);
        } else if ((check == rand2) && (!(countcow.contains(rand2)))) {
            cc++;
            countcow.add(rand2);
        } else if ((check == rand3) && (!(countcow.contains(rand3)))) {
            cc++;
            countcow.add(rand3);
        } else if ((check == rand4) && (!(countcow.contains(rand4)))) {
            cc++;
            countcow.add(rand4);
        }
        return cc;
    }*/


    // FUNCTION TO CHECK IF USER WINS OR NOT
    public void checkwin() {
        countb = 0;
        countc = 0;

        System.out.println(secret);
        System.out.println(guess);
        int countBull=0;
        int countCow =0;
        b.setText("Bulls : " + countBull + "");
        c.setText("Cows : " + countCow+ "");

        int[] arr1 = new int[10];
        int[] arr2 = new int[10];

        //CHECKING NUMBER OF BULLS AND COWS
        for(int i=0; i<secret.length(); i++){
            char c1 = secret.charAt(i);
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

        b.setText("Bulls : " + countBull + "");

        //UPDATING THE VALUES OF TEXTVIEWS WHICH WOULD BE SEEN BY THE USERS
        c.setText("Cows : " + countCow+ "");
        output.append("Guessed-");
        output.append(guess);
        output.append(" ");
        output.append(" Bulls "+countBull);
        output.append(" Cows "+countCow);
        output.append("\n");
        tv_output.setText(output);
        countc=countCow;
        countb=countBull;
        guess.delete(0,guess.length());
        secret.delete(0,secret.length());
    }
}