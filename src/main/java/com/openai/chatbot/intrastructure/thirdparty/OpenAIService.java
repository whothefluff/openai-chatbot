package com.openai.chatbot.intrastructure.thirdparty;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.port.secondary.ChatCompletionsService;
import com.openai.chatbot.intrastructure.configuration.OpenAIProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Chat completions using OpenAI API
 */
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED,
                makeFinal = true )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
@Service
class OpenAIService implements ChatCompletionsService{

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