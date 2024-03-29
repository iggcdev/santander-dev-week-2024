package com.iggcdev.sdw24.application;

import com.iggcdev.sdw24.domain.model.Champions;
import com.iggcdev.sdw24.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase(ChampionsRepository repository) {

    public List<Champions> findAll(){
        return repository.findAll();
    }
}
