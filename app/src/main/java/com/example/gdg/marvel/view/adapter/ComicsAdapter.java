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
import com.karumi.marvelapiclient.ComicApiClient;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.CharacterDto;
import com.karumi.marvelapiclient.model.ComicDto;
import com.karumi.marvelapiclient.model.ComicResourceDto;
import com.karumi.marvelapiclient.model.ComicsQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder> {

    // Vars
    Context context;
    List<ComicDto> comics = new ArrayList<>();
    List<ComicResourceDto> comicResources = new ArrayList<>();

    // Seta a fonte padrão
    Typeface defaultFont;

    /**
     * Cosntructor
     *
     * @param character
     */
    public ComicsAdapter(Context context, final CharacterDto character) {

        // Inicializando a classe
        this.context = context;

        this.defaultFont = Typeface.createFromAsset(context.getAssets(), Globals.defaultFontName);

        new AsyncTask() {

            protected Object doInBackground(Object[] params) {

                // Cria a api para busca e os termos de pesquisa
                ComicApiClient comicApiClient = new ComicApiClient(Globals.marvelApiConfig);
                ComicsQuery query = ComicsQuery.Builder
                        .create()
                        .withFormat(ComicsQuery.Format.COMIC)
                        .withFormatType(ComicsQuery.FormatType.COMIC)
                        .withNoVariants()
                        .withLimit(100)
                        .addCharacter(Integer.parseInt(character.getId()))
                        .build();

                try {
                    ComicsAdapter.this.comics = comicApiClient.getAll(query).getResponse().getComics();

                } catch (MarvelApiException e) {

                    ComicsAdapter.this.comicResources = character.getComics().getItems();
                }

                ((Activity) ComicsAdapter.this.context).runOnUiThread(new TimerTask() {
                    public void run() {

                        notifyDataSetChanged();
                    }
                });

                return null;
            }
        }.execute();
    }

    public ComicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ComicsViewHolder(LayoutInflater.from(context).inflate(R.layout.card_comic, null));
    }

    public void onBindViewHolder(ComicsViewHolder holder, int position) {

        if (comicResources.size() == 0)
            holder.bind(comics.get(position));
        else
            holder.bind(comicResources.get(position));
    }

    public int getItemCount() {

        return comicResources.size() == 0 ? comics.size() : comicResources.size();
    }

    // Classe auxiliar
    public class ComicsViewHolder extends RecyclerView.ViewHolder {

        // Inicializa as variáveis referentes aos components na tela
        TextView nameLabel;
        ImageView thumbnail;
        Bitmap thumbnailImage;
        Bitmap defaultBitmap;

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
            thumbnail = (ImageView) card.findViewById(R.id.thumbnail);

            defaultBitmap = ImageUtils.getResizedBitmap(context, R.drawable.marvel_logo_default_comic, 450, 300);
        }

        // Bids the char
        public void bind(final ComicResourceDto comic) {

            // Insere o nome do personagem no objeto name
            nameLabel.setText(comic.getName());

            thumbnail.setImageBitmap(defaultBitmap);

            // Executa de forma assíncrona a verificação da imagem
            new AsyncTask() {
                protected Void doInBackground(Object[] params) {

                    // Captura a imagem
                    ComicApiClient comicApiClient = new ComicApiClient(Globals.marvelApiConfig);

                    try {
                        String comicId = comic.getResourceUri().substring(comic.getResourceUri().lastIndexOf("/") + 1);
                        ComicDto comic = comicApiClient.getComic(comicId).getResponse();

                        thumbnailImage = ImageUtils.getFromComic(comic);

                    } catch (MarvelApiException e) {

                        thumbnailImage = defaultBitmap;
                    }

                    updateImage();

                    return null;
                }
            }.execute();
        }

        // Bids the char
        public void bind(final ComicDto comic) {

            // Insere o nome do personagem no objeto name
            nameLabel.setText(comic.getTitle());

            thumbnail.setImageBitmap(defaultBitmap);

            // Executa de forma assíncrona a verificação da imagem
            new AsyncTask() {
                protected Void doInBackground(Object[] params) {
                    thumbnailImage = ImageUtils.getFromComic(comic);

                    updateImage();
                    return null;
                }
            }.execute();
        }

        /**
         * Atualiza a imagem
         */
        private void updateImage() {

            // Executa a thread da interface
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {

                    thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
                    thumbnail.setImageBitmap(thumbnailImage);
                }
            });

        }
    }
}
