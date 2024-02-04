package com.openai.chatbot.application.adapter.rest.component;

import com.openai.chatbot.domain.exception.ChatServiceException;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@XSlf4j
@RestControllerAdvice
class ChatServiceExceptionHandler extends ResponseEntityExceptionHandler{

  @ExceptionHandler( ChatServiceException.class )
  @ResponseStatus( BAD_REQUEST )
  public String handleParentException( final ChatServiceException e, final WebRequest request ){

    log.entry( e, request );
    log.catching( e );
    return log.exit( e.getMessage( ) );

  }

  @ExceptionHandler( ChatServiceException.NotFound.class )
  @ResponseStatus( NOT_FOUND )
  public String handleNotFoundException( final ChatServiceException.NotFound e, final WebRequest request ){

    log.entry( e, request );
    log.catching( e );
    return log.exit( "The request resource is not found." ); //NON-NLS

  }

  @ExceptionHandler( ChatServiceException.Conflict.class )
  @ResponseStatus( CONFLICT )
  public String handleConflictException( final ChatServiceException.Conflict e, final WebRequest request ){

    log.entry( e, request );
    log.catching( e );
    return log.exit( e.getMessage( ) );

  }

}
