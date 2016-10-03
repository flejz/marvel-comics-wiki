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
import com.karumi.marvelapiclient.model.StoryResourceDto;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoriesViewHolder> {

    // Vars
    Context context;
    List<StoryResourceDto> stories;

    // Seta a fonte padrão
    Typeface defaultFont;

    /**
     * Cosntructor
     *
     * @param stories
     */
    public StoriesAdapter(Context context, List<StoryResourceDto> stories) {

        // Inicializando a classe
        this.context = context;
        this.stories = stories;

        this.defaultFont = Typeface.createFromAsset(context.getAssets(), Globals.defaultFontName);
    }

    public StoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new StoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.card_comic, null));
    }

    public void onBindViewHolder(StoriesViewHolder holder, int position) {
        holder.bind(stories.get(position));
    }

    public int getItemCount() {
        return stories.size();
    }

    // Classe auxiliar
    public class StoriesViewHolder extends RecyclerView.ViewHolder {

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
        public StoriesViewHolder(View card) {
            super(card);

            // Inicializa as variáveis referentes aos components na tela
            nameLabel = (TextView) card.findViewById(R.id.name);
            nameLabel.setTypeface(defaultFont);
            loading = (ProgressBar) card.findViewById(R.id.loading);
            thumbnail = (ImageView) card.findViewById(R.id.thumbnail);
        }

        // Bids the char
        public void bind(final StoryResourceDto comic) {

            // Insere o nome do personagem no objeto name
            nameLabel.setText(comic.getName().toUpperCase());

            if (thumbnailImage != null) {
                thumbnail.setImageBitmap(thumbnailImage);
                return;
            }

            thumbnail.setImageBitmap(ImageUtils.getRandomThumbnail(context));

            // Executa de forma assíncrona a verificação da imagem
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {

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
