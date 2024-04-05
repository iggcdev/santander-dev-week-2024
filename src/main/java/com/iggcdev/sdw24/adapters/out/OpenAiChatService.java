package com.iggcdev.sdw24.adapters.out;

import com.iggcdev.sdw24.domain.ports.GenerativeAiService;
import feign.FeignException;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@ConditionalOnProperty(name = "generatve-ai.provider", havingValue = "OPENAI", matchIfMissing = true)
@FeignClient(name = "openAiApi", url = "${openai.base-url}", configuration = OpenAiChatService.Config.class)
public interface OpenAiChatService extends GenerativeAiService {

    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq req);

    @Override
    default String genereteContent(String objective, String context){
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system",objective),
                new Message("user",context)

        );
        OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);
        try{
            OpenAiChatCompletionResp resp = chatCompletion(req);
            return resp.choices().getFirst().message().content();
    } catch (
    FeignException httpErrors){
        return "httpError. Não foi possivel comunicação com API da OPENAI.";
    } catch (Exception unexpectedErrors){
        return "Erro inesperado. Retorno da API OPENAI não contem dados esperados.";
    }
    }

    record OpenAiChatCompletionReq(String model, List<Message> messages){}
    record Message(String role, String content){}

    record OpenAiChatCompletionResp(List<Choice> choices){}
    record Choice (Message message){}

    class Config{
        //A classe vai adicionar a API_KEY do header, e autorizar a integração com OPENAI
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey){
            return requestTemplate -> requestTemplate.header(
                    HttpHeaders.AUTHORIZATION,"Bearer %s".formatted(apiKey)
            );
        }
    }

}
