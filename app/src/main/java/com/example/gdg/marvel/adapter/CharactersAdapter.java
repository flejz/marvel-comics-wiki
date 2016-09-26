package com.example.gdg.marvel.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gdg.marvel.R;
import com.example.gdg.marvel.utils.ImageUtils;
import com.karumi.marvelapiclient.model.CharacterDto;
import com.karumi.marvelapiclient.model.MarvelImage;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder> {

    // Vars
    Context context;
    List<CharacterDto> chars;

    /**
     * Cosntructor
     *
     * @param chars
     */
    public CharactersAdapter(Context context, List<CharacterDto> chars) {

        // Inicializando a classe
        this.context = context;
        this.chars = chars;
    }

    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.card_character, null));
    }

    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        holder.bind(chars.get(position));
    }

    public int getItemCount() {
        return chars.size();
    }

    // Classe auxiliar
    public class CharacterViewHolder extends RecyclerView.ViewHolder {

        // Inicializa as variáveis referentes aos components na tela
        ProgressBar loading;
        TextView nameLabel;
        ImageView thumbnail;

        /**
         * Constructor
         *
         * @param card
         */
        public CharacterViewHolder(View card) {
            super(card);

            // Inicializa as variáveis referentes aos components na tela
            nameLabel = (TextView) card.findViewById(R.id.name);
            thumbnail = (ImageView) card.findViewById(R.id.thumbnail);
            loading = (ProgressBar) card.findViewById(R.id.loading);
        }

        // Bids the char
        public void bind(final CharacterDto character) {

            // Insere o nome do personagem no objeto name
            nameLabel.setText(character.getName().toUpperCase());

            // Executa de forma assíncrona a verificação da imagem
            new AsyncTask<Void, Void, Void>() {
                protected Void doInBackground(Void... voids) {

                    // Monta o nome do arquivo
                    String thumbnailFileName = character.getId() + "." + character.getThumbnail().getExtension();

                    // Captura a imagem
                    final Bitmap thumbnailImage = ImageUtils.getFromUrl(
                            thumbnailFileName,
                            character.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_AMAZING));

                    // Executa a thread da interface
                    ((Activity) context).runOnUiThread(new Runnable() {
                        public void run() {

                            // Esconde o loading e insere a imagem
                            loading.setVisibility(View.GONE);
                            thumbnail.setImageBitmap(thumbnailImage);

                        }
                    });
                    return null;
                }
            }.execute();
        }
    }
}
