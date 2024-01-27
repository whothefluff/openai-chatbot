package com.openai.chatbot.intrastructure.configuration;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@SuppressWarnings( { "MissingJavadoc", "PublicConstructor" } )
@XSlf4j
@Configuration
@EnableConfigurationProperties( OpenAIProperties.class )
public class OpenAIConfig{

  @Bean
  WebClient.Builder webClientBuilder( ){

    log.entry( );
    return log.exit( WebClient.builder( ) );

  }

  @Bean
  OpenAIProperties openAIProperties( ){

    log.entry( );
    return log.exit( new OpenAIProperties( ) );

  }

}
