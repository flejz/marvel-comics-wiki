package com.example.gdg.marvel;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gdg.marvel.adapter.CharactersAdapter;
import com.karumi.marvelapiclient.CharacterApiClient;
import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.CharacterDto;
import com.karumi.marvelapiclient.model.CharactersDto;
import com.karumi.marvelapiclient.model.CharactersQuery;
import com.karumi.marvelapiclient.model.MarvelResponse;

public class SearchActivity extends AppCompatActivity {

    // Vars
    ProgressBar loading;
    TextView feedbackLabel;
    ImageButton searchButton;
    EditText searchText;
    RecyclerView charsList;

    MarvelApiConfig marvelApiConfig;

    String publicKey = "daa0d68ce8483fe62a24fd7c86086058";
    String privateKey = "edd1195e0e74509e624ecbc3bd44c4a09c7602d9";

    /**
     * Constructor
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        setUp();
    }

    private void setUp() {

        marvelApiConfig = new MarvelApiConfig.Builder(publicKey, privateKey).debug().build();

        loading = (ProgressBar) findViewById(R.id.loading);
        feedbackLabel = (TextView) findViewById(R.id.feedback_label);

        charsList = (RecyclerView) findViewById(R.id.chars_list);
        charsList.setLayoutManager(new LinearLayoutManager(this));

        searchText = (EditText) findViewById(R.id.search_edit_text);
        searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                loading.setVisibility(View.VISIBLE);
                feedbackLabel.setVisibility(View.INVISIBLE);

                final CharacterApiClient char acterApiClient = new CharacterApiClient(marvelApiConfig);
                final CharactersQuery spider = CharactersQuery.Builder.create().withNameStartWith(searchText.getText().toString()).build();

                // Executa a pesquisa pelos personagens de forma assíncrona
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... voids) {

                        try {

                            // Executa a pesquisa
                            final MarvelResponse<CharactersDto> all = characterApiClient.getAll(spider);

                            // Executa a thread da interface
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    // Esconde o loading
                                    loading.setVisibility(View.INVISIBLE);

                                    if (all.getResponse().getCharacters().size() == 0) {
                                        charsList.setAdapter(null);
                                        feedbackLabel.setText(R.string.search_feedback_not_found);
                                        feedbackLabel.setVisibility(View.VISIBLE);
                                        return;
                                    }

                                    // Cria o adaptador
                                    CharactersAdapter adapter = new CharactersAdapter(SearchActivity.this, all.getResponse().getCharacters());

                                    // Insere o adaptador
                                    charsList.setAdapter(adapter);

                                    // Esconde o teclado
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
                                }
                            });

                        } catch (MarvelApiException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();

            }
        });

    }
}
