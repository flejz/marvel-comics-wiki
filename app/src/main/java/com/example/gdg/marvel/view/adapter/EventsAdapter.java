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
import com.example.gdg.marvel.view.events.CharactersEvents;
import com.karumi.marvelapiclient.model.CharacterDto;
import com.karumi.marvelapiclient.model.EventResourceDto;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    // Vars
    Context context;
    List<EventResourceDto> events;

    // Seta a fonte padrão
    Typeface defaultFont;

    /**
     * Cosntructor
     *
     * @param events
     */
    public EventsAdapter(Context context, List<EventResourceDto> events) {

        // Inicializando a classe
        this.context = context;
        this.events = events;

        this.defaultFont = Typeface.createFromAsset(context.getAssets(), Globals.defaultFontName);
    }

    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new EventsViewHolder(LayoutInflater.from(context).inflate(R.layout.card_event, null));
    }

    public void onBindViewHolder(EventsViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    public int getItemCount() {
        return events.size();
    }

    // Classe auxiliar
    public class EventsViewHolder extends RecyclerView.ViewHolder {

        // Inicializa as variáveis referentes aos components na tela
        TextView nameLabel;

        /**
         * Constructor
         *
         * @param card
         */
        public EventsViewHolder(View card) {
            super(card);

            // Inicializa as variáveis referentes aos components na tela
            nameLabel = (TextView) card.findViewById(R.id.name);
            nameLabel.setTypeface(defaultFont);
        }

        // Bids the char
        public void bind(final EventResourceDto event) {

            // Insere o nome do personagem no objeto name
            nameLabel.setText(event.getName().toUpperCase());
        }
    }
}
