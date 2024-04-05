package com.iggcdev.sdw24.application;

import com.iggcdev.sdw24.domain.exceptions.ChampionNotFoundException;
import com.iggcdev.sdw24.domain.model.Champion;
import com.iggcdev.sdw24.domain.ports.ChampionsRepository;
import com.iggcdev.sdw24.domain.ports.GenerativeAiService;

public record AskChampionsUseCase(ChampionsRepository repository, GenerativeAiService genAiApi) {

    public String askChampion(Long championId, String question){
        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);
        String objective = """
                Atue como assistente, com habilidade de se comportar como os campeões de league of legends.
                Responda as perguntas incorporando a personalidade e estilo de um determinado campeão.
                Segue a pergunta, o nome do Campeão, e sua respectiva lore(historia):
                
                """;

        return genAiApi.genereteContent(objective,context);
    }
}
