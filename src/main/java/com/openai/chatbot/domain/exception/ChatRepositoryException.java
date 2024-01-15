package com.openai.chatbot.domain.exception;

import lombok.experimental.StandardException;

import java.io.Serial;

@SuppressWarnings( "MissingJavadoc" )
@StandardException
public class ChatRepositoryException extends Exception{

  @Serial
  private static final long serialVersionUID = 7713523706208555752L;

}