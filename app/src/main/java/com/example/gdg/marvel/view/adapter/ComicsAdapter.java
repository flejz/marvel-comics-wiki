package com.example.gdg.marvel.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
import com.karumi.marvelapiclient.ComicApiClient;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.ComicDto;
import com.karumi.marvelapiclient.model.ComicResourceDto;
import com.karumi.marvelapiclient.model.MarvelResponse;

import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder> {

    // Vars
    Context context;
    List<ComicResourceDto> comics;

    // Seta a fonte padrão
    Typeface defaultFont;

    /**
     * Cosntructor
     *
     * @param comics
     */
    public ComicsAdapter(Context context, List<ComicResourceDto> comics) {

        // Inicializando a classe
        this.context = context;
        this.comics = comics;

        this.defaultFont = Typeface.createFromAsset(context.getAssets(), Globals.defaultFontName);
    }

    public ComicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ComicsViewHolder(LayoutInflater.from(context).inflate(R.layout.card_comic, null));
    }

    public void onBindViewHolder(ComicsViewHolder holder, int position) {
        holder.bind(comics.get(position));
    }

    public int getItemCount() {
        return comics.size();
    }

    // Classe auxiliar
    public class ComicsViewHolder extends RecyclerView.ViewHolder {

        // Inicializa as variáveis referentes aos components na tela
        ProgressBar loading;
        TextView nameLabel;
        ImageView thumbnail;
        Bitmap thumbnailImage;

        /**
         * Constructor
         *
         * @param card
         */
        public ComicsViewHolder(View card) {
            super(card);

            // Inicializa as variáveis referentes aos components na tela
            nameLabel = (TextView) card.findViewById(R.id.name);
            nameLabel.setTypeface(defaultFont);
            loading = (ProgressBar) card.findViewById(R.id.loading);
            thumbnail = (ImageView) card.findViewById(R.id.thumbnail);
        }

        // Bids the char
        public void bind(final ComicResourceDto comic) {

            // Insere o nome do personagem no objeto name
            nameLabel.setText(comic.getName().toUpperCase());

            if (thumbnailImage != null) {
                thumbnail.setImageBitmap(thumbnailImage);
                return;
            }

            thumbnail.setImageBitmap(ImageUtils.getRandomThumbnail(context));

            // Executa de forma assíncrona a verificação da imagem
            new AsyncTask<Void, Void, Void>() {
                protected Void doInBackground(Void... voids) {

                    // Captura a imagem

                    ComicApiClient comicApiClient = new ComicApiClient(Globals.marvelApiConfig);

                    try {
                        String comicId = comic.getResourceUri().substring(comic.getResourceUri().lastIndexOf("/") + 1);
                        ComicDto comic = comicApiClient.getComic(comicId).getResponse();

                        thumbnailImage = ImageUtils.getFromComic(comic);

                        // Executa a thread da interface
                        ((Activity) context).runOnUiThread(new Runnable() {
                            public void run() {

                                // Esconde o loading e insere a imagem
                                loading.setVisibility(View.GONE);
                                thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
                                thumbnail.setImageBitmap(thumbnailImage);
                            }
                        });


                    } catch (MarvelApiException e) {
                        e.printStackTrace();
                    }


                    return null;
                }
            }.execute();
        }
    }
}
