package com.example.gdg.marvel.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gdg.marvel.R;
import com.example.gdg.marvel.base.Cache;
import com.example.gdg.marvel.base.Globals;
import com.example.gdg.marvel.view.adapter.CharactersAdapter;
import com.example.gdg.marvel.view.base.BaseActivity;
import com.example.gdg.marvel.view.events.CharactersEvents;
import com.karumi.marvelapiclient.CharacterApiClient;
import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.CharacterDto;
import com.karumi.marvelapiclient.model.CharactersDto;
import com.karumi.marvelapiclient.model.CharactersQuery;
import com.karumi.marvelapiclient.model.MarvelResponse;

public class SearchActivity extends BaseActivity {

    // Comṕonents
    ProgressBar loading;
    TextView feedbackLabel;
    ImageView searchButton;
    EditText searchText;
    RecyclerView charactersList;

    // Listeners
    CharactersEvents characterEvents = new CharactersEvents() {

        public void onSelect(CharacterDto character) {

            Cache.currentCharacter = character;

            // Cria a intenção de abrir a tela do personagem
            Intent characterIntention = new Intent(SearchActivity.this, CharacterActivity.class);

            // Executa a intenção
            startActivity(characterIntention);
        }
    };

    /**
     * Constructor
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
    }

    /**
     * Sets up the components
     */
    protected void setComponents() {

        Globals.marvelApiConfig = new MarvelApiConfig.Builder(Globals.publicKey, Globals.privateKey).debug().build();

        loading = (ProgressBar) findViewById(R.id.loading);
        feedbackLabel = (TextView) findViewById(R.id.feedback_label);
        feedbackLabel.setTypeface(defaultFont);

        charactersList = (RecyclerView) findViewById(R.id.characters_list);
        charactersList.setLayoutManager(new LinearLayoutManager(this));

        searchText = (EditText) findViewById(R.id.search_edit_text);
        searchText.setTypeface(defaultFont);
        searchButton = (ImageView) findViewById(R.id.search_button);
    }

    /**
     * Sets the components' events
     */
    protected void setEvents() {

        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (searchText.getText().toString().trim().equals("")) {
                    feedbackLabel.setText(R.string.search_feedback_empty);
                    feedbackLabel.setVisibility(View.VISIBLE);
                    return;
                }

                loading.setVisibility(View.VISIBLE);
                feedbackLabel.setVisibility(View.INVISIBLE);

                final CharacterApiClient characterApiClient = new CharacterApiClient(Globals.marvelApiConfig);
                final CharactersQuery spider = CharactersQuery.Builder.create().withNameStartWith(searchText.getText().toString()).build();

                // Executa a pesquisa pelos personagens de forma assíncrona
                new AsyncTask() {

                    protected Void doInBackground(Object[] params) {

                        try {

                            // Executa a pesquisa
                            final MarvelResponse<CharactersDto> all = characterApiClient.getAll(spider);

                            // Executa a thread da interface
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    // Esconde o loading
                                    loading.setVisibility(View.INVISIBLE);

                                    if (all.getResponse().getCharacters().size() == 0) {
                                        charactersList.setAdapter(null);
                                        feedbackLabel.setText(R.string.search_feedback_not_found);
                                        feedbackLabel.setVisibility(View.VISIBLE);
                                        return;
                                    }

                                    // Cria o adaptador
                                    CharactersAdapter adapter = new CharactersAdapter(SearchActivity.this, all.getResponse().getCharacters(), characterEvents);

                                    // Insere o adaptador
                                    charactersList.setAdapter(adapter);

                                    // Esconde o teclado
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
                                }
                            });

                        } catch (MarvelApiException e) {
                            e.printStackTrace();

                            // Executa a thread da interface
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    loading.setVisibility(View.INVISIBLE);
                                    charactersList.setAdapter(null);
                                    feedbackLabel.setText(R.string.search_feedback_internet_problems);
                                    feedbackLabel.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        return null;
                    }
                }.execute();
            }
        });
    }
}
