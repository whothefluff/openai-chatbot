package com.openai.chatbot.domain.exception;

import java.io.Serial;

@SuppressWarnings( { "MissingJavadoc", "unused" } )
public class ChatServiceException extends Exception{

  @Serial
  private static final long serialVersionUID = 3324187937268233899L;

  public ChatServiceException( ){

    super( );

  }

  public ChatServiceException( final String message ){

    super( message );

  }

  public ChatServiceException( final String message, final Throwable cause ){

    super( message, cause );

  }

  public ChatServiceException( final Throwable cause ){

    super( cause );

  }

  public ChatServiceException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace ){

    super( message, cause, enableSuppression, writableStackTrace );

  }

}
