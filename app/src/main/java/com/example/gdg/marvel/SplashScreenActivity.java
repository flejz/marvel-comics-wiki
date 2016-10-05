package com.example.gdg.marvel;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    /*

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

    */

    /**
     * Constructor
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);


        findViewById(R.id.marvel_logo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Toast.makeText(SplashScreenActivity.this, "Marvel Super Heroes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedFinish(FINISH_DELAY_MILLIS);
    }

    private void delayedFinish(int delayMillis) {
        finishHandler.removeCallbacks(finishSplashScreenRunnable);
        finishHandler.postDelayed(finishSplashScreenRunnable, delayMillis);
    }

    */
}
