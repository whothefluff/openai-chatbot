package com.openai.chatbot.domain.exception;

import lombok.experimental.StandardException;

import java.io.Serial;

@SuppressWarnings( "MissingJavadoc" )
@StandardException
public class ChatServiceException extends Exception{

  @Serial
  private static final long serialVersionUID = 3324187937268233899L;

  @StandardException
  public static class Conflict extends ChatServiceException{

    @Serial
    private static final long serialVersionUID = -2603973940410828951L;

  }
  
  @StandardException
  public static class NotFound extends ChatServiceException{

    @Serial
    private static final long serialVersionUID = 205103353435958745L;

  }

}
