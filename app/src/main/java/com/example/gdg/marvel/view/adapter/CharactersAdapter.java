package com.example.gdg.marvel.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gdg.marvel.R;
import com.example.gdg.marvel.base.Globals;
import com.example.gdg.marvel.utils.ImageUtils;
import com.example.gdg.marvel.view.events.CharactersEvents;
import com.karumi.marvelapiclient.model.CharacterDto;
import com.karumi.marvelapiclient.model.MarvelImage;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder> {

    // Vars
    Context context;
    List<CharacterDto> characters;
    //CharactersEvents events;

    // Seta a fonte padrão
    Typeface defaultFont;

    /**
     * Cosntructor
     *
     * @param characters
     */
    public CharactersAdapter(Context context, List<CharacterDto> characters) {

        // Inicializando a classe
        this.context = context;
        this.characters = characters;
        //this.events = events;

        this.defaultFont = Typeface.createFromAsset(context.getAssets(), Globals.defaultFontName);
    }

    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.card_character, null));
    }

    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        holder.bind(characters.get(position));
    }

    public int getItemCount() {
        return characters.size();
    }

    // Classe auxiliar
    public class CharacterViewHolder extends RecyclerView.ViewHolder {

        // Inicializa as variáveis referentes aos components na tela
        ProgressBar loading;
        TextView nameLabel;
        ImageView thumbnail;
        CharacterDto character;

        /**
         * Constructor
         *
         * @param card
         */
        public CharacterViewHolder(View card) {
            super(card);

            // Inicializa as variáveis referentes aos components na tela
            nameLabel = (TextView) card.findViewById(R.id.name);
            nameLabel.setTypeface(defaultFont);
            loading = (ProgressBar) card.findViewById(R.id.loading);
            thumbnail = (ImageView) card.findViewById(R.id.thumbnail);

            /*
            thumbnail.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (events != null) {
                        events.onSelect(character);
                    }
                }
            });
            */
        }

        // Bids the char
        public void bind(final CharacterDto character) {

            // Insere o nome do personagem no objeto name
            nameLabel.setText(character.getName().toUpperCase());
            thumbnail.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marvel_logo_default_character));

            this.character = character;

            // Executa de forma assíncrona a verificação da imagem
            new AsyncTask<Void, Void, Void>() {
                protected Void doInBackground(Void... voids) {

                    // Captura a imagem
                    final Bitmap thumbnailImage = ImageUtils.getFromCharacter(character);

                    // Executa a thread da interface
                    ((Activity) context).runOnUiThread(new Runnable() {
                        public void run() {

                            // Esconde o loading e insere a imagem
                            loading.setVisibility(View.GONE);
                            thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
                            thumbnail.setImageBitmap(thumbnailImage);

                        }
                    });
                    return null;
                }
            }.execute();
        }
    }
}
