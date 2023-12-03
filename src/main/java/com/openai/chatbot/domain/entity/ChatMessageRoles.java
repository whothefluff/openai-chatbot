package com.openai.chatbot.domain.entity;

/**
 * Constants for system, user, assistant, or function
 */
@SuppressWarnings( { "ALL" } )
public class ChatMessageRoles{

  //lazy
  public static final ChatMessageRole SYSTEM = new ChatMessageRole.System( );
  public static final ChatMessageRole USER = new ChatMessageRole.User( );
  public static final ChatMessageRole ASSISTANT = new ChatMessageRole.Assistant( );
  public static final ChatMessageRole FUNCTION = new ChatMessageRole.Function( );

  private ChatMessageRoles( ){

    super( );

  }

}
