package com.iggcdev.sdw24.domain.ports;

import com.iggcdev.sdw24.domain.model.Champion;

import java.util.List;
import java.util.Optional;

public interface ChampionsRepository {
    List<Champion> findAll();
    //Retorna todos os champions
    Optional<Champion> findById(Long id);
    //Retorna um champion por Id, Optional informa que pode ou nao retornar um champion, dependendo da ID existir.
}
