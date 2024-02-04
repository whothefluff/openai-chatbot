package com.openai.chatbot.domain.exception;

import lombok.experimental.StandardException;

import java.io.Serial;

@SuppressWarnings( "MissingJavadoc" )
@StandardException
public class ChatRepositoryException extends Exception{

  @Serial
  private static final long serialVersionUID = 7713523706208555752L;

  @StandardException
  public static class Conflict extends ChatRepositoryException{

    @Serial
    private static final long serialVersionUID = -6166936357086595638L;

  }

  @StandardException
  public static class NotFound extends ChatRepositoryException{

    @Serial
    private static final long serialVersionUID = 1524004762041026893L;

  }

}