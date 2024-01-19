package com.openai.chatbot.application.adapter.rest;

import com.openai.chatbot.domain.exception.ChatServiceException;
import com.openai.chatbot.domain.exception.ChatServiceNotFoundException;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SuppressWarnings( { "MissingJavadoc", "PublicConstructor" } )
@XSlf4j
@RestControllerAdvice
public class ChatServiceExceptionHandler extends ResponseEntityExceptionHandler{

  @ExceptionHandler( ChatServiceException.class )
  @ResponseStatus( BAD_REQUEST )
  public String handleParentException( final ChatServiceException e, final WebRequest request ){

    log.entry( e, request );
    return log.exit( e.getMessage( ) );

  }

  @ExceptionHandler( ChatServiceNotFoundException.class )
  @ResponseStatus( NOT_FOUND )
  public String handleNotFoundException( final ChatServiceNotFoundException e, final WebRequest request ){

    log.entry( e, request );
    return log.exit( "The request resource is not found." ); //NON-NLS

  }

}
