package com.example.gdg.marvel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gdg.marvel.base.Globals;
import com.example.gdg.marvel.adapter.CharactersAdapter;

public class SearchActivity extends AppCompatActivity {

    // Vars
    Typeface defaultFont;

    // Comṕonents
    ProgressBar loading;
    TextView feedbackLabel;
    ImageView searchButton;
    EditText searchText;
    RecyclerView charactersList;

    /**
     * Constructor
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        //this.defaultFont = Typeface.createFromAsset(getAssets(), Globals.defaultFontName);

        setComponents();
        setEvents();
    }

    /**
     * Sets up the components
     */
    protected void setComponents() {

        //Globals.marvelApiConfig = new MarvelApiConfig.Builder(Globals.publicKey, Globals.privateKey).debug().build();

        loading = (ProgressBar) findViewById(R.id.loading);
        feedbackLabel = (TextView) findViewById(R.id.feedback_label);
        //feedbackLabel.setTypeface(defaultFont);

        charactersList = (RecyclerView) findViewById(R.id.characters_list);
        charactersList.setLayoutManager(new LinearLayoutManager(this));

        searchText = (EditText) findViewById(R.id.search_edit_text);
        //searchText.setTypeface(defaultFont);

        searchButton = (ImageView) findViewById(R.id.search_button);
    }

    /**
     * Sets the components' events
     */
    protected void setEvents() {

        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                loading.setVisibility(View.VISIBLE);
                feedbackLabel.setVisibility(View.INVISIBLE);

                //final CharacterApiClient characterApiClient = new CharacterApiClient(Globals.marvelApiConfig);
                //final CharactersQuery query = CharactersQuery.Builder.create().withNameStartWith(searchText.getText().toString()).build();

                // Executa a pesquisa pelos personagens de forma assíncrona
                try {

                    // Executa a pesquisa
                    //final MarvelResponse<CharactersDto> all = characterApiClient.getAll(query);

                    // Esconde o loading
                    loading.setVisibility(View.INVISIBLE);

                    /*
                    if (all.getResponse().getCharacters().size() == 0) {
                        charactersList.setAdapter(null);
                        feedbackLabel.setText(R.string.search_feedback_not_found);
                        feedbackLabel.setVisibility(View.VISIBLE);
                        return;
                    }

                    // Cria o adaptador
                    CharactersAdapter adapter = new CharactersAdapter(SearchActivity.this, all.getResponse().getCharacters());

                    // Insere o adaptador
                    charactersList.setAdapter(adapter);

                    // Esconde o teclado
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
                    */

                } catch (Exception e) {
                    e.printStackTrace();

                    // Executa a thread da interface
                    /*
                    runOnUiThread(new Runnable() {
                        public void run() {
                            loading.setVisibility(View.INVISIBLE);
                            charactersList.setAdapter(null);
                            feedbackLabel.setText(R.string.search_feedback_internet_problems);
                            feedbackLabel.setVisibility(View.VISIBLE);
                        }
                    });
                    */
                }
            }
        });
    }
}
