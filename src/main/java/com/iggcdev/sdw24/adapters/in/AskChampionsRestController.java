package com.iggcdev.sdw24.adapters.in;

import com.iggcdev.sdw24.application.AskChampionsUseCase;
import com.iggcdev.sdw24.application.ListChampionsUseCase;
import com.iggcdev.sdw24.domain.model.Champion;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Campeões", description = "Endpoints do dominio Campeoes LOL")
@RestController
@RequestMapping("/champions")
public record AskChampionsRestController(AskChampionsUseCase useCase) {
    @PostMapping("/{championId}/ask")
    public AskChampionResponse askChampion(@PathVariable Long championId, @RequestBody AskChampionRequest request){
        String answer = useCase.askChampion(championId,request.question());
        return new AskChampionResponse(answer);
    }
    public record AskChampionRequest(String question){

    }
    public record AskChampionResponse(String answer){}
}
