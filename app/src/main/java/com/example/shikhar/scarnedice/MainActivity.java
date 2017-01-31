package com.example.shikhar.scarnedice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.concurrent.RunnableFuture;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {
    Random rnd = new Random();
    int useroverall=0;
    int usercurrent=0;
    int computeroverall=0;
    int computercurrent=0;
    ImageView img;
    Button hold;
    Button reset,roll;
    TextView yourscore;
    TextView compscore;
    android.os.Handler timerHandler;
    Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerHandler= new android.os.Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                timerHandler.postDelayed(this,500);
            }
        };

        yourscore = (TextView)findViewById(R.id.yourscore);
        compscore = (TextView)findViewById(R.id.compscore);
        roll = (Button)findViewById(R.id.roll);
        hold = (Button)findViewById(R.id.hold);
        reset = (Button)findViewById(R.id.reset);
        img  = (ImageView)findViewById(R.id.imageView);


        if(useroverall+computeroverall==0){
            hold.setEnabled(false);
            reset.setEnabled(false);
        }
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold.setEnabled(true);
                reset.setEnabled(true);
                int newnumber = getnumber();
                if(newnumber==1){
                    usercurrent = 0;
                    yourscore.setText(String.format("Your score:%d",useroverall));
                    hold.setEnabled(false);
                    reset.setEnabled(false);
                    computerTurn();
                    if(computeroverall>=100){
                        Toast.makeText(MainActivity.this,"COMPUTER WINS",Toast.LENGTH_LONG).show();
                        resetfunc();
                    }
                }
                else {
                    usercurrent += newnumber;
                    yourscore.setText(String.format("Your score:%d",useroverall+usercurrent));
                }
            }
        });
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usercurrent!=0) {
                    useroverall += usercurrent;
                    yourscore.setText(String.format("Your score:%d", useroverall));
                    usercurrent = 0;
                    hold.setEnabled(false);
                    reset.setEnabled(false);
                    if(useroverall>=100){
                        Toast.makeText(MainActivity.this,"YOU WIN",Toast.LENGTH_LONG).show();
                        resetfunc();

                    }
                    else {
                        computerTurn();
                        if(computeroverall>=100){
                            Toast.makeText(MainActivity.this,"COMPUTER WINS",Toast.LENGTH_LONG).show();
                            resetfunc();
                        }
                    }
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetfunc();
            }
        });


    }
    public void resetfunc(){
        useroverall = 0;
        usercurrent = 0;
        computercurrent = 0;
        computeroverall = 0;
        yourscore.setText(String.format("Your score:%d",useroverall));
        compscore.setText(String.format("Computer score:%d",computeroverall));
        hold.setEnabled(false);
        reset.setEnabled(false);
    }
    public int getnumber(){
        int number = (rnd.nextInt(6))+1;
        switch(number){
            case 1:
                img.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                break;
            case 2:
                img.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                break;
            case 3:
                img.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                break;
            case 4:
                img.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                break;
            case 5:
                img.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                break;
            case 6:
                img.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
                break;

        }

        return number;
    }

    public void computerTurn(){
        computerTurnsingle();
        int rand = rnd.nextInt(2);
        if (rand == 0) {
            computerTurn();
        }
        else {
            hold.setEnabled(true);
            reset.setEnabled(true);
        }

    }
    public void computerTurnsingle(){
        computercurrent = 0;
        int number = getnumber();
        if(number==1) {
            hold.setEnabled(true);
            reset.setEnabled(true);
            computercurrent = 0;
        }
        else{
            computercurrent += number;
        }
        computeroverall+=computercurrent;

        compscore.setText(String.format("Computer score:%d",computeroverall));

        timerHandler.postDelayed(timerRunnable,5000);

    }



}
