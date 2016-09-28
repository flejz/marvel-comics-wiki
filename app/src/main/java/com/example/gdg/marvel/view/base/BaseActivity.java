package com.example.gdg.marvel.view.base;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.gdg.marvel.R;
import com.example.gdg.marvel.base.Globals;

public class BaseActivity extends AppCompatActivity {

    // Vars
    TextView creditsLabel;

    protected Typeface defaultFont;

    /**
     * When starts the activity
     */
    @Override
    protected void onStart() {
        super.onStart();

        setUp();
    }

    /**
     * Sets up the application
     */
    private void setUp() {

        // Seta a fonte padrão
        defaultFont = Typeface.createFromAsset(getAssets(), Globals.defaultFontName);

        // Pega o elemento credits label
        creditsLabel = (TextView) findViewById(R.id.credits_label);

        // Se este elemento existir na activity atual, adiciona a fonte a ele
        if (creditsLabel != null) {
            creditsLabel.setTypeface(defaultFont);
        }
    }
}
