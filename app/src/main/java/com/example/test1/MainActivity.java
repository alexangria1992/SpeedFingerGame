package com.example.test1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private TextView aThousandTextView;
    private Button tatTapButton;
    private CountDownTimer countDownTimer;

    private long initialCountDownInMillis = 60000;
    private int timerInterval = 1000;
    private int remainingTime = 60;
    private int aThousand = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.txtTimer);
        aThousandTextView = findViewById(R.id.txtAThousand);
        tatTapButton = findViewById(R.id.btnTap);
        aThousandTextView.setText(aThousand + "");


        tatTapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aThousand--;
                // aThousand = aThousand - 1;
                aThousandTextView.setText(aThousand + "");

                if (remainingTime > 0 && aThousand <= 0) {
                   Toast.makeText(MainActivity.this, "Congratulations", Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.alert_title)
                            .setMessage(R.string.alert_message)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what would happen when positive button is clicked
                                    finish();
                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what should happen when negative button is clicked
                                    Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }
            }
        });
        countDownTimer = new CountDownTimer(initialCountDownInMillis, timerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int)millisUntilFinished / 1000;
                timerTextView.setText(remainingTime + "");
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Countdown finished", Toast.LENGTH_SHORT);
            }
        };
        countDownTimer.start();


    }

    private void resetTheGame(){
        
    }
}