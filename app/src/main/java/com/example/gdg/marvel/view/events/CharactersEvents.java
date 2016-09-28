package com.example.gdg.marvel.view.events;

import com.karumi.marvelapiclient.model.CharacterDto;

public interface CharactersEvents {

    void onSelect(CharacterDto character);
}
