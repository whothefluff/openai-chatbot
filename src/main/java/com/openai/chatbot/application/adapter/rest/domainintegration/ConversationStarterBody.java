package com.openai.chatbot.application.adapter.rest.domainintegration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
public class ConversationStarterBody{

  String name;
  String systemMessage;

}
