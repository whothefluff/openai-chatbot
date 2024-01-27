package com.openai.chatbot.application.adapter.rest.domainintegration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for a conversation
 */
@SuppressWarnings( "ClassWithoutLogger" )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@Getter( onMethod = @__( @JsonProperty ) )
public class ConversationBody{

  UUID id;
  String name;
  Instant createdAt;
  Instant modifiedAt;

}
