package com.example.test1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    private final String REMAINING_TIME_KEY = "remaining time key";
    private final String A_THOUSAND_KEY = "A thousand key";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("On destroy Method is Called", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        showToast("On Save Instance Is Called", Toast.LENGTH_SHORT);
        outState.putInt(REMAINING_TIME_KEY, remainingTime);
        outState.putInt(A_THOUSAND_KEY, aThousand);
        countDownTimer.cancel();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToast("On Ceate Method is called", Toast.LENGTH_SHORT);

        timerTextView = findViewById(R.id.txtTimer);
        aThousandTextView = findViewById(R.id.txtAThousand);
        tatTapButton = findViewById(R.id.btnTap);
        aThousandTextView.setText(aThousand + "");

        if(savedInstanceState != null) {
            remainingTime = savedInstanceState.getInt(REMAINING_TIME_KEY);
            aThousand = savedInstanceState.getInt(A_THOUSAND_KEY);

            restoreTheGame();
        }

        tatTapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aThousand--;
                // aThousand = aThousand - 1;
                aThousandTextView.setText(aThousand + "");

                if (remainingTime > 0 && aThousand <= 0) {
                    Toast.makeText(MainActivity.this, "Congratulations", Toast.LENGTH_SHORT).show();

                    showAlert("Congratulations", "Please reset the game");
                }
            }
        });
        if(savedInstanceState == null) {
            countDownTimer = new CountDownTimer(initialCountDownInMillis, timerInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTime = (int) millisUntilFinished / 1000;
                    timerTextView.setText(remainingTime + "");
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, "Countdown finished", Toast.LENGTH_SHORT);

                    showAlert("Not interesting", "Would you like to try again?");
                }
            };
            countDownTimer.start();
        }


    }

    private void restoreTheGame() {
        int restoredRemainingTime = remainingTime;
        int restoredAThousand = aThousand;
        timerTextView.setText(restoredRemainingTime + "");
        aThousandTextView.setText(restoredAThousand + "");

        countDownTimer = new CountDownTimer((long)remainingTime * 1000, timerInterval) {
            @Override
            public void onTick(long milisUntilFinished) {
            remainingTime = (int) milisUntilFinished/ 1000;
            timerTextView.setText(remainingTime+ "");


            }

            @Override
            public void onFinish() {
               showAlert("Finished", "Would you like to reset the game?");
            }
        };
        countDownTimer.start();
    }

    private void resetTheGame() {
        if(countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }

        aThousand = 1000;
        aThousandTextView.setText(Integer.toString(aThousand));
        timerTextView.setText(remainingTime + "");

        countDownTimer = new CountDownTimer(initialCountDownInMillis, timerInterval) {
            @Override
            public void onTick(long millisToFinish) {
                remainingTime = (int) millisToFinish / 1000;
                timerTextView.setText(remainingTime + "");
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Countdown finished", Toast.LENGTH_SHORT);


                    showAlert("Not interested", "Would you like to try again?");



            }
        };
        countDownTimer.start();
    }
    private void showAlert (String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        resetTheGame();
                    }
                })
                .show();
        alertDialog.setCancelable(false);
    }

    private void showToast(String message, int duration){
        Toast.makeText(this, message, duration).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId() == R.id.info_item){
           showToast("This is the Current version of your game. Check Google Play and Make sure that you're playing latest version of game" + BuildConfig.VERSION_NAME, Toast.LENGTH_LONG);
       }
       return true;
    }
}