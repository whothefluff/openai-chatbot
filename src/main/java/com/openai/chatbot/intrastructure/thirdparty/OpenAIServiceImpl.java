package com.openai.chatbot.intrastructure.thirdparty;

import com.openai.chatbot.domain.port.secondary.chatcompletions.ChatCompletionsService;
import com.openai.chatbot.domain.port.secondary.chatcompletions.ChatRequest;
import com.openai.chatbot.domain.port.secondary.chatcompletions.ChatResponse;
import com.openai.chatbot.intrastructure.configuration.OpenAIProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@Service
public class OpenAIServiceImpl implements ChatCompletionsService{

  //TODO undo jpa renaming
  //TODO create domain entities (Step 4)
  //TODO create DTOs and mapper (step 4)
  final WebClient.Builder webClientBuilder;
  final OpenAIProperties openAIProperties;

  @Override
  public ChatResponse createResponse( final ChatRequest request ){
    /*
    OpenAIRequestDTO apiRequestDTO = OpenAIMapper.toAPIRequestDTO(request);

    WebClient webClient = webClientBuilder.build();

    OpenAIResponseDTO apiResponseDTO = webClient.post()
            .uri(openAIProperties.getApiEndpoint())
            .header("Authorization", "Bearer " + openAIProperties.getApiKey())
            .bodyValue(apiRequestDTO)
            .retrieve()
            .bodyToMono(OpenAIResponseDTO.class)
            .block();  // Wait for the response; non-blocking in a fully reactive setup

    // Convert API-specific response DTO to domain entity
    return OpenAIMapper.toDomainChatResponse(apiResponseDTO);
    */
    return null;

  }

}