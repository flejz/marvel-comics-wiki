package com.example.gdg.marvel.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gdg.marvel.R;

public class SplashScreenActivity extends AppCompatActivity {

    // Vars
    private final Handler finishHandler = new Handler();

    // Const
    private static final int FINISH_DELAY_MILLIS = 2000;

    // Runabbles
    private final Runnable finishSplashScreenRunnable = new Runnable() {

        public void run() {

            // Cria uma nova intenção
            Intent searchScreenIntention = new Intent(SplashScreenActivity.this, SearchActivity.class);

            // Inicializa a intenção
            startActivity(searchScreenIntention);

            // Finaliza a tela atual
            finish();
        }
    };

    /**
     * Constructor
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

    }

    /**
     * On post create activity
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedFinish(FINISH_DELAY_MILLIS);
    }

    /**
     * Delay and finish
     */
    private void delayedFinish(int delayMillis) {
        finishHandler.removeCallbacks(finishSplashScreenRunnable);
        finishHandler.postDelayed(finishSplashScreenRunnable, delayMillis);
    }
}
