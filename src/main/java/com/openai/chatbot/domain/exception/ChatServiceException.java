package com.openai.chatbot.domain.exception;

import lombok.experimental.StandardException;

import java.io.Serial;

@SuppressWarnings( "MissingJavadoc" )
@StandardException
public class ChatServiceException extends Exception{

  @Serial
  private static final long serialVersionUID = 3324187937268233899L;

}
