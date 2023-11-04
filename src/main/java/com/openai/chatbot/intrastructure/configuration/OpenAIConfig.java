package com.openai.chatbot.intrastructure.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties( OpenAIProperties.class )
public class OpenAIConfig{

  @Bean
  public WebClient.Builder webClientBuilder( ){

    return WebClient.builder( );

  }

  @Bean
  public OpenAIProperties openAIProperties( ){

    return new OpenAIProperties( );

  }

}
