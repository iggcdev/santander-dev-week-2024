package com.iggcdev.sdw24.application;

import com.iggcdev.sdw24.domain.exceptions.ChampionNotFoundException;
import com.iggcdev.sdw24.domain.model.Champion;
import com.iggcdev.sdw24.domain.ports.ChampionsRepository;

import java.util.List;

public record AskChampionsUseCase(ChampionsRepository repository) {

    public String askChampion(Long championId, String question){
        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));
        return champion.generateContextByQuestion(question);
    }
}
