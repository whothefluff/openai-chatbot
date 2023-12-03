package com.openai.chatbot.domain.exception;

import java.io.Serial;

@SuppressWarnings( { "MissingJavadoc", "unused" } )
public class ChatRepositoryException extends Exception{

  @Serial
  private static final long serialVersionUID = 7713523706208555752L;

  public ChatRepositoryException( ){

    super( );

  }

  public ChatRepositoryException( final String message ){

    super( message );

  }

  public ChatRepositoryException( final String message, final Throwable cause ){

    super( message, cause );

  }

  public ChatRepositoryException( final Throwable cause ){

    super( cause );

  }

  public ChatRepositoryException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace ){

    super( message, cause, enableSuppression, writableStackTrace );

  }

}
