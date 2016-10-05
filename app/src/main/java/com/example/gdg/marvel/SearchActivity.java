package com.example.gdg.marvel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
public class SearchActivity extends AppCompatActivity {

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

        // Inicializa os componentes e seta os eventos
        setComponents();
        setEvents();
    }

    /**
     * Sets up the components
     */
    protected void setComponents() {

        loading = (ProgressBar) findViewById(R.id.loading);
        feedbackLabel = (TextView) findViewById(R.id.feedback_label);

        charactersList = (RecyclerView) findViewById(R.id.characters_list);
        charactersList.setLayoutManager(new LinearLayoutManager(this));

        searchText = (EditText) findViewById(R.id.search_edit_text);
        searchButton = (ImageView) findViewById(R.id.search_button);
    }

    /**
     * Sets the components' events
     */
    protected void setEvents() {

        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Toast.makeText(SearchActivity.this, "Pesquisa de personagens", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
