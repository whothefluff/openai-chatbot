package com.openai.chatbot.application.adapter.rest.domainintegration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * DTO for a new conversation
 */
@SuppressWarnings( "ClassWithoutLogger" )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@Getter( onMethod = @__( @JsonProperty ) )
public class ConversationStarterBody{

  String name;
  String systemMessage;

}
