package com.openai.chatbot.domain.exception;

import lombok.experimental.StandardException;

import java.io.Serial;

@SuppressWarnings( "MissingJavadoc" )
@StandardException
public class ChatServiceNotFoundException extends ChatServiceException{

  @Serial
  private static final long serialVersionUID = 205103353435958745L;

}
