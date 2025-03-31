package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView time;
    TextView score1;
    ImageView mole, mole2, mole3, mole4, mole5, mole6, mole7, mole8, mole9;
    private Thread t1 = null;

    private void viewGoneAnimator(final View view) {

        view.animate()
                .alpha(0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.INVISIBLE);
                    }
                });

    }

    private void viewVisibleAnimator(final View view) {

        view.animate()
                .alpha(1f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score1 = findViewById(R.id.id_score);
        time = findViewById(R.id.id_time);
        mole = findViewById(R.id.id_mole1);
        mole2 = findViewById(R.id.id_mole2);
        mole3 = findViewById(R.id.id_mole3);
        mole4 = findViewById(R.id.id_mole4);
        mole5 = findViewById(R.id.id_mole5);
        mole6 = findViewById(R.id.id_mole6);
        mole7 = findViewById(R.id.id_mole7);
        mole8 = findViewById(R.id.id_mole8);
        mole9 = findViewById(R.id.id_mole9);

        time.setText("Timer: 60");
        score1.setText("Score: 0");

        List<ImageView> molesList = Arrays.asList(mole, mole2, mole3, mole4, mole5, mole6, mole7, mole8, mole9);

        int[] scoreCount = {0};
        int[] countdown = {60};
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time.setText("Timer: " + (countdown[0]));
                        for (ImageView imageView : molesList){
                            imageView.setImageResource(R.drawable.mole);
                        }
                        for (ImageView imageView : molesList){
                            int abc = new Random().nextInt((11 - 1) + 1) + 1;
                            if ((abc == 1) && (imageView.getVisibility() != View.VISIBLE)) {
                                int oops = new Random().nextInt((5 - 1) + 1) + 1;
                                if ((oops == 1)) {
                                    imageView.setImageResource(R.drawable.bomb);
                                }
                                viewVisibleAnimator(imageView);

                            }
                            else {
                                viewGoneAnimator(imageView);
                            }
                        }
                    }
                });
                if (countdown[0] > 0) {
                    countdown[0]--;
                }
                else {
                    time.post(new Runnable() {
                        @Override
                        public void run() {
                            time.setText("Game Over!");
                            for (ImageView imageView : molesList){
                                imageView.setClickable(false);
                            }
                            score1.setText("Final Score: " + scoreCount[0]);
                            scoreCount[0] = 0;
                            t.cancel();
                        }
                    });
                }
            }
        }, 1000, 1000);

        for (ImageView imageView : molesList) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((imageView.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.bomb).getConstantState()) && countdown[0] > 0)
                    {
                        countdown[0] -= 5;
                    }
                    if(imageView.getVisibility() == View.VISIBLE)
                    {
                        scoreCount[0]++;
                        if(scoreCount[0] % 5 == 0){
                            ImageView imageview = new ImageView(MainActivity.this);
                            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                            imageview.setImageResource(R.drawable.moletally);
                            imageview.setLayoutParams(params);
                            linearLayout.addView(imageview);
                        }
                        score1.setText("Score: " + scoreCount[0]);
                        viewGoneAnimator(imageView);
                    }
                }
            });
        }

    }
}


