package com.openai.chatbot.intrastructure.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@SuppressWarnings( { "MissingJavadoc", "ClassWithoutLogger" } )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Validated
@ConfigurationProperties( prefix = "openai" )
public class OpenAIProperties{

  @NotBlank String endpoint;
  @NotBlank String apiKey;

}
