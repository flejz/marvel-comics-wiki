package com.example.gdg.marvel.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gdg.marvel.R;
import com.example.gdg.marvel.base.Cache;
import com.example.gdg.marvel.utils.ImageUtils;
import com.example.gdg.marvel.view.adapter.ComicsAdapter;
import com.example.gdg.marvel.view.adapter.EventsAdapter;
import com.example.gdg.marvel.view.base.BaseActivity;
import com.karumi.marvelapiclient.model.CharacterDto;
import com.karumi.marvelapiclient.model.EventResourceDto;

import java.util.List;

public class CharacterActivity extends BaseActivity {

    // Variables
    CharacterDto character;

    // Comṕonents
    TextView characterTitle;
    TextView characterName;
    TextView characterDescription;
    ImageView characterThumbnail;

    TextView comicsTitle;
    RecyclerView comicsList;

    TextView eventsTitle;
    RecyclerView eventsList;


    /**
     * Constructor
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character);

        this.character = Cache.currentCharacter;
    }

    /**
     * When starts the activity
     */
    @Override
    protected void onStart() {
        super.onStart();

        setComponents();
        fillUp();
    }

    /**
     * Sets the components
     */
    private void setComponents() {



        characterTitle = (TextView) findViewById(R.id.character_title);
        characterTitle.setTypeface(defaultFont);
        characterName = (TextView) findViewById(R.id.character_name);
        characterName.setTypeface(defaultFont);
        characterDescription = (TextView) findViewById(R.id.character_description);
        characterDescription.setTypeface(defaultFont);
        characterThumbnail = (ImageView) findViewById(R.id.character_thumbnail);

        LinearLayoutManager comicsLayoutManager = new LinearLayoutManager(this);
        comicsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        comicsTitle = (TextView) findViewById(R.id.comics_title);
        comicsTitle.setTypeface(defaultFont);
        comicsList = (RecyclerView) findViewById(R.id.comics_list);
        comicsList.setLayoutManager(comicsLayoutManager);

        LinearLayoutManager eventsLayoutManager = new LinearLayoutManager(this);
        eventsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        eventsTitle = (TextView) findViewById(R.id.events_title);
        eventsTitle.setTypeface(defaultFont);
        eventsList = (RecyclerView) findViewById(R.id.events_list);
        eventsList.setLayoutManager(eventsLayoutManager);
    }

    /**
     * Fill up the activity
     */
    private void fillUp() {

        // Captura a imagem
        final Bitmap thumbnailImage = ImageUtils.getFromCharacter(character);

        // Character
        characterTitle.setText(character.getName().toUpperCase());
        characterThumbnail.setImageBitmap(thumbnailImage);

        if (character.getDescription() == null || character.getDescription().equals(""))
            characterDescription.setVisibility(View.GONE);
        else
            characterDescription.setText(character.getDescription());

        // Events
        comicsList.setAdapter(new ComicsAdapter(this, character.getComics().getItems()));

        // Events
        eventsList.setAdapter(new EventsAdapter(this, character.getEvents().getItems()));
    }
}
